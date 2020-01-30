package botcore;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public abstract class Output {


    private final static Logger logger = LoggerFactory.getLogger("Output");




    public static boolean sendMessageToChannel(long channelID, String message){
        Message messageObject = new  MessageBuilder().append(message).build();
        return sendMessageToChannel(channelID,messageObject);
    }


    public static boolean sendMessageToChannel(long channelID, Message message){return sendMessageToChannel(channelID, message, null);}
    public static boolean sendMessageToChannel(long channelID, MessageEmbed message){return sendMessageToChannel(channelID, message, null);}
    public static boolean sendMessageToChannel(long channelID, EmbedWithPicture message){return sendMessageToChannel(channelID, message, null);}

    public static boolean sendMessageToChannel(long channelID, Message message, MessageHolder messageHolder){
        try {
            //TextChannel channel = Bot.getInstance().getMyJDA().getTextChannelById(channelID);
            //if (messageHolder != null)
            Bot.getInstance().getMyJDA().getTextChannelById(channelID).sendMessage(message).queue(messageHolder::setMessage);
            return true;
        } catch (NullPointerException nullPointer){
            logger.info(nullPointer.getMessage());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendMessageToChannel(long channelID, MessageEmbed message, MessageHolder messageHolder){
        try {
            Bot.getInstance().getMyJDA().getTextChannelById(channelID).sendMessage(message).queue(messageHolder::setMessage);
            return true;
        } catch (NullPointerException nullPointer){
            logger.info(nullPointer.getMessage());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static boolean sendMessageToChannel(long channelID, EmbedWithPicture message, MessageHolder messageHolder) {
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
                channel.sendMessage(embedBuilder.build()).queue(messageHolder::setMessage);
                return true;
            }
            currAction.embed(embedBuilder.build()).queue(messageHolder::setMessage);
            return true;
        } catch (NullPointerException nullPointer){
            logger.info(nullPointer.getMessage());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
