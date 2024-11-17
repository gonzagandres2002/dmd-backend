package dmd.clientmanagement.service;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dmd.clientmanagement.dto.UserDto;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    UserDto updateUserRole(Long userId, Role role);

    UserDto assignServicesToUser(Long userId, List<Long> serviceTypeIds);
}