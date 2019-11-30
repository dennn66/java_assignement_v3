package com.dennn66.tasktracker.repositories;

import com.dennn66.tasktracker.entities.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TaskRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Task findById(Long id) {
        Task task = entityManager.getReference(Task.class, id);
        return task;
    }

    public List<Task> findAll() {
        List<Task> tasks = entityManager.createQuery("SELECT i FROM Task i").getResultList();
        return tasks;
    }

    public void insert(Task task) {
        entityManager.persist(task);
    }

    public void update(Task task) {
        entityManager.persist(task);
    }

    public Optional<Task> findOneById(Long id) {
        Task task = entityManager.getReference(Task.class, id);
        if(task == null) return Optional.empty();
        return Optional.of(task);
    }
}