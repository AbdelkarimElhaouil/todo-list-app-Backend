package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.TaskDTO;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    TaskService service;


    @GetMapping("all/{username}")
    public List<String> getTasks(@PathVariable String username) {
        return service.getTasks(username);
    }

    @PostMapping("{username}")
    public void addTask(@RequestBody Map<String, String> task, @PathVariable String username) {
        String desc = task.get("task");
        service.addTask(desc, username);
    }

    @PostMapping("list/{username}")
    public void addTasks(@RequestBody List<String> tasks, @PathVariable String username) {
        service.addTasks(tasks, username);
    }

    @DeleteMapping("{username}")
    public void deleteTask(@RequestBody String desc, @PathVariable String username) {
        service.deleteTask(desc, username);
    }

    @PutMapping("task")
    public void updateTask(@RequestBody TaskDTO task) {
        service.updateTask(task);
    }

}
