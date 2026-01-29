package com.afsar.task_manager.service;

import com.afsar.task_manager.entity.Task;
import com.afsar.task_manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public void saveTask(Task task){
        taskRepository.save(task);
    }

    public List<Task> findAllTask(){
        return taskRepository.findAll();
    }
    public Optional<Task> findTaskById(String id){
        return taskRepository.findById(id);
    }
    public void deleteTaskById(String id){
        taskRepository.deleteById(id);
    }
}
