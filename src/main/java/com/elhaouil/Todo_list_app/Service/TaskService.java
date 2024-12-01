package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.Exception.InvalidTaskDeletion;
import com.elhaouil.Todo_list_app.Model.Task;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.TaskRepo;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService {

    public final TaskRepo taskRepo;
    public final UserRepo userRepo;

    public ResponseEntity<String> addTask(String desc, String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Task task = new Task();
        task.setDescription(desc);
        task.setUser(user);
        user.getTasks().add(task);
        taskRepo.save(task);
        return ResponseEntity.ok("Deleted Successfully");
    }

    public ResponseEntity<String> updateTask(String desc) {
        Task task = taskRepo.findByDescription(desc)
                        .orElseThrow(() -> new UsernameNotFoundException("Task not Found"));
        task.setDescription(desc);
        taskRepo.save(task);
        return ResponseEntity.ok("Task Updated Successfully");
    }

    public ResponseEntity<String> addTasks(List<String> descriptions, String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        for (String desc : descriptions) {
            Task task = new Task();
            task.setDescription(desc);
            task.setUser(user);
            taskRepo.save(task);
            user.getTasks().add(task);
        }
        userRepo.save(user);
        return ResponseEntity.ok("Added Successfully");
    }

    public ResponseEntity<String> deleteTask(String desc) {
        Task task = taskRepo.findByDescription(desc)
                .orElseThrow(() -> new UsernameNotFoundException("Task Not Found"));
        taskRepo.deleteById(task.getId());
        return ResponseEntity.ok("Deleted Successfully");
    }


    public ResponseEntity<List<String>> getTasks(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        List<String> tasks = new ArrayList<>();
        for (Task task : user.getTasks()) {
            tasks.add(task.getDescription());
        }
        return ResponseEntity.ok(tasks);
    }
}
