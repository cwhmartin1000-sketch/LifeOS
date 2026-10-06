package com.lifeos.task;

import java.util.UUID;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
class TaskService {
    private final TaskRepository repository;

    TaskService(TaskRepository repository) { this.repository = repository; }

    TaskResponse create(TaskRequest request) {
        return repository.save(new Task(request.title(), request.description(), request.dueDate())).response();
    }

    @Transactional(readOnly = true)
    TaskResponse get(UUID id) { return find(id).response(); }

    @Transactional(readOnly = true)
    Page<TaskResponse> list(TaskStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending().and(Sort.by("id")));
        return (status == null ? repository.findAll(pageable) : repository.findByStatus(status, pageable))
                .map(Task::response);
    }

    TaskResponse update(UUID id, TaskRequest request) {
        Task task = find(id);
        task.update(request.title(), request.description(), request.dueDate());
        return task.response();
    }

    TaskResponse changeStatus(UUID id, TaskStatus status) {
        Task task = find(id);
        task.changeStatus(status);
        return task.response();
    }

    void delete(UUID id) { repository.delete(find(id)); }

    private Task find(UUID id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }
}
