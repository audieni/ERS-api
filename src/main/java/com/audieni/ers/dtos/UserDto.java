package com.audieni.ers.dtos;

import com.audieni.ers.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private boolean manager;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.manager = user.isManager();
    }
}
