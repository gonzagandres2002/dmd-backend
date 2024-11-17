package dmd.clientmanagement.controllers;

import dmd.clientmanagement.dto.UserDto;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> fetchUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> fetchUserById(@PathVariable Long id) {
        try {
            UserDto user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserDto> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> roleData) {
        try {
            Role role = Role.valueOf(roleData.get("role").toUpperCase());
            UserDto updatedUser = userService.updateUserRole(id, role);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            // Handle invalid role cases
            return ResponseEntity.badRequest().body(null);
        } catch (UserNotFoundException e) {
            // Handle case where user is not found
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/services")
    public ResponseEntity<UserDto> assignServicesToUser(
            @PathVariable Long id,
            @RequestBody Map<String, List<Long>> serviceData) {
        try {
            List<Long> serviceIds = serviceData.get("serviceIds");
            UserDto updatedUser = userService.assignServicesToUser(id, serviceIds);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            // Handle case where user is not found
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Handle other exceptions, if needed
            return ResponseEntity.status(500).body(null);
        }
    }
}