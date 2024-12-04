package com.example.splitwise.CommandExecutor;

public interface Command {
    boolean matches(String input);
    void execute(String input);
}
