package com.afsar.task_manager.controller;

import com.afsar.task_manager.entity.Task;
import com.afsar.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTask(){
        return taskService.findAllTask();
    }

    @PostMapping
    public boolean createTask(@RequestBody Task task){
        taskService.saveTask(task);
        return true;
    }

    @GetMapping("/id/{id}")
    public Task getTaskById(@PathVariable String id){
        return taskService.findTaskById(id).orElse(null);
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteTaskById(@PathVariable String id){
        taskService.deleteTaskById(id);
        return true;
    }

    @PutMapping("/id/{id}")
    public Task updateTaskById(@RequestBody Task newTask,@PathVariable String id){
        Task old = taskService.findTaskById(id).orElse(null);
        if(old != null){
            old.setTitle(newTask.getTitle()!=null && !newTask.getTitle().isEmpty() ? newTask.getTitle() : old.getTitle());
            old.setDescription(newTask.getDescription()!=null && !newTask.getDescription().isEmpty()? newTask.getDescription() : old.getDescription());
            old.setCompleted(newTask.isCompleted());
            taskService.saveTask(old);
            return old;
        }
        return null;
    }


}
