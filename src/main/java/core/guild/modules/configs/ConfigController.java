package core.guild.modules.configs;

import botcore.Bot;
import core.guild.modules.commands.Executor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigController {

    private long UserID;
    private long prChannelID;
    private ExecutorService executorService;
    private User user;
    private Map<String,Executor> executors;
    private long guildID;
    private Timer timer;


    public ConfigController(long userID, long guildID, Map<String,Executor> executors) {
        UserID = userID;
        this.guildID = guildID;
        this.executors = executors;
        user = Bot.getUser(userID);
        if (this.hasAdminPermission(userID,guildID)) sendMessage("Du bist Admin auf dem Server "+ Bot.getGuild(guildID).getName());
        else sendMessage("Du bist kein Admin auf dem Server " + Bot.getGuild(guildID).getName());


        timer = new Timer(user,new ConfigListener(), this);


    }

    private Boolean hasAdminPermission(long clientID, long guildID){
        Member member = Bot.getGuild(guildID).getMemberById(clientID);
        return member.hasPermission(Permission.ADMINISTRATOR);
    }

    protected void sendMessage(String content){
        user.openPrivateChannel().queue((channel) -> channel.sendMessage(content).queue());

    }


}
