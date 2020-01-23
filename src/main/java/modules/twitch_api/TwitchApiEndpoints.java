package modules.twitch_api;


import bot.Bot;
import fileManagement.FileLoader;
import fileManagement.FileStringReader;
import okhttp3.*;
import org.json.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class TwitchApiEndpoints {

    private static int active;

    public static void init(){
        try {
            active = 1;
            FileLoader.getInstance().loadFileFromClasspath("data/twitchClientId.txt");
        } catch (IOException e) {
            active = -1;
            System.err.println("Can't load twitch token");
        }
    }

    public static int getClientID(String userName){
        Request request = templateBuilder()
                .url("https://api.twitch.tv/kraken/users?login=" + userName)
                .build();
        JSONObject answer = sendRequest(request);
        return answer.getJSONArray("users").getJSONObject(0).getInt("_id");


    }

    public static StreamData getLiveStreamByUser(int userID){
        Request request = templateBuilder()
                .url("https://api.twitch.tv/kraken/streams/"+ userID)
                .build();
        JSONObject answer = sendRequest(request);
        System.out.println(answer);
        return getStreamDataFromMessage(answer);
    }

    private static StreamData getStreamDataFromMessage(JSONObject message){
        if (message.get("stream").equals(null)) return new StreamData(false);
        StreamData streamData = new StreamData(true);
        try {
            JSONObject stream = message.getJSONObject("stream");
            JSONObject channel = stream.getJSONObject("channel");

            streamData = new StreamData(
                    true,
                    new URL(stream.getJSONObject("preview").getString("template")),
                    stream.getString("game"),
                    new URL(channel.getString("logo")),
                    channel.getString("name"),
                    channel.getString("status")
                    );
        } catch (JSONException json){json.printStackTrace();}
        catch (MalformedURLException malformed){
            malformed.printStackTrace();
        }

        return streamData;
    }


    private static Request.Builder templateBuilder(){
        if (active == 0) init();
        if (active == -1) {
            System.err.println("can't send request, twitch module is offline");
            return null;
        }
        return new Request.Builder()
                .addHeader("Client-ID", getTwitchClientId())
                .addHeader("Accept", "application/vnd.twitchtv.v5+json");
    }

    private static String getTwitchClientId(){
        return FileStringReader.getInstance().getFileContentAsString("twitchClientId");
    }

    private static JSONObject sendRequest(Request request){
        try {
            Response response = Bot.getInstance().getMyJDA().getHttpClient().newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
