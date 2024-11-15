package com.elhaouil.Todo_list_app.Exception;

public class InvalidTaskDeletion extends RuntimeException{
    public InvalidTaskDeletion(String msg){
        super(msg);
    }
}
