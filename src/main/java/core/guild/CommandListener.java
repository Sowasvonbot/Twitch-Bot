package core.guild;

import botcore.Bot;
import botcore.Output;
import core.guild.modules.CommandController;
import core.guild.modules.commands.Executor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CommandListener extends ListenerAdapter {


    private long myGuildID;
    private Map<String, Executor> executorList;

    public CommandListener(long myGuildID, HashMap<String, Executor> executorList) {
        super();
        this.myGuildID = myGuildID;
        this.executorList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        this.executorList.putAll(executorList);
        Bot.addListener(this);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getGuild().getIdLong() != myGuildID) return;


        String messageContent = event.getMessage().getContentRaw();
        System.out.println("Incoming message: " + messageContent);
        String[] parameters = messageContent.split(" ");
        if (parameters.length == 0) return;
        if (parameters[0].contains("config")){
            Output.sendMessageToChannel(event.getChannel().getIdLong(), "Da hat jemand config eingegeben");
            //TODO Here give the content to the config controller
        } else{
            //TODO give content to command controller
            Executor executor = executorList.get(parameters[0].toLowerCase());
            if (executor == null) return;
            if (parameters.length < 2){
                Output.sendMessageToChannel(event.getChannel().getIdLong(),
                        "Welchen Commmand möchtest du ausführen?\n" +
                                executor.getCommands()
                        );
            }
            else {
                String[] args = null;
                if (parameters.length > 2){
                    args = new String[parameters.length -2];
                    for (int i = 0; i < parameters.length -2; i++) args[i] = parameters[i+2];
                }
                executor.executeCommand(parameters[1], args);
            }

        }

    }
}
