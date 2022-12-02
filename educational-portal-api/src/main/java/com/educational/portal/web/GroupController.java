package com.educational.portal.web;

import com.educational.portal.domain.dto.CreateGroupRequest;
import com.educational.portal.domain.dto.GroupDto;
import com.educational.portal.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> findById(@PathVariable Long id) {
        GroupDto groupDto = groupService.findById(id);
        return ResponseEntity.ok(groupDto);
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<GroupDto> groupDtos = groupService.getAllGroups();
        return ResponseEntity.ok(groupDtos);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(Principal principal,
                                                @RequestBody @Valid CreateGroupRequest createGroupRequest,
                                                UriComponentsBuilder uriComponentsBuilder) {
        GroupDto group = groupService.createGroup(principal, createGroupRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("/groups/{id}").buildAndExpand(group.getId());
        var location = uriComponents.toUri();

        return ResponseEntity.created(location).build();
    }
}