package com.dennn66.tasktracker.services;

import com.dennn66.tasktracker.entities.Task;
import com.dennn66.tasktracker.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> getAllTasks(Specification<Task> spec, Pageable pageable) {
        return taskRepository.findAll(spec, pageable);
    }

    public void insert(Task task) {
        task.setStatus(Task.Status.OPEN);
        taskRepository.save(task);
    }
    public void delete(Task task) {
        taskRepository.delete(task);
    }
    public void update(Task task) {taskRepository.save(task);}
    public Optional<Task> findById(Long id) {return taskRepository.findById(id);}
}
