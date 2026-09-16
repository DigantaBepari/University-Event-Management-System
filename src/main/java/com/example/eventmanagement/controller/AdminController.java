package com.example.eventmanagement.controller;

import com.example.eventmanagement.Category;
import com.example.eventmanagement.Event;
import com.example.eventmanagement.User;
import com.example.eventmanagement.dto.UserResponse;
import com.example.eventmanagement.service.CategoryService;
import com.example.eventmanagement.service.EventService;
import com.example.eventmanagement.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;

    public AdminController(UserService userService,
                           CategoryService categoryService,
                           EventService eventService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.eventService = eventService;
    }

    @PostMapping("/organizers")
    public User createOrganizer(@RequestBody User user) {
        user.setRole("ORGANIZER");
        return userService.saveUser(user);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {

        return userService.getAllUsers()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
}