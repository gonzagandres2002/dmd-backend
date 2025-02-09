package dmd.clientmanagement.unit.service;

import dmd.clientmanagement.dto.UserDto;
import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.mapper.UserMapper;
import dmd.clientmanagement.repository.ServiceTypeRepository;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void testGetAllUsers() {
        // Given
        User user = new User();
        UserDto userDto = new UserDto(1L, "username", Role.USER, null);
        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        List<UserDto> result = userServiceImpl.getAllUsers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(1L);
        assertThat(result.getFirst().getUsername()).isEqualTo("username");
    }

    @Test
    public void testGetUserById() {
        // Given
        Long userId = 1L;
        User user = new User(); // Mock User object
        user.setId(userId);
        user.setUsername("username");

        UserDto userDto = new UserDto(1L, "username", Role.USER, null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        UserDto result = userServiceImpl.getUserById(userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo("username");
    }

    @Test
    public void testGetUserById_NotFound() {
        // Given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> userServiceImpl.getUserById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with ID " + userId + " not found.");
    }

    @Test
    public void testUpdateUserRole() {
        // Given
        Long userId = 2L;
        Role newRole = Role.ADMIN;
        User user = new User(); // Mock User object
        user.setId(userId);
        user.setRole(Role.USER);

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setRole(newRole);

        UserDto userDto = new UserDto(userId, user.getUsername(), newRole, user.getServiceTypes());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(userDto);

        // When
        UserDto result = userServiceImpl.updateUserRole(userId, newRole);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getRole()).isEqualTo(newRole);
    }

    @Test
    public void testUpdateUserRole_DefaultAdmin() {
        // Given
        Long userId = 1L; // Default admin
        Role newRole = Role.ADMIN;

        // When / Then
        assertThatThrownBy(() -> userServiceImpl.updateUserRole(userId, newRole))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cannot update the role of the default admin user.");
    }

    @Test
    public void testAssignServicesToUser() {
        // Given
        Long userId = 1L;
        List<Long> serviceTypeIds = Arrays.asList(10L, 20L);
        List<ServiceType> serviceTypes = List.of(new ServiceType(1L, "service 1", null, null));
        User user = new User(); // Mock User object
        user.setId(userId);

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setServiceTypes(serviceTypes);

        UserDto userDto = new UserDto(1L, "username", Role.USER, serviceTypes); // Mock UserDto

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(serviceTypeRepository.findAllById(serviceTypeIds)).thenReturn(serviceTypes);
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(userDto);

        // When
        UserDto result = userServiceImpl.assignServicesToUser(userId, serviceTypeIds);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
    }

    @Test
    public void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        userServiceImpl.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
