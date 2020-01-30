package core.guild;

import botcore.Bot;
import core.guild.modules.commands.Executor;
import core.guild.modules.configs.ConfigController;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandListener extends ListenerAdapter {


    private long myGuildID;
    private Map<String, Executor> executorList;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService configService;

    public CommandListener(long myGuildID, HashMap<String, Executor> executorList) {
        super();
        this.myGuildID = myGuildID;
        this.executorList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        this.executorList.putAll(executorList);
        configService = Executors.newSingleThreadExecutor();
        Bot.addListener(this);

    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getGuild().getIdLong() != myGuildID) return;


        String messageContent = event.getMessage().getContentRaw();

        logger.debug("Incoming message {}", messageContent);


        String[] parameters = messageContent.split(" ");
        if (parameters.length == 0) return;

        if (event.getMessage().getMentionedUsers().contains(Bot.getBotUser())){
            if (event.getMessage().getContentRaw().toLowerCase().contains("config")){
                //TODO instance of new ConfigController(Executor, UserID, Guild
                configService.submit(() -> new ConfigController(event.getAuthor().getIdLong(),event.getGuild().getIdLong(), executorList));
            }
            return;
        }
        if (parameters[0].length() < 2 || parameters[0].charAt(0) != '!') return;
        String[] args = null;
        if (parameters.length >= 2) {
            args = new String[parameters.length -1];
            for (int i = 0; i < parameters.length-1; i++) {
                args[i] = parameters[i+1];
            }
        }
        for (Map.Entry<String, Executor> entry :executorList.entrySet()) {
            entry.getValue().executeCommand(
                    parameters[0].replace("!",""),
                    args,
                    event.getChannel().getIdLong());
        }
        //executorList.forEach((s, executor) -> executor.executeCommand(parameters[0].replace("!",""),args));

        /*if (parameters[0].contains("config")){
            Output.sendMessageToChannel(event.getChannel().getIdLong(), "Da hat jemand config eingegeben");

            //TODO Here give the content to the config controller
            GuildHandler.saveConfigs(event.getGuild().getIdLong());
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
                executor.executeCommand(parameters[1], args, event.getChannel().getIdLong());
            }

        }*/

    }
}
