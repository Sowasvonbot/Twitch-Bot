package core.guild;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class GuildListener extends ListenerAdapter {


    private long myGuildID;

    public GuildListener(long myGuildID) {
        super();
        this.myGuildID = myGuildID;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getGuild().getIdLong() != myGuildID) return;

        String messageContent = event.getMessage().getContentRaw();

        String[] parameters = messageContent.split(" ");
        if (parameters.length == 0) return;
        if (parameters[0].contains("config")){
            //TODO Here give the content to the config controller
        } else{
            //TODO give content to command controller
        }

    }
}
