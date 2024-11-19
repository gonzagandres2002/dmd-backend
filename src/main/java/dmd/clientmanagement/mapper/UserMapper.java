package dmd.clientmanagement.mapper;

import dmd.clientmanagement.dto.UserDto;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRole().name(),
                user.getServices()
        );
    }

    public User toEntity(UserDto userDto) {
        Role role = Role.valueOf(userDto.getRole().toUpperCase());

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setRole(role);
        user.setServices(userDto.getServices());
        return user;
    }
}
