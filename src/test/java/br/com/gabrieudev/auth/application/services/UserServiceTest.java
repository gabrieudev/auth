package br.com.gabrieudev.auth.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrieudev.auth.application.exceptions.*;
import br.com.gabrieudev.auth.application.ports.output.*;
import br.com.gabrieudev.auth.domain.Role;
import br.com.gabrieudev.auth.domain.User;
import br.com.gabrieudev.auth.domain.UserRole;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private User testUser;
    private final UUID userId = UUID.randomUUID();
    private final String email = "test@example.com";
    private final String hashedPassword = "hashedPassword";

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

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
    void setUp() {
        testUser = new User(
                userId,
                "John",
                "Doe",
                email,
                hashedPassword,
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @Test
    void create_WhenEmailExists_ThrowsAlreadyExistsException() {
        when(userOutputPort.existsByEmail(email)).thenReturn(true);

        assertThrows(AlreadyExistsException.class,
                () -> userService.create(testUser));
    }

    @Test
    void create_Success_ReturnsCreatedUser() {
        when(userOutputPort.existsByEmail(email)).thenReturn(false);
        when(userOutputPort.create(any(User.class))).thenReturn(Optional.of(testUser));
        when(roleOutputPort.findByName("USER"))
                .thenReturn(Optional.of(new Role(UUID.randomUUID(), "USER", "user role")));
        when(userRoleOutputPort.create(any(UserRole.class))).thenReturn(Optional.of(new UserRole()));
        when(emailOutputPort.sendEmail(anyString(), anyString(), anyString())).thenReturn(true);

        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));

        when(environmentOutputPort.getEmailConfirmationUrl()).thenReturn("http://localhost:8080/users/confirm");
        when(cacheOutputPort.set(anyString(), anyString(), anyInt())).thenReturn(true);

        User result = userService.create(testUser);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userOutputPort).hashPassword(anyString());
        verify(emailOutputPort).sendEmail(eq(email), anyString(), stringArgumentCaptor.capture());
        assertTrue(stringArgumentCaptor.getValue().contains("http://localhost:8080/users/confirm"));
    }

    @Test
    void delete_WhenUserNotExists_ThrowsNotFoundException() {
        when(userOutputPort.existsById(userId)).thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> userService.delete(userId));
    }

    @Test
    void delete_Success_DeletesUserAndRoles() {
        when(userOutputPort.existsById(userId)).thenReturn(true);
        when(userRoleOutputPort.findByUserId(userId)).thenReturn(List.of(new UserRole()));
        when(userOutputPort.delete(userId)).thenReturn(true);

        userService.delete(userId);

        verify(userOutputPort).delete(userId);
    }

    @Test
    void confirmEmail_InvalidCode_ThrowsNotFoundException() {
        when(cacheOutputPort.get(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> userService.confirmEmail(UUID.randomUUID()));
    }

    @Test
    void confirmEmail_Success_ActivatesUser() {
        UUID code = UUID.randomUUID();

        when(cacheOutputPort.get("code:" + code)).thenReturn(Optional.of(userId.toString()));
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(userOutputPort.existsById(userId)).thenReturn(true);
        when(userOutputPort.update(any(User.class))).thenAnswer(invocation -> {
            User updatedUser = invocation.getArgument(0);
            return Optional.of(updatedUser);
        });

        userService.confirmEmail(code);

        assertTrue(testUser.getIsActive());
        verify(cacheOutputPort).delete("code:" + code);
        verify(emailOutputPort).sendEmail(
                eq(testUser.getEmail()),
                eq("Confirmação de e-mail"),
                contains("confirmado com sucesso"));
    }

    @Test
    void sendConfirmationEmail_CacheFailure_ThrowsInternalError() {
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(cacheOutputPort.set(anyString(), anyString(), anyInt())).thenReturn(false);

        assertThrows(InternalErrorException.class,
                () -> userService.sendConfirmationEmail(userId));
    }

    @Test
    void getMe_InvalidToken_ThrowsInvalidTokenException() {
        when(authOutputPort.isValidToken(anyString())).thenReturn(false);

        assertThrows(InvalidTokenException.class,
                () -> userService.getMe("invalid-token"));
    }

    @Test
    void resetPassword_InvalidCode_ThrowsNotFoundException() {
        when(cacheOutputPort.get(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> userService.resetPassword(UUID.randomUUID(), "newPassword"));
    }

    @Test
    void resetPassword_Success_UpdatesPasswordAndSendsEmail() {
        UUID code = UUID.randomUUID();
        String newPassword = "newPassword";
        String newHashedPassword = "newHashedPassword";

        when(cacheOutputPort.get("code:" + code)).thenReturn(Optional.of(userId.toString()));
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(userOutputPort.existsById(userId)).thenReturn(true);
        when(userOutputPort.update(any(User.class))).thenAnswer(invocation -> {
            User updatedUser = invocation.getArgument(0);
            return Optional.of(updatedUser);
        });
        when(userOutputPort.hashPassword(newPassword)).thenReturn(newHashedPassword);

        userService.resetPassword(code, newPassword);

        assertEquals(newHashedPassword, testUser.getPassword());
        verify(userOutputPort).update(testUser);
        verify(cacheOutputPort).delete("code:" + code);
        verify(emailOutputPort).sendEmail(
                eq(testUser.getEmail()),
                eq("Redefinição de senha"),
                contains("redefinida com sucesso"));
    }

    @Test
    void sendResetPasswordEmail_EmailFailure_ThrowsEmailException() {
        when(authOutputPort.getUserByToken(anyString())).thenReturn(Optional.of(testUser));
        when(emailOutputPort.sendEmail(anyString(), anyString(), anyString())).thenReturn(false);

        assertThrows(EmailException.class,
                () -> userService.sendResetPasswordEmail("valid-token"));
    }

    @Test
    void update_UserNotExists_ThrowsNotFoundException() {
        when(userOutputPort.existsById(userId)).thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> userService.update(testUser));
    }

    @Test
    void findAll_ReturnsUserList() {
        List<User> expected = List.of(testUser);
        when(userOutputPort.findAll(anyString(), anyString(), anyInt(), anyInt())).thenReturn(expected);

        List<User> result = userService.findAll("param", "email", 0, 10);

        assertEquals(expected, result);
    }

    @Test
    void validateResetPassword_InvalidCode_ThrowsNotFoundException() {
        when(cacheOutputPort.get(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> userService.validateResetPassword(UUID.randomUUID()));
    }

    @Test
    void findById_UserNotExists_ThrowsNotFoundException() {
        when(userOutputPort.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> userService.findById(userId));
    }

    @Test
    void create_RoleNotFound_ThrowsInternalError() {
        when(userOutputPort.existsByEmail(email)).thenReturn(false);

        assertThrows(InternalErrorException.class,
                () -> userService.create(testUser));
    }

    @Test
    void create_UserRoleCreationFails_ThrowsInternalError() {
        when(userOutputPort.existsByEmail(email)).thenReturn(false);

        assertThrows(InternalErrorException.class,
                () -> userService.create(testUser));
    }

    @Test
    void sendConfirmationEmail_Success_SendsEmailWithUrl() {
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(cacheOutputPort.set(anyString(), anyString(), anyInt())).thenReturn(true);
        when(environmentOutputPort.getEmailConfirmationUrl()).thenReturn("http://localhost:8080/users/confirm");
        when(emailOutputPort.sendEmail(anyString(), anyString(), anyString())).thenReturn(true);

        userService.sendConfirmationEmail(userId);

        verify(emailOutputPort).sendEmail(
                eq(testUser.getEmail()),
                eq("Confirmação de e-mail"),
                stringArgumentCaptor.capture());

        String emailMessage = stringArgumentCaptor.getValue();
        assertTrue(emailMessage.contains("http://localhost:8080/users/confirm"), "URL de confirmação não encontrada no email");
        assertTrue(emailMessage.contains(testUser.getFirstName()), "Nome do usuário não encontrado no email");
    }
}