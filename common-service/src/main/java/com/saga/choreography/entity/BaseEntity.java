package com.saga.choreography.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private String id;
    private LocalDateTime creationAt;
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        id = UUID.randomUUID().toString();
        creationAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }


    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
