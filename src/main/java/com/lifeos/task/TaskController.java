package com.lifeos.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
class TaskController {
    private final TaskService service;
    TaskController(TaskService service) { this.service = service; }

    record StatusRequest(@NotNull TaskStatus status) {}
    record TaskPage(List<TaskResponse> items, int page, int size, long totalElements, int totalPages) {}

    @PostMapping
    ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
        TaskResponse task = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/tasks/" + task.id())).body(task);
    }

    @GetMapping("/{id}")
    TaskResponse get(@PathVariable UUID id) { return service.get(id); }

    @GetMapping
    TaskPage list(@RequestParam(required = false) TaskStatus status,
                  @RequestParam(defaultValue = "0") @Min(0) int page,
                  @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        var result = service.list(status, page, size);
        return new TaskPage(result.getContent(), page, size, result.getTotalElements(), result.getTotalPages());
    }

    @PutMapping("/{id}")
    TaskResponse update(@PathVariable UUID id, @Valid @RequestBody TaskRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    TaskResponse changeStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest request) {
        return service.changeStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
