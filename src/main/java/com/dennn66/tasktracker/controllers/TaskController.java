package com.dennn66.tasktracker.controllers;

import com.dennn66.tasktracker.entities.Task;
import com.dennn66.tasktracker.services.TaskService;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    @Setter
    public void setTaskService(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/")
    @ResponseBody
    public List<Task> jsonAllTasks() {
        return taskService.getAllTasks();
    }

    // http://localhost:8189/app/tasks/show
    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String showAllTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "all_tasks";
    }

    // GET http://localhost:8189/app/tasks/show_form
    @GetMapping("/show_form")
    public String showSimpleForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);

        return "task_form";
    }

    // POST http://localhost:8189/app/tasks/process_form
    @PostMapping("/process_form")
    public String processForm(@ModelAttribute("task") Task task) {
        taskService.insert(task);
        return "task_form_result";
    }
}
