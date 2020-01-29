package botcore;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.InputStream;

public class Output {






    public static boolean sendMessageToChannel(long channelID, String message){
        Message messageObject = new  MessageBuilder().append(message).build();
        return sendMessageToChannel(channelID,messageObject);
    }

    public static boolean sendMessageToChannel(long channelID, Message message){
        try {
            Bot.getInstance().getMyJDA().getTextChannelById(channelID).sendMessage(message).queue();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendMessageToChannel(long channelID, MessageEmbed message){
        try {
            Bot.getInstance().getMyJDA().getTextChannelById(channelID).sendMessage(message).queue();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static boolean sendMessageToChannel(long channelID, EmbedWithPicture message) {
        try {
            MessageAction currAction = null;
            InputStream  file = null;
            InputStream thumbnail = null;

            TextChannel channel = Bot.getInstance().getMyJDA().getTextChannelById(channelID);
            EmbedBuilder embedBuilder = message.getEmbedBuilder();
            if(message.getPicture() != null) {
                file = message.getPicture().openStream();
                embedBuilder.setImage("attachment://picture.jpg");
            }

            if (message.getThumbnail() != null){
                thumbnail = message.getThumbnail().openStream();
                embedBuilder.setThumbnail("attachment://thumbnail.jpg");
            }
            if (file != null) currAction =  channel.sendFile(file, "picture.jpg");
            if (currAction != null && thumbnail != null) currAction.addFile(thumbnail, "thumbnail.jpg");
            else if (currAction == null && thumbnail != null) currAction = channel.sendFile(thumbnail, "thumbnail.jpg");
            else if (currAction == null && thumbnail == null) {
                channel.sendMessage(embedBuilder.build());
                return true;
            }
            currAction.embed(embedBuilder.build()).queue();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
