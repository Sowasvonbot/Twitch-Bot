import botcore.Bot;
import core.GuildHandler;
import core.guild.modules.twitch_api.TwitchApiEndpoints;
import core.guild.modules.twitch_api.livestream.StreamData;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) throws IOException {
        StreamData streamData =  TwitchApiEndpoints.getLiveStreamByUser(TwitchApiEndpoints.getClientID("lec"));
        System.out.println(streamData);

        new GuildHandler();

        setupScanner();
    }



    private static void setupScanner(){
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
                        System.exit(0);
                    default:
                        System.out.println("Don't know: " + line);
                }
            }
        });
    }

}
