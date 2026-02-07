package com.afsar.task_manager.controller;

import com.afsar.task_manager.entity.Task;
import com.afsar.task_manager.entity.User;
import com.afsar.task_manager.service.TaskService;
import com.afsar.task_manager.service.UserDetailsServiceImpl;
import com.afsar.task_manager.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping
    public ResponseEntity<?> getAllTask() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<Task> allTask = user.getTaskList();
        if (allTask != null && !allTask.isEmpty()) {
            return new ResponseEntity<>(allTask, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            taskService.saveTask(task, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        Task task = user.getTaskList().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = taskService.deleteTaskById(id, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateTaskById(@RequestBody Task newTask, @PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        boolean collect = user.getTaskList().stream().anyMatch(x -> x.getId().equals(id));
        if (collect) {
            Optional<Task> old = taskService.findTaskById(id);
            if (old.isPresent()) {
                Task task = old.get();
                task.setTitle(newTask.getTitle() != null && !newTask.getTitle().isEmpty() ? newTask.getTitle() : task.getTitle());
                task.setDescription(newTask.getDescription() != null && !newTask.getDescription().isEmpty() ? newTask.getDescription() : task.getDescription());
                task.setCompleted(newTask.isCompleted());
                taskService.saveNewTask(task);
                return new ResponseEntity<>(task, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
