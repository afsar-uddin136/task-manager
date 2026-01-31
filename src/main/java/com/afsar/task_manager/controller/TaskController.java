package com.afsar.task_manager.controller;

import com.afsar.task_manager.entity.Task;
import com.afsar.task_manager.service.TaskService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTask(){
        List<Task> allTask = taskService.findAllTask();
        if(allTask != null && !allTask.isEmpty()){
            return new ResponseEntity<>(allTask, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task){
        taskService.saveTask(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable ObjectId id){
        Task task = taskService.findTaskById(id).orElse(null);
        if(task != null){
            return new ResponseEntity<>(task,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable ObjectId id){
        boolean removed = taskService.deleteTaskById(id);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateTaskById(@RequestBody Task newTask,@PathVariable ObjectId id){
        Task old = taskService.findTaskById(id).orElse(null);
        if(old != null){
            old.setTitle(newTask.getTitle()!=null && !newTask.getTitle().isEmpty() ? newTask.getTitle() : old.getTitle());
            old.setDescription(newTask.getDescription()!=null && !newTask.getDescription().isEmpty()? newTask.getDescription() : old.getDescription());
            old.setCompleted(newTask.isCompleted());
            taskService.saveTask(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
