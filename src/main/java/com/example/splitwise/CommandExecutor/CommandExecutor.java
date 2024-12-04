package com.example.splitwise.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {
    private final List<Command> commands = new ArrayList<Command>();

    @Autowired
    public CommandExecutor(UserSettleUpCommand userSettleUpCommand, ExitCommand exitCommand) {
        this.AddCommand(userSettleUpCommand);
        this.AddCommand(exitCommand);
    }

    public void AddCommand(Command command) {
        commands.add(command);
    }

    public void RemoveCommand(Command command) {
        commands.remove(command);
    }

    public void execute(String input){
        for (Command command : commands) {
            if(command.matches(input)){
                command.execute(input);
                break;
            }
        }
    }
}
