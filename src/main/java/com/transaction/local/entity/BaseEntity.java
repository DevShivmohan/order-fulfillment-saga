package com.transaction.local.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity {
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private String id;
    private LocalDateTime creationAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        id = UUID.randomUUID().toString();
        creationAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        updatedAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }


    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
}