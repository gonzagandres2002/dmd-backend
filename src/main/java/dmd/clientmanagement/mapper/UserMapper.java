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
                user.getRole(),
                user.getServiceTypes()
        );
    }
}
