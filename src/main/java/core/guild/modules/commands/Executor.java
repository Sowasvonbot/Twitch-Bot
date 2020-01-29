package core.guild.modules.commands;

import core.guild.modules.CommandController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {

    private ExecutorService executorService;
    private CommandHolder commandHolder;
    private CommandController commandController;

    public Executor(CommandController commandController){
        executorService = Executors.newSingleThreadExecutor();
        this.commandController = commandController;

    }

    public void executeCommand(String parameter, String[] args) {
        executorService.submit(() ->{
           commandController.executeCommand(parameter, args);
        });
    }

    public String getCommands() {
        if (commandController.getCommands()!= null) return commandController.getCommands().toString();
        return "No commands :(";
    }
}
