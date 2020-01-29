package core.guild.modules.commands;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandController {

    private ExecutorService executorService;
    private CommandHolder commandHolder;

    public CommandController(CommandHolder commandHolder){
        executorService = Executors.newSingleThreadExecutor();
        this.commandHolder = commandHolder;

    }
}
