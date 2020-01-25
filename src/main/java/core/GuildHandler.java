package core;

import botcore.Bot;
import core.guild.ModuleController;
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
        String path = File.separator +"data"+ File.separator + guild.getName();
        FileLoader.getInstance().createDir(path);
        guild.setModuleController(new ModuleController(path));
    }
}
