package com.dennn66.tasktracker.controllers;

import com.dennn66.tasktracker.entities.Task;
import com.dennn66.tasktracker.repositories.specifications.TaskSpecifications;
import com.dennn66.tasktracker.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService){
        this.taskService = taskService;
    }

    // http://localhost:8189/app/tasks/show?creatorFilter=ddd&statusFilter=Открыта
    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String showAllTasks(
            Model model,
            @RequestParam(defaultValue = "1") Long pageNumber,
            @RequestParam(value = "nameFilter", required = false, defaultValue="") String nameFilter,
            @RequestParam(value = "creatorFilter", required = false, defaultValue="") String creatorFilter,
            @RequestParam(value = "statusFilter", required = false, defaultValue="ALL") String statusFilter
    ) {
        int tasksPerPage = 5;
        if (pageNumber < 1L) {
            pageNumber = 1L;
        }
        Specification<Task> spec = Specification.where(null);
        if (!creatorFilter.equals("")) {
            spec = spec.and(TaskSpecifications.creatorContains(creatorFilter));
        }
        if (!nameFilter.equals("")) {
            spec = spec.and(TaskSpecifications.nameContains(nameFilter));
        }
        if (!statusFilter.equals("ALL")) {
            spec = spec.and(TaskSpecifications.statusEqual(Task.Status.valueOf(statusFilter)));
        }
        Page<Task> tasksPage = taskService.getAllTasks(spec, PageRequest.of(
                pageNumber.intValue() - 1,
                tasksPerPage,
                Sort.Direction.ASC,
                "id"));
        model.addAttribute("tasksPage", tasksPage);
        model.addAttribute("statusFilter", statusFilter);
        model.addAttribute("creatorFilter", creatorFilter);
        model.addAttribute("nameFilter", nameFilter);
        return "all_tasks";
    }

    // http://localhost:8189/app/task/1/details
    @GetMapping("/{id}/details")
    public String details(Model model, @PathVariable String id){
        Optional<Task> task = taskService.findById(Long.valueOf(id));
        if(task.equals(Optional.empty())) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        model.addAttribute("task", task.get());
        return "task_details";
    }

    // GET http://localhost:8189/app/tasks/show_form
    @GetMapping("/add")
    public String add(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "task_form";
    }

    // POST http://localhost:8189/app/task/process_form
    @PostMapping("/process_form")
    public String processForm(@ModelAttribute("task") Task task, @ModelAttribute("action") String action, @ModelAttribute("stat") String status) {

        switch (action){
            case "Update":
                taskService.update(task);
                break;
            case "Add":
                taskService.insert(task);
                break;
            case "Delete":
                taskService.delete(task);
                break;
        }
        return "redirect:/task/show";
    }
}
