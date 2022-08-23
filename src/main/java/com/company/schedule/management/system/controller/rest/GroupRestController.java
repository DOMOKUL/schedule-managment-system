package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.GroupDTO;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupRestController {

    private final GroupService groupService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<GroupDTO> addGroup(@Valid @RequestBody GroupDTO groupDto) {
        Group group = entityConverter.convertDtoToEntity(groupDto, Group.class);
        return new ResponseEntity<>(entityConverter.convertEntityToDto(groupService.saveGroup(group), GroupDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(entityConverter.convertEntityToDto(groupService.getGroupById(id), GroupDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<GroupDTO>> getListOfGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups.stream()
                .map(group -> entityConverter.convertEntityToDto(group, GroupDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable("id") Long id, @Valid @RequestBody GroupDTO groupDto) {
        if (!id.equals(groupDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        groupService.saveGroup(entityConverter.convertDtoToEntity(groupDto, Group.class));
        return ResponseEntity.ok(entityConverter.convertDtoToEntity(groupDto, Group.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroupById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
