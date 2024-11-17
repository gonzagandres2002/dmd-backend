package dmd.clientmanagement.dto;

import dmd.clientmanagement.entity.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String role;
    private List<ServiceType> services;
}