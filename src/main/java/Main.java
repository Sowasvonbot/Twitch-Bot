import core.BigDiscordBot;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {

        //register Modules here
        BigDiscordBot.getInstance().registerModule(twitch_api.ModuleAPI.class);

        //start bot
        BigDiscordBot.getInstance().startBot();
    }





}
