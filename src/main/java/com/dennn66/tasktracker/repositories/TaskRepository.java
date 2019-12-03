package com.dennn66.tasktracker.repositories;

import com.dennn66.tasktracker.entities.Task;
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
        return entityManager.getReference(Task.class, id);
    }

    public List<Task> findAll() {
        List<Task> tasks = entityManager.createQuery("SELECT i FROM Task i").getResultList();
        return tasks;
    }

    public List<Task> findByStatus(String status) {
        List<Task> tasks = entityManager.createQuery("SELECT i FROM Task i WHERE status = ?1").
                setParameter(1, Task.Status.valueOf(status)).getResultList();
        return tasks;
    }
    public List<Task> findByCreator(String creator) {
        List<Task> tasks = entityManager.
                createQuery("SELECT i FROM Task i WHERE creator LIKE ?1").
                setParameter(1, creator).getResultList();
        return tasks;
    }

    public void insert(Task task) {
        entityManager.persist(task);
    }

    public void update(Task task) {
        entityManager.merge(task);
    }

    public void remove(Task task) {
        entityManager.remove(entityManager.find(Task.class, task.getId()));
    }

    public Optional<Task> findOneById(Long id) {
        Task task = entityManager.find(Task.class, id);
        if(task == null) return Optional.empty();
        return Optional.of(task);
    }
}