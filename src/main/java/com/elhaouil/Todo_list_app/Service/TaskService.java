package com.elhaouil.Todo_list_app.Service;

import com.elhaouil.Todo_list_app.DTO.TaskDTO;
import com.elhaouil.Todo_list_app.Exception.InvalidTaskDeletion;
import com.elhaouil.Todo_list_app.Model.Task;
import com.elhaouil.Todo_list_app.Model.User;
import com.elhaouil.Todo_list_app.Repo.TaskRepo;
import com.elhaouil.Todo_list_app.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    User user;

    public void addTask(String desc, String username) {
        user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        Task task = new Task();
        task.setDescription(desc);
        task.setUser(user);
        user.getTasks().add(task);
        taskRepo.save(task);
    }

    public void addTasks(List<String> descriptions, String username) {
        if (!userRepo.existsByUsername(username)) {
            throw new UsernameNotFoundException("User Not Found");
        }
        User user = userRepo.findByUsername(username);
        for (String desc : descriptions) {
            Task task = new Task();
            task.setDescription(desc);
            task.setUser(user);
            user.getTasks().add(task);
        }
        userRepo.save(user);

    }

    public void deleteTask(String desc, String username) {
        user = userRepo.findByUsername(username);
        Task task = taskRepo.findByDescriptionAndUser(desc, user);
        if (user == null || task == null) {
            throw new InvalidTaskDeletion("Cannot Delete the task due to invalid credentials");
        }
        taskRepo.deleteById(task.getId());
    }

    public void updateTask(TaskDTO task) {
        Task localTask = new Task();
        if (!taskRepo.existsById(localTask.getId())) {
            throw new InvalidTaskDeletion("Cannot Update the task due to invalid credentials");
        }
        localTask = taskRepo.findById(localTask.getId());
        localTask.setDescription(localTask.getDescription());
        taskRepo.save(localTask);
    }

    public List<String> getTasks(String username) {
        user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User 404");
        }
        List<String> tasks = new ArrayList<>();
        for (Task task : user.getTasks()) {
            tasks.add(task.getDescription());
        }
        return tasks;
    }
}
