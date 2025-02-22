package com.example.splitwise.CommandExecutor;

import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements Command {
    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("exit");
    }

    @Override
    public void execute(String input) {
        System.exit(0) ;
    }
}
