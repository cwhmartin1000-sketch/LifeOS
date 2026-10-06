package com.lifeos.task;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tasks")
class Task {
    @Id private UUID id;
    @Column(nullable = false, length = 200) private String title;
    @Column(nullable = false, length = 2000) private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20) private TaskStatus status;
    private LocalDate dueDate;
    @Column(nullable = false) private Instant createdAt;
    @Column(nullable = false) private Instant updatedAt;
    @Version private long version;

    protected Task() {}

    Task(String title, String description, LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.status = TaskStatus.TODO;
        update(title, description, dueDate);
    }

    void update(String title, String description, LocalDate dueDate) {
        this.title = title.strip();
        this.description = description == null ? "" : description.strip();
        this.dueDate = dueDate;
        this.updatedAt = Instant.now();
    }

    void changeStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    TaskResponse response() {
        return new TaskResponse(id, title, description, status, dueDate, createdAt, updatedAt);
    }
}
