package botcore;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

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
            EmbedBuilder embedBuilder = message.getEmbedBuilder();
            InputStream file = message.getPicture().openStream();
            embedBuilder.setImage("attachment://picture.jpg");
            Bot.getInstance().getMyJDA().getTextChannelById(channelID)
                    .sendFile(file, "picture.jpg")
                    .embed(embedBuilder.build())
                    .queue();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
