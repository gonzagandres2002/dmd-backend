package dmd.clientmanagement.dto;

import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private Role role;
    private List<ServiceType> services;
}