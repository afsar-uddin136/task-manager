package com.afsar.task_manager.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@Document(collection = "task")
public class Task {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;

}
