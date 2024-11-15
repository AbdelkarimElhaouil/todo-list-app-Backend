package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.DTO.TaskDTO;
import com.elhaouil.Todo_list_app.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService service;

    @PostMapping("task/{username}")
    public void addTask(@RequestBody String task, @PathVariable String username){
        service.addTask(task, username);
    }

    @PostMapping("tasks/{username}")
    public void addTasks(@RequestBody List<String> tasks, @PathVariable String username){
        service.addTasks(tasks, username);
    }

    @DeleteMapping("/task/{task_id}")
    public void deleteTask(@PathVariable long task_id){
        service.deleteTask(task_id);
    }

    @PutMapping("task")
    public void updateTask(@RequestBody TaskDTO task){
        service.updateTask(task);
    }

}
