import botcore.Bot;
import core.GuildHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) throws IOException {
        //StreamData streamData =  TwitchApiEndpoints.getLiveStreamByUser(TwitchApiEndpoints.getClientID("lec"));

        GuildHandler guildHandler =  GuildHandler.getInstance();

        JSONObject object;

        setupScanner(guildHandler);
    }



    private static void setupScanner(GuildHandler guildHandler){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() ->{
            String line;
            Scanner in = new Scanner(System.in);
            while ((line = in.next())!= null){
                switch (line){
                    case "quit":
                    case "stop":
                    case "exit":
                        Bot.shutdown();
                        int exitCode = guildHandler.shutdown();
                        System.exit(exitCode);
                    default:
                        System.out.println("Don't know: " + line);
                }
            }
        });
    }

}
