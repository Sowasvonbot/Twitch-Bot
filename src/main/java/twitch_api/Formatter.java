package twitch_api;

import botcore.EmbedWithPicture;
import botcore.MyEmbedBuilder;
import twitch_api.livestream.StreamData;

public class Formatter {



    public static EmbedWithPicture buildEmbedWithPictureFromStreamData(StreamData data){
         return new EmbedWithPicture(
                new MyEmbedBuilder()
                        //setDescription(data.toString() + " " + data.getStreamLink().toString())
                        .addField("Game", data.getGame(), true)
                        .addField("Title", data.getStreamName(), true)
                        .addField("Viewer", Integer.toString(data.getViewer()), true)
                        .setTitle(data.getStreamerName(), data.getStreamLink().toString()),
                 data.getPictureURL(),
                 data.getLogo());
    }

    public static EmbedWithPicture updateEmbedWithPicture(StreamData data, EmbedWithPicture embed){
        try {
            embed.getEmbedBuilder().clearFields()
                    .addField("Game", data.getGame(), true)
                    .addField("Title", data.getStreamName(), true)
                    .addField("Viewer", Integer.toString(data.getViewer()), true);
        } catch (Exception e){
            e.printStackTrace();
        }
        return embed;

    }
}
