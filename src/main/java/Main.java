import bot.Bot;
import fileManagement.FileLoader;
import fileManagement.FileStringReader;
import org.json.JSONObject;
import twitch_api.TwitchApiEndpoints;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main {


    public static void main(String[] args) throws IOException {
        Bot.getInstance();
        TwitchApiEndpoints.getLiveStreamByUser(TwitchApiEndpoints.getClientID("pueppi0"));

    }

}
