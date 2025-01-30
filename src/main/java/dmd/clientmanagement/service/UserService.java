package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.UserDto;
import dmd.clientmanagement.entity.user.Role;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    UserDto updateUserRole(Long userId, Role role);

    UserDto assignServicesToUser(Long userId, List<Long> serviceTypeIds);

    void deleteUser(Long userId);
}