package com.afsar.task_manager.controller;

import com.afsar.task_manager.entity.Task;
import com.afsar.task_manager.entity.User;
import com.afsar.task_manager.service.TaskService;
import com.afsar.task_manager.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllTask(@PathVariable String username){
        User user = userService.findByUserName(username);
        List<Task> allTask = user.getTaskList();
        if(allTask != null && !allTask.isEmpty()){
            return new ResponseEntity<>(allTask, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createTask(@RequestBody Task task,@PathVariable String username){
        taskService.saveTask(task,username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}/{username}")
    public ResponseEntity<?> getTaskById(@PathVariable ObjectId id,@PathVariable String username){
        User user = userService.findByUserName(username);
        Task task = user.getTaskList().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if(task != null){
            return new ResponseEntity<>(task,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}/{username}")
    public ResponseEntity<?> deleteTaskById(@PathVariable ObjectId id,@PathVariable String username){
        boolean removed = taskService.deleteTaskById(id,username);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}/{username}")
    public ResponseEntity<?> updateTaskById(@RequestBody Task newTask,@PathVariable ObjectId id,@PathVariable String username){
        Task old = taskService.findTaskById(id).orElse(null);
        if(old != null){
            old.setTitle(newTask.getTitle()!=null && !newTask.getTitle().isEmpty() ? newTask.getTitle() : old.getTitle());
            old.setDescription(newTask.getDescription()!=null && !newTask.getDescription().isEmpty()? newTask.getDescription() : old.getDescription());
            old.setCompleted(newTask.isCompleted());
            taskService.saveTask(old,username);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
