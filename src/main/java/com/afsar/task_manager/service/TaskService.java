package com.afsar.task_manager.service;

import com.afsar.task_manager.entity.Task;
import com.afsar.task_manager.entity.User;
import com.afsar.task_manager.repository.TaskRepository;
import com.afsar.task_manager.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public void saveTask(Task task,String username){
        task.setCreatedAt(LocalDateTime.now());
        User user = userService.findByUserName(username);
        Task saved = taskRepository.save(task);
        user.getTaskList().add(saved);
        userService.saveUser(user);

    }

    public List<Task> findAllTask(){
        return taskRepository.findAll();
    }
    public Optional<Task> findTaskById(ObjectId id){
        return taskRepository.findById(id);
    }
    @Transactional
    public boolean deleteTaskById(ObjectId id, String username){
        try{
            User user = userService.findByUserName(username);
            boolean removed = user.getTaskList().removeIf(x -> x.getId().equals(id));
            if(removed){
                taskRepository.deleteById(id);
                userService.saveUser(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the task "+e);
        }

    }
}
