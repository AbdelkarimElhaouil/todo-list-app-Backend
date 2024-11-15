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

import java.util.List;


@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    UserRepo userRepo;

    public void addTask(String desc, String username){
        if(!userRepo.existsByUsername(username)){
            throw new UsernameNotFoundException("User Not Found");
        }
        User user = userRepo.findByUsername(username);
        Task task = new Task();
        task.setDescription(desc);
        task.setUser(user);
        taskRepo.save(task);
    }

    public void addTasks(List<String> descriptions, String username) {
        if(!userRepo.existsByUsername(username)){
            throw new UsernameNotFoundException("User Not Found");
        }
        User user = userRepo.findByUsername(username);
        for(String desc : descriptions){
            Task task = new Task();
            task.setDescription(desc);
            task.setUser(user);
            taskRepo.save(task);
        }

    }

    public void deleteTask(long id) {
        if(!taskRepo.existsById(id)){
            throw new InvalidTaskDeletion("Cannot Delete the task due to invalid credentials");
        }
        taskRepo.deleteById(id);
    }

    public void updateTask(TaskDTO task) {
        if(!taskRepo.existsById(task.getId())){
            throw new InvalidTaskDeletion("Cannot Update the task due to invalid credentials");
        }
        Task existedTask = taskRepo.findById(task.getId());
        existedTask.setDescription(task.getDescription());
        taskRepo.save(existedTask);
    }
}
