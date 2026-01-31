package com.afsar.task_manager.repository;

import com.afsar.task_manager.entity.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.rmi.server.ObjID;

public interface TaskRepository extends MongoRepository<Task, ObjectId> {
}
