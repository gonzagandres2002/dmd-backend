package dmd.clientmanagement.service.implementation;

import dmd.clientmanagement.dto.UserDto;
import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.mapper.ServiceTypeMapper;
import dmd.clientmanagement.mapper.UserMapper;
import dmd.clientmanagement.repository.ServiceTypeRepository;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUserRole(Long id, Role role) {
        if (id == 1) {
            throw new RuntimeException("Cannot update the role of the default admin user.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setRole(role);
        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto assignServicesToUser(Long id, List<Long> serviceTypeIds) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        List<ServiceType> services = new ArrayList<>(serviceTypeRepository.findAllById(serviceTypeIds));
        user.setServices(services);

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }
}
