package com.afsar.task_manager.repository;

import com.afsar.task_manager.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface TaskRepository extends MongoRepository<Task,String> {
}
