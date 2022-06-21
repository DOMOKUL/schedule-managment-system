package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.GroupConverter;
import com.company.schedule.management.system.dto.GroupDto;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupRestController {

    private final GroupService groupService;
    private final GroupConverter groupConverter;

    public GroupRestController(GroupService groupService, GroupConverter groupConverter) {
        this.groupService = groupService;
        this.groupConverter = groupConverter;
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto addGroup(@Valid @RequestBody GroupDto groupDto) {
        Group group = groupConverter.convertToEntity(groupDto);
        Group groupCreated = groupService.saveGroup(group);
        return groupConverter.convertToDto(groupCreated);
    }

    @GetMapping("/{id}")
    public GroupDto getGroupById(@PathVariable Long id) throws HttpClientErrorException {
        return groupConverter.convertToDto(groupService.getGroupById(id));
    }

    @GetMapping()
    public List<GroupDto> getListOfGroups() {
        List<Group> groups = groupService.getAllGroups();
        return groups.stream()
                .map(groupConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Group> updateGroup(@PathVariable("id") Long id, @Valid @RequestBody GroupDto groupDto) {
        if (!id.equals(groupDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        if (groupService.getGroupById(id) == null) {
            throw new EntityNotFoundException("Group with id: " + id + " is not found");
        }

        groupService.saveGroup(groupConverter.convertToEntity(groupDto));
        return ResponseEntity.ok(groupConverter.convertToEntity(groupDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroupById(id);
        return ResponseEntity.ok().build();
    }
}
