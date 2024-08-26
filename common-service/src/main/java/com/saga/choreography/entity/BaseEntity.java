package com.saga.choreography.entity;

import com.saga.choreography.util.DateTimeUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.util.UUID;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private String id;
    private String creationAt;
    private String updatedAt;

    @PrePersist
    protected void onCreate() {
        id = UUID.randomUUID().toString();
        creationAt = DateTimeUtil.getDateTime();
        updatedAt = DateTimeUtil.getDateTime();
    }


    @PreUpdate
    protected void onUpdate() {
        updatedAt = DateTimeUtil.getDateTime();
    }
}
