package com.educational.portal.service;

import com.educational.portal.domain.dto.CreateGroupRequest;
import com.educational.portal.domain.dto.GroupDto;
import com.educational.portal.domain.entity.Category;
import com.educational.portal.domain.entity.Group;
import com.educational.portal.domain.entity.User;
import com.educational.portal.exception.AlreadyExistsException;
import com.educational.portal.exception.NotFoundException;
import com.educational.portal.repository.CategoryRepository;
import com.educational.portal.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    public GroupService(GroupRepository groupRepository, UserService userService, CategoryRepository categoryRepository) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    public GroupDto findById(Long id) {
        return groupRepository.findById(id)
                .map(GroupDto::convertGroupToGroupDto)
                .orElseThrow(() -> new NotFoundException("Group with this id = " + id + " is not found"));

    }

    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(GroupDto::convertGroupToGroupDto)
                .collect(Collectors.toList());
    }

    public GroupDto createGroup(Principal principal, CreateGroupRequest createGroupRequest) {
        createGroupValidation(createGroupRequest.getName());
        User userManager = userService.findByEmail(principal.getName());
        User userInstructor = userService.findUserById(createGroupRequest.getInstructorId());
        Optional<Category> optionalCategory = categoryRepository.findById(createGroupRequest.getCategoryId());
        Category category = optionalCategory.get();
        Group group = new Group(createGroupRequest.getName(), userManager, category, userInstructor);
        groupRepository.save(group);
        return GroupDto.convertGroupToGroupDto(group);

    }

    private void createGroupValidation(String groupName) {
        Optional<Group> optionalGroup = groupRepository.findGroupByName(groupName);
        if (optionalGroup.isPresent()) {
            throw new AlreadyExistsException("Category With this name " + groupName + " already exists");
        }

    }

}