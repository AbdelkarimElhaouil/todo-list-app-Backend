package com.elhaouil.Todo_list_app.Controller;

import com.elhaouil.Todo_list_app.Jwt.JwtService;
import com.elhaouil.Todo_list_app.Service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    public final TaskService service;
    public final JwtService jwtService;

    @GetMapping("/all-tasks")
    public ResponseEntity<List<String>> getTasks(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return service.getTasks(username);
    }

    @PostMapping("/add-task")
    public ResponseEntity<String> addTask(@RequestBody Map<String, String> task, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        String desc = task.get("task");
        return service.addTask(desc, username);
    }

    @PostMapping("/add-tasks")
    public ResponseEntity<String> addTasks(@RequestBody List<String> tasks, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return service.addTasks(tasks, username);
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<String> deleteTask(@RequestBody String desc) {
        return service.deleteTask(desc);
    }

    @PutMapping("/update-task")
    public ResponseEntity<String> updateTask(@RequestBody String desc, HttpServletRequest request)
    {
        return service.updateTask(desc);
    }
}
