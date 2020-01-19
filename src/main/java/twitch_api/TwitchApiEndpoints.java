package twitch_api;


import bot.Bot;
import fileManagement.FileStringReader;
import okhttp3.*;
import org.json.*;

import java.io.File;
import java.io.IOException;

public abstract class TwitchApiEndpoints {



    public static int getClientID(String userName){
        Request request = templateBuilder()
                .url("https://api.twitch.tv/kraken/users?login=" + userName)
                .build();
        JSONObject answer = sendRequest(request);
        return answer.getJSONArray("users").getJSONObject(0).getInt("_id");


    }

    public static void getLiveStreamByUser(int userID){
        Request request = templateBuilder()
                .url("https://api.twitch.tv/kraken/streams/"+ userID)
                .build();
        JSONObject answer = sendRequest(request);
        System.out.println(answer.toString());
    }


    private static Request.Builder templateBuilder(){
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
