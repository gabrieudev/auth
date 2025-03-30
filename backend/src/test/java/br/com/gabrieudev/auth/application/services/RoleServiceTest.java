package br.com.gabrieudev.auth.application.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrieudev.auth.application.exceptions.AlreadyExistsException;
import br.com.gabrieudev.auth.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.auth.application.exceptions.InternalErrorException;
import br.com.gabrieudev.auth.application.exceptions.NotFoundException;
import br.com.gabrieudev.auth.application.ports.output.RoleOutputPort;
import br.com.gabrieudev.auth.application.ports.output.UserRoleOutputPort;
import br.com.gabrieudev.auth.domain.Role;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    private Role testRole;
    private final UUID roleId = UUID.randomUUID();
    private final String roleName = "ADMIN";

    @Mock
    private RoleOutputPort roleOutputPort;

    @Mock
    private UserRoleOutputPort userRoleOutputPort;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        testRole = new Role(roleId, roleName, null);
    }

    @Test
    void create_WhenRoleExists_ThrowsAlreadyExistsException() {
        when(roleOutputPort.existsByName(roleName)).thenReturn(true);

        assertThrows(AlreadyExistsException.class,
            () -> roleService.create(testRole));
    }

    @Test
    void create_Success_ReturnsCreatedRole() {
        when(roleOutputPort.existsByName(roleName)).thenReturn(false);
        when(roleOutputPort.create(any(Role.class))).thenReturn(Optional.of(testRole));

        Role result = roleService.create(testRole);

        assertNotNull(result);
        assertEquals(roleId, result.getId());
        verify(roleOutputPort).create(testRole);
    }

    @Test
    void create_WhenCreationFails_ThrowsInternalError() {
        when(roleOutputPort.existsByName(roleName)).thenReturn(false);
        when(roleOutputPort.create(any(Role.class))).thenReturn(Optional.empty());

        assertThrows(InternalErrorException.class,
            () -> roleService.create(testRole));
    }

    @Test
    void delete_WhenRoleNotExists_ThrowsNotFoundException() {
        when(roleOutputPort.existsById(roleId)).thenReturn(false);

        assertThrows(NotFoundException.class,
            () -> roleService.delete(roleId));
    }

    @Test
    void delete_WhenHasLinkedUsers_ThrowsBusinessRuleException() {
        when(roleOutputPort.existsById(roleId)).thenReturn(true);
        when(userRoleOutputPort.existsByRoleId(roleId)).thenReturn(true);

        assertThrows(BusinessRuleException.class,
            () -> roleService.delete(roleId));
    }

    @Test
    void delete_WhenDeletionFails_ThrowsInternalError() {
        when(roleOutputPort.existsById(roleId)).thenReturn(true);
        when(userRoleOutputPort.existsByRoleId(roleId)).thenReturn(false);
        when(roleOutputPort.delete(roleId)).thenReturn(false);

        assertThrows(InternalErrorException.class,
            () -> roleService.delete(roleId));
    }

    @Test
    void delete_Success_DeletesRole() {
        when(roleOutputPort.existsById(roleId)).thenReturn(true);
        when(userRoleOutputPort.existsByRoleId(roleId)).thenReturn(false);
        when(roleOutputPort.delete(roleId)).thenReturn(true);

        assertDoesNotThrow(() -> roleService.delete(roleId));
        verify(roleOutputPort).delete(roleId);
    }

    @Test
    void findAll_ReturnsRoleList() {
        List<Role> expected = List.of(testRole);
        when(roleOutputPort.findAll(any(), any())).thenReturn(expected);

        List<Role> result = roleService.findAll(null, null);

        assertEquals(expected, result);
    }

    @Test
    void findById_WhenRoleExists_ReturnsRole() {
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));

        Role result = roleService.findById(roleId);

        assertEquals(testRole, result);
    }

    @Test
    void findById_WhenRoleNotExists_ThrowsNotFoundException() {
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> roleService.findById(roleId));
    }

    @Test
    void update_WhenNameConflict_ThrowsAlreadyExistsException() {
        Role updatedRole = new Role(roleId, "NEW_NAME", null);
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(roleOutputPort.existsByName("NEW_NAME")).thenReturn(true);

        assertThrows(AlreadyExistsException.class,
            () -> roleService.update(updatedRole));
    }

    @Test
    void update_Success_ReturnsUpdatedRole() {
        Role updatedRole = new Role(roleId, "UPDATED_NAME", null);
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(roleOutputPort.existsByName("UPDATED_NAME")).thenReturn(false);
        when(roleOutputPort.update(updatedRole)).thenReturn(Optional.of(updatedRole));

        Role result = roleService.update(updatedRole);

        assertEquals("UPDATED_NAME", result.getName());
        verify(roleOutputPort).update(updatedRole);
    }

    @Test
    void update_WhenUpdateFails_ThrowsInternalError() {
        Role updatedRole = new Role(roleId, roleName, null);
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(roleOutputPort.update(updatedRole)).thenReturn(Optional.empty());

        assertThrows(InternalErrorException.class,
            () -> roleService.update(updatedRole));
    }

    @Test
    void update_WhenSameName_UpdatesWithoutCheck() {
        Role updatedRole = new Role(roleId, roleName, null);
        when(roleOutputPort.findById(roleId)).thenReturn(Optional.of(testRole));
        when(roleOutputPort.update(updatedRole)).thenReturn(Optional.of(updatedRole));

        Role result = roleService.update(updatedRole);

        assertEquals(roleName, result.getName());
        verify(roleOutputPort, never()).existsByName(anyString());
    }
}