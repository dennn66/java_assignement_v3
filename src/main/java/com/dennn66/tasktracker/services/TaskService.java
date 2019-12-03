package com.dennn66.tasktracker.services;

import com.dennn66.tasktracker.entities.Task;
import com.dennn66.tasktracker.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public List<Task> findTasks(String creatorFilter, String statusFilter) {
        if(!creatorFilter.equals("")) return taskRepository.findByCreator(creatorFilter);
        if(!statusFilter.equals("ALL")) return taskRepository.findByStatus(statusFilter);
        return taskRepository.findAll();
    }

    public void insert(Task task) {
        task.setStatus(Task.Status.OPEN);
        taskRepository.insert(task);
    }
    public void delete(Task task) {
        taskRepository.remove(task);
    }
    public void update(Task task) {
        taskRepository.update(task);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findOneById(id);
    }
}
