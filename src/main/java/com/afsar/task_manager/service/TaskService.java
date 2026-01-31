package com.afsar.task_manager.service;

import com.afsar.task_manager.entity.Task;
import com.afsar.task_manager.repository.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public void saveTask(Task task){
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public List<Task> findAllTask(){
        return taskRepository.findAll();
    }
    public Optional<Task> findTaskById(ObjectId id){
        return taskRepository.findById(id);
    }
    public boolean deleteTaskById(ObjectId id){
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
