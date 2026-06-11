package com.yakuen.ceisa.utils;

import com.yakuen.ceisa.dto.auth.LoginRequest;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class SessionIdUtil {

  // Encode username dan password menjadi sessionId (Base64)
  public String encodeCredentials(String username, String password) {
    try {
      // Format: username|password|timestamp
      String data =
        username + "|" + password + "|" + System.currentTimeMillis();

      // Encode ke Base64 URL-safe
      return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(data.getBytes("UTF-8"));
    } catch (Exception e) {
      throw new RuntimeException("Failed to encode credentials", e);
    }
  }

  // Extract credentials dari sessionId
  public String[] extractCredentials(String sessionId) {
    try {
      // Decode dari Base64
      byte[] decodedBytes = Base64.getUrlDecoder().decode(sessionId);
      String data = new String(decodedBytes, "UTF-8");

      String[] parts = data.split("\\|");

      if (parts.length < 3) {
        throw new RuntimeException("Invalid session ID format");
      }

      // Cek expiry (sessionId hanya valid 5 menit)
      long timestamp = Long.parseLong(parts[2]);
      long now = System.currentTimeMillis();
      if (now - timestamp > 5 * 60 * 1000) {
        // 5 menit
        throw new RuntimeException("Session ID expired");
      }

      // Return [username, password]
      return new String[] { parts[0], parts[1] };
    } catch (Exception e) {
      throw new RuntimeException(
        "Invalid or expired session ID: " + e.getMessage()
      );
    }
  }

  // Decode ke LoginRequest
  public LoginRequest decodeToLoginRequest(String sessionId) {
    String[] credentials = extractCredentials(sessionId);
    LoginRequest request = new LoginRequest();
    request.setUsernameOrEmail(credentials[0]);
    request.setPassword(credentials[1]);
    return request;
  }
}
