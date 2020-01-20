package twitch_api;

import java.net.URL;

public class StreamData {


    private boolean online;
    private URL pictureURL;
    private String game;
    private URL logo;
    private String streamerName;
    private String streamName;


    public StreamData(boolean online, URL pictureURL, String game, URL logo, String streamerName, String streamName) {
        this.online = online;
        this.pictureURL = pictureURL;
        this.game = game;
        this.logo = logo;
        this.streamerName = streamerName;
        this.streamName = streamName;
    }

    public StreamData(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public URL getPictureURL() {
        return pictureURL;
    }

    public String getGame() {
        return game;
    }

    public URL getLogo() {
        return logo;
    }

    public String getStreamerName() {
        return streamerName;
    }

    public String getStreamName() {
        return streamName;
    }

    @Override
    public String toString() {
        if (isOnline()){
            return "Der Streamer " + getStreamerName() + " ist gerade online und spielt " + getGame();
        }
        return "Der Stream ist offline";
    }
}
