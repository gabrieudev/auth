package br.com.gabrieudev.auth.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
class UserRoleServiceTest {
    private User testUser;
    private Role testRole;
    private UserRole testUserRole;
    private final UUID userId = UUID.randomUUID();
    private final UUID roleId = UUID.randomUUID();
    private final UUID userRoleId = UUID.randomUUID();

    @Captor
    private ArgumentCaptor<UserRole> userRoleArgumentCaptor;

    @Mock
    private UserRoleOutputPort userRoleOutputPort;

    @Mock
    private RoleOutputPort roleOutputPort;

    @Mock
    private UserOutputPort userOutputPort;

    @InjectMocks
    private UserRoleService userRoleService;

    @BeforeEach
    void setUp() {
        testUser = new User(userId, "John", "Doe", "john@example.com", "password", true, null, null);
        testRole = new Role(roleId, "ADMIN", null);
        testUserRole = new UserRole(userRoleId, testUser, testRole);
    }

    @Test
    void assign_RoleNotFound_ThrowsNotFoundException() {
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> userRoleService.assign(userId, roleId));
    }

    @Test
    void assign_UserNotFound_ThrowsNotFoundException() {
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(userOutputPort.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> userRoleService.assign(userId, roleId));
    }

    @Test
    void assign_RoleAlreadyAssigned_ThrowsAlreadyExistsException() {
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRoleOutputPort.existsByUserIdAndRoleId(userId, roleId)).thenReturn(true);

        assertThrows(AlreadyExistsException.class,
            () -> userRoleService.assign(userId, roleId));
    }

    @Test
    void assign_CreationFails_ThrowsInternalErrorException() {
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRoleOutputPort.existsByUserIdAndRoleId(userId, roleId)).thenReturn(false);
        when(userRoleOutputPort.create(any(UserRole.class))).thenReturn(Optional.empty());

        assertThrows(InternalErrorException.class,
            () -> userRoleService.assign(userId, roleId));
    }

    @Test
    void assign_Success_ReturnsUserRole() {
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRoleOutputPort.existsByUserIdAndRoleId(userId, roleId)).thenReturn(false);
        when(userRoleOutputPort.create(any(UserRole.class))).thenReturn(Optional.of(testUserRole));

        UserRole result = userRoleService.assign(userId, roleId);

        assertNotNull(result);
        assertEquals(userRoleId, result.getId());
        
        verify(userRoleOutputPort).create(userRoleArgumentCaptor.capture());
        
        UserRole created = userRoleArgumentCaptor.getValue();
        assertEquals(testUser, created.getUser());
        assertEquals(testRole, created.getRole());
    }

    @Test
    void unassign_UserNotFound_ThrowsNotFoundException() {
        when(userOutputPort.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> userRoleService.unassign(userId, roleId));
    }

    @Test
    void unassign_RoleNotFound_ThrowsNotFoundException() {
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> userRoleService.unassign(userId, roleId));
    }

    @Test
    void unassign_UserRoleNotFound_ThrowsNotFoundException() {
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(userRoleOutputPort.findByUserIdAndRoleId(userId, roleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> userRoleService.unassign(userId, roleId));
    }

    @Test
    void unassign_DeletionFails_ThrowsInternalErrorException() {
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(userRoleOutputPort.findByUserIdAndRoleId(userId, roleId)).thenReturn(Optional.of(testUserRole));
        when(userRoleOutputPort.delete(userRoleId)).thenReturn(false);

        assertThrows(InternalErrorException.class,
            () -> userRoleService.unassign(userId, roleId));
    }

    @Test
    void unassign_Success_DeletesUserRole() {
        when(userOutputPort.findById(userId)).thenReturn(Optional.of(testUser));
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(userRoleOutputPort.findByUserIdAndRoleId(userId, roleId)).thenReturn(Optional.of(testUserRole));
        when(userRoleOutputPort.delete(userRoleId)).thenReturn(true);

        assertDoesNotThrow(() -> userRoleService.unassign(userId, roleId));
        verify(userRoleOutputPort).delete(userRoleId);
    }
}