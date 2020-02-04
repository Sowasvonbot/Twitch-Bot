import core.BigDiscordBot;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        BigDiscordBot.getInstance().registerModule(twitch_api.ModuleAPI.class);
        BigDiscordBot.getInstance().startBot();
    }





}
