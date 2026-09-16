package com.tutorial.learning.exception;

public class StaleTaskVerionsException extends RuntimeException{
    public StaleTaskVerionsException(long id, long expectedVersion, long currentVersion){
        super("Task " + id
                + " was modified by another request; expected version "
                + expectedVersion
                + " but current version is "
                + currentVersion);
    }
}   
