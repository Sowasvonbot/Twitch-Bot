package core;

import botcore.Bot;
import core.guild.CommandListener;
import core.guild.ModuleController;
import core.guild.ModuleHolder;
import fileManagement.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GuildHandler {

    private final Logger guildLogger = LoggerFactory.getLogger(GuildHandler.class);
    private GuildHolder holdedGuilds;

    public GuildHandler() {
        guildLogger.info("Starting Guild Handler");
        holdedGuilds = new GuildHolder(Bot.getActiveGuilds());
        holdedGuilds.getGuilds().forEach((guild -> initGuild(guild)));


    }


    private void initGuild(Guild guild){
        guildLogger.info("Guild {} is loading",guild.getName());
        String path = File.separator +"data"+ File.separator + guild.getName();
        FileLoader.getInstance().createDir(path);

        ModuleController moduleHolder = new ModuleController(path, new ModuleHolder());
        guild.setModuleController(moduleHolder);


        new CommandListener(guild.getGuildID(),moduleHolder.getExecutorsForAllModules());


        guildLogger.info("Guild {} loaded",guild.getName());
    }


    public int shutdown(){
        int exitcode = 0;
        int i = 0;
        for (Guild guild:holdedGuilds.getGuilds()) {
            if ((i = guild.getModuleController().shutdown()) != 0){
                guildLogger.info("Shutting down Guild {}", guild.getName());
                exitcode = i;
                guildLogger.info("Guild {} couldn't stop properly. Exitcode: {}", guild.getName(),i);
            }
        }

        return exitcode;
    }
}
