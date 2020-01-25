package botcore;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

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



}
