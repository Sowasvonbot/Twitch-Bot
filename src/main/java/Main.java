import botcore.Bot;
import core.BigDiscordBot;
import core.GuildHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) throws IOException {


        BigDiscordBot.getInstance().registerModule(twitch_api.ModuleAPI.class);
        BigDiscordBot.getInstance().startBot();


    }





}
