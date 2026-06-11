package com.yakuen.ceisa.dto.auth;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginResponse {

  Long userId;
  String username;
  String fullName;
  String email;
  String authToken;
  List<String> roles;
}
