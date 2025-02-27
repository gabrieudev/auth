package br.com.gabrieudev.auth.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrieudev.auth.application.exceptions.AlreadyExistsException;
import br.com.gabrieudev.auth.application.exceptions.EmailException;
import br.com.gabrieudev.auth.application.exceptions.InternalErrorException;
import br.com.gabrieudev.auth.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.auth.application.exceptions.NotFoundException;
import br.com.gabrieudev.auth.application.ports.output.AuthOutputPort;
import br.com.gabrieudev.auth.application.ports.output.CacheOutputPort;
import br.com.gabrieudev.auth.application.ports.output.EmailOutputPort;
import br.com.gabrieudev.auth.application.ports.output.EnvironmentOutputPort;
import br.com.gabrieudev.auth.application.ports.output.RoleOutputPort;
import br.com.gabrieudev.auth.application.ports.output.UserOutputPort;
import br.com.gabrieudev.auth.application.ports.output.UserRoleOutputPort;
import br.com.gabrieudev.auth.domain.Role;
import br.com.gabrieudev.auth.domain.User;
import br.com.gabrieudev.auth.domain.UserRole;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private User inputUser;
    private User outputUser;
    private Role userRole;
    
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<Role> roleArgumentCaptor;

    @Captor
    private ArgumentCaptor<UserRole> userRoleArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Mock
    private UserOutputPort userOutputPort;
    
    @Mock
    private RoleOutputPort roleOutputPort;
    
    @Mock
    private UserRoleOutputPort userRoleOutputPort;
    
    @Mock
    private EmailOutputPort emailOutputPort;
    
    @Mock
    private AuthOutputPort authOutputPort;
    
    @Mock
    private CacheOutputPort cacheOutputPort;
    
    @Mock
    private EnvironmentOutputPort environmentOutputPort;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        inputUser = new User(null, "John", "Paul", "john@gmail", "123456", false, null, null);

        outputUser = new User(UUID.randomUUID(), "John", "Paul", "john@gmail", "123456", false, LocalDateTime.now(), LocalDateTime.now());

        userRole = new Role(UUID.randomUUID(), "USER", "User role");
    }

    @Test
    @DisplayName("Should create user")
    public void shouldCreateUser() {
        UserRole assignedRole = new UserRole(UUID.randomUUID(), outputUser, userRole);

        doReturn(false).when(userOutputPort).existsByEmail(inputUser.getEmail());
        doReturn(Optional.of(outputUser)).when(userOutputPort).create(userArgumentCaptor.capture());
        doReturn(Optional.of(userRole)).when(roleOutputPort).findByName("USER");
        doReturn(Optional.of(assignedRole)).when(userRoleOutputPort).create(userRoleArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).findById(uuidArgumentCaptor.capture());
        doReturn(true).when(cacheOutputPort).set(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), integerArgumentCaptor.capture());
        doReturn("http://localhost:8080/api/v1/users/confirm").when(environmentOutputPort).getEmailConfirmationUrl();
        doReturn(true).when(emailOutputPort).sendEmail(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture());

        userService.create(inputUser);

        assertEquals(inputUser, userArgumentCaptor.getValue());
        assertEquals(assignedRole.getUser(), userRoleArgumentCaptor.getValue().getUser());
        assertEquals(assignedRole.getRole(), userRoleArgumentCaptor.getValue().getRole());
        assertEquals(outputUser.getId().toString(), stringArgumentCaptor.getAllValues().get(1));
        assertEquals(5, integerArgumentCaptor.getValue());
        assertEquals(outputUser.getEmail(), stringArgumentCaptor.getAllValues().get(2));
        assertEquals(outputUser.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when user already exists")
    public void shouldThrowExceptionWhenUserAlreadyExists() {
        doReturn(true).when(userOutputPort).existsByEmail(stringArgumentCaptor.capture());

        assertThrows(AlreadyExistsException.class, () -> userService.create(inputUser));

        assertEquals(inputUser.getEmail(), stringArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when user creation fails")
    public void shouldThrowExceptionWhenUserCreationFails() {
        doReturn(false).when(userOutputPort).existsByEmail(stringArgumentCaptor.capture());
        doReturn(Optional.empty()).when(userOutputPort).create(userArgumentCaptor.capture());

        assertThrows(InternalErrorException.class, () -> userService.create(inputUser));

        assertEquals(inputUser, userArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when user role creation fails")
    public void shouldThrowExceptionWhenUserRoleCreationFails() {
        doReturn(false).when(userOutputPort).existsByEmail(stringArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).create(userArgumentCaptor.capture());
        doReturn(Optional.empty()).when(roleOutputPort).findByName("USER");

        assertThrows(InternalErrorException.class, () -> userService.create(inputUser));

        assertEquals(inputUser, userArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when role not found")
    public void shouldThrowExceptionWhenRoleNotFound() {
        doReturn(false).when(userOutputPort).existsByEmail(stringArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).create(userArgumentCaptor.capture());
        doReturn(Optional.empty()).when(roleOutputPort).findByName("USER");

        assertThrows(InternalErrorException.class, () -> userService.create(inputUser));

        assertEquals(inputUser, userArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should send confirmation email")
    public void shouldSendConfirmationEmail() {
        doReturn(Optional.of(outputUser)).when(userOutputPort).findById(uuidArgumentCaptor.capture());
        doReturn(true).when(cacheOutputPort).set(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), integerArgumentCaptor.capture());
        doReturn("http://localhost:8080/api/v1/users/confirm").when(environmentOutputPort).getEmailConfirmationUrl();
        doReturn(true).when(emailOutputPort).sendEmail(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture());

        userService.sendConfirmationEmail(outputUser.getId());

        assertEquals(outputUser.getId().toString(), stringArgumentCaptor.getAllValues().get(1));
        assertEquals(5, integerArgumentCaptor.getValue());
        assertEquals(outputUser.getEmail(), stringArgumentCaptor.getAllValues().get(2));
        assertEquals(outputUser.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when caching fails")
    public void shouldThrowExceptionWhenCachingFails() {
        doReturn(false).when(cacheOutputPort).set(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), integerArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).findById(uuidArgumentCaptor.capture());

        assertThrows(InternalErrorException.class, () -> userService.sendConfirmationEmail(outputUser.getId()));

        assertEquals(outputUser.getId().toString(), stringArgumentCaptor.getAllValues().get(1));
    }

    @Test
    @DisplayName("Should throw exception when email sending fails")
    public void shouldThrowExceptionWhenEmailSendingFails() {
        doReturn(Optional.of(outputUser)).when(userOutputPort).findById(uuidArgumentCaptor.capture());
        doReturn(true).when(cacheOutputPort).set(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), integerArgumentCaptor.capture());
        doReturn("http://localhost:8080/api/v1/users/confirm").when(environmentOutputPort).getEmailConfirmationUrl();
        doReturn(false).when(emailOutputPort).sendEmail(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture());

        assertThrows(EmailException.class, () -> userService.sendConfirmationEmail(outputUser.getId()));

        assertEquals(outputUser.getId().toString(), stringArgumentCaptor.getAllValues().get(1));
    }

    @Test
    @DisplayName("Should delete user")
    public void shouldDeleteUser() {
        UserRole assignedRole = new UserRole(UUID.randomUUID(), outputUser, userRole); 

        doReturn(true).when(userOutputPort).existsById(uuidArgumentCaptor.capture());
        doReturn(List.of(assignedRole)).when(userRoleOutputPort).findByUserId(uuidArgumentCaptor.capture());
        doReturn(true).when(userRoleOutputPort).delete(uuidArgumentCaptor.capture());
        doReturn(true).when(userOutputPort).delete(uuidArgumentCaptor.capture());

        userService.delete(outputUser.getId());

        assertEquals(outputUser.getId(), uuidArgumentCaptor.getAllValues().get(0));
        assertEquals(outputUser.getId(), uuidArgumentCaptor.getAllValues().get(1));
        assertEquals(assignedRole.getId(), uuidArgumentCaptor.getAllValues().get(2));
        assertEquals(outputUser.getId(), uuidArgumentCaptor.getAllValues().get(3));
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    public void shouldThrowExceptionWhenUserNotFound() {
        doReturn(false).when(userOutputPort).existsById(uuidArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.delete(outputUser.getId()));

        assertEquals(outputUser.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when user is not deleted")
    public void shouldThrowExceptionWhenUserIsNotDeleted() {
        doReturn(true).when(userOutputPort).existsById(uuidArgumentCaptor.capture());
        doReturn(List.of()).when(userRoleOutputPort).findByUserId(uuidArgumentCaptor.capture());
        doReturn(false).when(userOutputPort).delete(uuidArgumentCaptor.capture());

        assertThrows(InternalErrorException.class, () -> userService.delete(outputUser.getId()));

        assertEquals(outputUser.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should get all users")
    public void shouldGetAllUsers() {
        doReturn(List.of(outputUser)).when(userOutputPort).findAll(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), integerArgumentCaptor.capture(), integerArgumentCaptor.capture());

        assertEquals(List.of(outputUser), userService.findAll(null, null, 0, 10));
    }

    @Test
    @DisplayName("Should get user by id")
    public void shouldGetUserById() {
        doReturn(Optional.of(outputUser)).when(userOutputPort).findById(uuidArgumentCaptor.capture());

        assertEquals(outputUser, userService.findById(outputUser.getId()));
    }

    @Test
    @DisplayName("Should update user")
    public void shouldUpdateUser() {
        inputUser.setId(outputUser.getId());

        doReturn(true).when(userOutputPort).existsById(uuidArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).update(userArgumentCaptor.capture());
    
        userService.update(inputUser);

        assertEquals(inputUser, userArgumentCaptor.getValue());
        assertEquals(outputUser.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    public void shouldThrowExceptionWhenUserNotFoundToUpdate() {
        doReturn(false).when(userOutputPort).existsById(uuidArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.update(inputUser));

        assertEquals(inputUser.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when user is not updated")
    public void shouldThrowExceptionWhenUserIsNotUpdated() {
        doReturn(true).when(userOutputPort).existsById(uuidArgumentCaptor.capture());
        doReturn(Optional.empty()).when(userOutputPort).update(userArgumentCaptor.capture());

        assertThrows(InternalErrorException.class, () -> userService.update(inputUser));

        assertEquals(inputUser, userArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should confirm User email")
    public void shouldConfirmUserEmail() {
        UUID userId = outputUser.getId();

        doReturn(Optional.of(userId.toString())).when(cacheOutputPort).get(stringArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).findById(uuidArgumentCaptor.capture());
        doReturn(true).when(userOutputPort).existsById(uuidArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).update(userArgumentCaptor.capture());
        doReturn(true).when(cacheOutputPort).delete(stringArgumentCaptor.capture());
        doReturn(true).when(emailOutputPort).sendEmail(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture());

        userService.confirmEmail(userId);

        assertEquals("code:" + userId.toString(), stringArgumentCaptor.getAllValues().get(0));
        assertEquals("code:" + userId.toString(), stringArgumentCaptor.getAllValues().get(1));
        assertEquals(userId, uuidArgumentCaptor.getAllValues().get(0));
        assertEquals(userId, uuidArgumentCaptor.getAllValues().get(1));
        assertEquals(outputUser, userArgumentCaptor.getAllValues().get(0));
        assertEquals(outputUser.getEmail(), stringArgumentCaptor.getAllValues().get(2));
        assertEquals("Confirmação de e-mail", stringArgumentCaptor.getAllValues().get(3));
        assertEquals("Seu e-mail foi confirmado com sucesso.", stringArgumentCaptor.getAllValues().get(4));
    }

    @Test
    @DisplayName("Should throw exception when user not found to confirm email")
    public void shouldThrowExceptionWhenUserNotFoundToConfirmEmail() {
        doReturn(Optional.empty()).when(cacheOutputPort).get(stringArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.confirmEmail(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should throw exception when code is not valid")
    public void shouldThrowExceptionWhenCodeIsNotValid() {
        doReturn(Optional.empty()).when(cacheOutputPort).get(stringArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.confirmEmail(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should get me user")
    public void shouldGetMeUser() {
        String token = "token";

        doReturn(true).when(authOutputPort).isValidToken(stringArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(authOutputPort).getUserByToken(stringArgumentCaptor.capture());

        userService.getMe(token);

        assertEquals(token, stringArgumentCaptor.getAllValues().get(0));
        assertEquals(token, stringArgumentCaptor.getAllValues().get(1));
    }

    @Test
    @DisplayName("Should throw exception when token is not valid")
    public void shouldThrowExceptionWhenTokenIsNotValid() {
        doReturn(false).when(authOutputPort).isValidToken(stringArgumentCaptor.capture());

        assertThrows(InvalidTokenException.class, () -> userService.getMe("token"));
    }

    @Test
    @DisplayName("Should throw exception when user is not found")
    public void shouldThrowExceptionWhenUserIsNotFound() {
        doReturn(true).when(authOutputPort).isValidToken(stringArgumentCaptor.capture());
        doReturn(Optional.empty()).when(authOutputPort).getUserByToken(stringArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.getMe("token"));
    }

    @Test
    @DisplayName("Should validate reset password")
    public void shouldValidateResetPassword() {
        UUID code = UUID.randomUUID();
        
        doReturn(Optional.of(outputUser.getId().toString())).when(cacheOutputPort).get(stringArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).findById(uuidArgumentCaptor.capture());

        userService.validateResetPassword(code);

        assertEquals(outputUser.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when user not found to validate reset password")
    public void shouldThrowExceptionWhenUserNotFoundToValidateResetPassword() {
        doReturn(Optional.empty()).when(cacheOutputPort).get(stringArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.validateResetPassword(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should throw exception when code is not valid")
    public void shouldThrowExceptionWhenCodeIsNotValidToValidateResetPassword() {
        doReturn(Optional.empty()).when(cacheOutputPort).get(stringArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.validateResetPassword(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should send reset password email")
    public void shouldSendResetPasswordEmail() {
        doReturn(Optional.of(outputUser)).when(authOutputPort).getUserByToken(stringArgumentCaptor.capture());
        doReturn(true).when(cacheOutputPort).set(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), integerArgumentCaptor.capture());
        doReturn(true).when(emailOutputPort).sendEmail(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture());

        userService.sendResetPasswordEmail("token");

        assertEquals(outputUser.getEmail(), stringArgumentCaptor.getAllValues().get(3));
    }

    @Test
    @DisplayName("Should throw exception when user not found to send reset password email")
    public void shouldThrowExceptionWhenUserNotFoundToSendResetPasswordEmail() {
        doReturn(Optional.empty()).when(authOutputPort).getUserByToken(stringArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.sendResetPasswordEmail("token"));
    }

    @Test
    @DisplayName("Should throw exception when email sending fails")
    public void shouldThrowExceptionWhenEmailSendingFailsToSendResetPasswordEmail() {
        doReturn(Optional.of(outputUser)).when(authOutputPort).getUserByToken(stringArgumentCaptor.capture());
        doReturn(true).when(cacheOutputPort).set(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), integerArgumentCaptor.capture());
        doReturn(false).when(emailOutputPort).sendEmail(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture());

        assertThrows(EmailException.class, () -> userService.sendResetPasswordEmail("token"));
    }

    @Test
    @DisplayName("Should reset password")
    public void shouldResetPassword() {
        String newPassword = "newPassword";
        String hashPassword = "hashPassword";
        UUID code = UUID.randomUUID();

        doReturn(Optional.of(outputUser.getId().toString())).when(cacheOutputPort).get(stringArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).findById(uuidArgumentCaptor.capture());
        doReturn(hashPassword).when(userOutputPort).hashPassword(stringArgumentCaptor.capture());
        doReturn(true).when(userOutputPort).existsById(uuidArgumentCaptor.capture());
        doReturn(Optional.of(outputUser)).when(userOutputPort).update(userArgumentCaptor.capture());

        userService.resetPassword(code, newPassword);

        assertEquals(outputUser.getId(), uuidArgumentCaptor.getValue());
        assertEquals(newPassword, stringArgumentCaptor.getAllValues().get(1));
        assertEquals(outputUser, userArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should throw exception when user not found to reset password")
    public void shouldThrowExceptionWhenUserNotFoundToResetPassword() {
        doReturn(Optional.of(outputUser.getId().toString())).when(cacheOutputPort).get(stringArgumentCaptor.capture());
        doReturn(Optional.empty()).when(userOutputPort).findById(uuidArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.resetPassword(UUID.randomUUID(), "newPassword"));
    }

    @Test
    @DisplayName("Should throw exception when code is not valid")
    public void shouldThrowExceptionWhenCodeIsNotValidToResetPassword() {
        doReturn(Optional.empty()).when(cacheOutputPort).get(stringArgumentCaptor.capture());

        assertThrows(NotFoundException.class, () -> userService.resetPassword(UUID.randomUUID(), "newPassword"));
    }
}
