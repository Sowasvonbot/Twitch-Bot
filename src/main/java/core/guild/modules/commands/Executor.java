package core.guild.modules.commands;

import botcore.EmbedWithPicture;
import botcore.MessageHolder;
import botcore.Output;
import core.guild.modules.CommandController;
import core.guild.modules.CommandReturn;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {

    private ExecutorService executorService;
    private CommandHolder commandHolder;
    private CommandController commandController;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Executor(CommandController commandController){
        executorService = Executors.newSingleThreadExecutor();
        this.commandController = commandController;
        commandHolder = new CommandHolder();

    }

    public void executeCommand(String parameter, String[] args, long channelID) {
        executorService.submit(() ->{
            logger.info("Executing command {} with parameters", parameter);
            CommandReturn commandReturn = commandController.executeCommand(parameter, args);
            Object o = commandReturn.getContent();
            MessageHolder messageHolder = null;
            messageHolder = commandReturn.getMessageHolder();
            if (o instanceof Message) {
                Output.sendMessageToChannel(channelID, (Message) o, messageHolder);
            } else if (o instanceof MessageEmbed) {
                Output.sendMessageToChannel(channelID, (MessageEmbed) o, messageHolder );
            } else if (o instanceof EmbedWithPicture){
                Output.sendMessageToChannel(channelID, (EmbedWithPicture) o, messageHolder);
            } else{
                Output.sendMessageToChannel(channelID,o.toString());
        }
        });
    }

    public String getCommands() {
        if (commandController.getCommands()!= null) return commandController.getCommands().toString();
        return "No commands :(";
    }

    public CommandController getCommandController() {
        return commandController;
    }
}
