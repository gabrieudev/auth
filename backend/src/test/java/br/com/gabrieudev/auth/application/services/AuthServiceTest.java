package br.com.gabrieudev.auth.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import br.com.gabrieudev.auth.application.exceptions.BadCredentialsException;
import br.com.gabrieudev.auth.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.auth.application.ports.output.AuthOutputPort;
import br.com.gabrieudev.auth.application.ports.output.CacheOutputPort;
import br.com.gabrieudev.auth.application.ports.output.EnvironmentOutputPort;
import br.com.gabrieudev.auth.application.ports.output.UserOutputPort;
import br.com.gabrieudev.auth.domain.Tokens;
import br.com.gabrieudev.auth.domain.User;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private User testUser;
    private final UUID userId = UUID.randomUUID();
    private final String accessToken = "access-token";
    private final String refreshToken = "refresh-token";
    private final Integer accessExpiration = 5;
    private final Integer refreshExpiration = 7200;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Mock
    private AuthOutputPort authOutputPort;

    @Mock
    private UserOutputPort userOutputPort;

    @Mock
    private CacheOutputPort cacheOutputPort;

    @Mock
    private EnvironmentOutputPort environmentOutputPort;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        testUser = new User(userId, "teste user", "test user", "test@gmail.com", "hashed-password", true,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void login_UserNotFound_ThrowsBadCredentialsException() {
        when(userOutputPort.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class,
                () -> authService.login("invalid@email.com", "password"));
    }

    @Test
    void login_InvalidPassword_ThrowsBadCredentialsException() {
        when(userOutputPort.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(userOutputPort.verifyPassword(anyString(), anyString())).thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> authService.login("test@example.com", "wrong-password"));
    }

    @Test
    void login_Success_ReturnsTokensAndUpdatesCache() {
        when(userOutputPort.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(userOutputPort.verifyPassword(anyString(), anyString())).thenReturn(true);
        when(authOutputPort.generateAccessToken(testUser)).thenReturn(Optional.of(accessToken));
        when(authOutputPort.generateRefreshToken(testUser)).thenReturn(Optional.of(refreshToken));
        when(environmentOutputPort.getAccessTokenExpiration()).thenReturn(accessExpiration);
        when(environmentOutputPort.getRefreshTokenExpiration()).thenReturn(refreshExpiration);

        Tokens result = authService.login("test@example.com", "password");

        assertNotNull(result);
        assertEquals(accessToken, result.getAccessToken());
        assertEquals(refreshToken, result.getRefreshToken());

        verify(cacheOutputPort).delete("accessToken:" + userId);
        verify(cacheOutputPort).delete("refreshToken:" + userId);

        verify(cacheOutputPort).set(eq("accessToken:" + userId), eq(accessToken), eq(accessExpiration));
        verify(cacheOutputPort).set(eq("refreshToken:" + userId), eq(refreshToken), eq(refreshExpiration));
    }

    @Test
    void refreshTokens_InvalidToken_ThrowsInvalidTokenException() {
        when(authOutputPort.isValidToken(anyString())).thenReturn(false);

        assertThrows(InvalidTokenException.class,
                () -> authService.refreshTokens("invalid-token"));
    }

    @Test
    void refreshTokens_ValidToken_GeneratesNewTokensAndUpdatesCache() {
        String oldRefreshToken = "old-refresh-token";
        String newRefreshToken = "new-refresh-token";

        when(authOutputPort.isValidToken(oldRefreshToken)).thenReturn(true);
        when(authOutputPort.getUserByToken(oldRefreshToken)).thenReturn(Optional.of(testUser));
        when(authOutputPort.generateAccessToken(testUser)).thenReturn(Optional.of(accessToken));
        when(authOutputPort.generateRefreshToken(testUser)).thenReturn(Optional.of(newRefreshToken));
        when(environmentOutputPort.getAccessTokenExpiration()).thenReturn(accessExpiration);
        when(environmentOutputPort.getRefreshTokenExpiration()).thenReturn(refreshExpiration);

        when(authOutputPort.isValidToken(newRefreshToken)).thenReturn(true);
        when(authOutputPort.getUserByToken(newRefreshToken)).thenReturn(Optional.of(testUser));
        when(cacheOutputPort.hasKey(anyString())).thenReturn(true);

        authService.refreshTokens(oldRefreshToken);

        verify(cacheOutputPort, times(4)).delete(stringArgumentCaptor.capture());

        assertTrue(stringArgumentCaptor.getAllValues().containsAll(
                List.of(
                        "accessToken:" + userId,
                        "refreshToken:" + userId,
                        "accessToken:" + userId,
                        "refreshToken:" + userId)));
    }

    @Test
    void logout_ValidToken_DeletesTokensFromCache() {
        when(authOutputPort.isValidToken(refreshToken)).thenReturn(true);
        when(authOutputPort.getUserByToken(refreshToken)).thenReturn(Optional.of(testUser));
        when(cacheOutputPort.hasKey("accessToken:" + userId)).thenReturn(true);

        authService.logout(refreshToken);

        verify(cacheOutputPort).delete("accessToken:" + userId);
        verify(cacheOutputPort).delete("refreshToken:" + userId);
    }

    @Test
    void generateAccessToken_Success_UpdatesCache() {
        when(authOutputPort.generateAccessToken(testUser)).thenReturn(Optional.of(accessToken));
        when(environmentOutputPort.getAccessTokenExpiration()).thenReturn(accessExpiration);

        String result = authService.generateAccessToken(testUser);

        assertEquals(accessToken, result);

        verify(cacheOutputPort).set(stringArgumentCaptor.capture(), stringArgumentCaptor.capture(),
                integerArgumentCaptor.capture());

        assertEquals("accessToken:" + userId, stringArgumentCaptor.getAllValues().get(0));
        assertEquals(accessToken, stringArgumentCaptor.getAllValues().get(1));
        assertEquals(accessExpiration, integerArgumentCaptor.getValue());
    }

    @Test
    void isValidToken_TokenInCache_ReturnsTrue() {
        String validToken = "valid-token";
        when(authOutputPort.isValidToken(validToken)).thenReturn(true);
        when(authOutputPort.getUserByToken(validToken)).thenReturn(Optional.of(testUser));
        when(cacheOutputPort.hasKey("accessToken:" + userId)).thenReturn(true);

        assertTrue(authService.isValidToken(validToken));
    }

    @Test
    void isValidToken_TokenNotInCache_ReturnsFalse() {
        String invalidToken = "invalid-token";
        when(authOutputPort.isValidToken(invalidToken)).thenReturn(true);
        when(authOutputPort.getUserByToken(invalidToken)).thenReturn(Optional.of(testUser));
        when(cacheOutputPort.hasKey(anyString())).thenReturn(false);

        assertFalse(authService.isValidToken(invalidToken));
    }

    @Test
    void logout_InvalidToken_ThrowsInvalidTokenException() {
        when(authOutputPort.isValidToken(anyString())).thenReturn(false);

        assertThrows(InvalidTokenException.class,
                () -> authService.logout("invalid-token"));
    }

    @Test
    void getUserByToken_InvalidToken_ThrowsInvalidTokenException() {
        when(authOutputPort.isValidToken(anyString())).thenReturn(false);

        assertThrows(InvalidTokenException.class,
                () -> authService.getUserByToken("invalid-token"));
    }

    @Test
    void isValidToken_RefreshTokenInCache_ReturnsTrue() {
        String validToken = "valid-token";
        when(authOutputPort.isValidToken(validToken)).thenReturn(true);
        when(authOutputPort.getUserByToken(validToken)).thenReturn(Optional.of(testUser));

        lenient().when(cacheOutputPort.hasKey("refreshToken:" + userId)).thenReturn(true);
        lenient().when(cacheOutputPort.hasKey("accessToken:" + userId)).thenReturn(false);

        assertTrue(authService.isValidToken(validToken));
    }

    @Test
    void isValidToken_NoTokensInCache_ReturnsFalse() {
        String validToken = "valid-token";
        when(authOutputPort.isValidToken(validToken)).thenReturn(true);
        when(authOutputPort.getUserByToken(validToken)).thenReturn(Optional.of(testUser));
        when(cacheOutputPort.hasKey(anyString())).thenReturn(false);

        assertFalse(authService.isValidToken(validToken));
    }
}