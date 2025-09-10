package com.Manager.task_manager.Dto.AuthDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String lastName;
    private String firstname;
    private String email;
    private String password;
    private String username;

}
