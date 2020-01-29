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
        //System.out.println("Starting Guild Handler");
        guildLogger.info("Starting Guild Handler");
        holdedGuilds = new GuildHolder(Bot.getActiveGuilds());
        holdedGuilds.getGuilds().forEach((guild -> initGuild(guild)));


    }


    private void initGuild(Guild guild){
        //System.out.println("Guild " + guild.getName() + " is loading");
        guildLogger.info("Guild {} is loading",guild.getName());
        String path = File.separator +"data"+ File.separator + guild.getName();
        FileLoader.getInstance().createDir(path);

        ModuleController moduleHolder = new ModuleController(path, new ModuleHolder());
        guild.setModuleController(moduleHolder);


        new CommandListener(guild.getGuildID(),moduleHolder.getExecutorsForAllModules());


        guildLogger.info("Guild {} loaded",guild.getName());
        //System.out.println("Guild " + guild.getName() + " loaded");
    }


    public int shutdown(){
        int exitcode = 0;
        int i = 0;
        for (Guild guild:holdedGuilds.getGuilds()) {
            if ((i = guild.getModuleController().shutdown()) != 0){
                System.out.println("Shutting down Guild: " + guild.getName());
                exitcode = i;
                System.out.println("Guild " +  guild.getName() + " couldn't stop properly. Exitcode: " + i);
            }
        }

        return exitcode;
    }
}
