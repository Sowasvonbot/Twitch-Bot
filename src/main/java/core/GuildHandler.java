package core;

import botcore.Bot;
import core.guild.CommandListener;
import core.guild.ModuleController;
import core.guild.ModuleHolder;
import fileManagement.FileLoader;

import java.io.File;

public class GuildHandler {

    private GuildHolder holdedGuilds;

    public GuildHandler() {
        System.out.println("Starting Guild Handler");
        holdedGuilds = new GuildHolder(Bot.getActiveGuilds());
        holdedGuilds.getGuilds().forEach((guild -> initGuild(guild)));


    }


    private void initGuild(Guild guild){
        System.out.println("Guild " + guild.getName() + " is loading");
        String path = File.separator +"data"+ File.separator + guild.getName();
        FileLoader.getInstance().createDir(path);
        ModuleHolder moduleHolder = new ModuleHolder();
        guild.setModuleController(new ModuleController(path,moduleHolder));
        new CommandListener(guild.getGuildID(),moduleHolder);
        System.out.println("Guild " + guild.getName() + " loaded");
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
