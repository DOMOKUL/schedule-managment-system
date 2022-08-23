package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.UserDTO;
import com.company.schedule.management.system.model.User;
import com.company.schedule.management.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body((userService.saveUser(entityConverter.convertDtoToEntity(userDTO, User.class))));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(entityConverter.convertEntityToDto(userService.getUserById(id), UserDTO.class));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users.stream()
                .map(user -> entityConverter.convertEntityToDto(user, UserDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDTO userDTO) {
        if (!id.equals(userDTO.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        User user = entityConverter.convertDtoToEntity(userDTO, User.class);
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
