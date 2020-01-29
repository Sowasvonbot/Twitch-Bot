package twitch_api;

import core.guild.modules.MiscModuleData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitch_api.livestream.StreamData;

import java.util.HashMap;
import java.util.List;

public class Config implements MiscModuleData {

    private String[] liveStreamer;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean loadConfig(String fileContent) {
        try{
            JSONObject configData = new JSONObject(fileContent);

            JSONArray streamer = configData.getJSONArray("Streamer");
            liveStreamer = new String[streamer.length()];
            for (int i = 0; i < streamer.length(); i++) liveStreamer[i] = streamer.getString(i);

            return true;
        } catch (JSONException e){
            logger.error("Couldn't load config data from {}", fileContent);
            logger.error("Loading init values");
            initDefaultValues();
            return false;
        }
    }

    @Override
    public String saveConfig() {
        JSONObject configData = new JSONObject();
        configData.put("Streamer", liveStreamer);



        return  configData.toString();
    }


    private void initDefaultValues(){
        liveStreamer = new String[0];
    }
}
