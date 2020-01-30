package twitch_api;

import botcore.*;
import core.guild.modules.CommandReturn;
import core.guild.modules.commands.Command;
import twitch_api.livestream.StreamData;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandController implements core.guild.modules.CommandController {

    private Config config;
    private List<MessageHolder> messageHolders;

    public CommandController(Config config) {
        this.config = config;
        messageHolders = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Twitch";
    }

    @Override
    public List<Command> getCommands() {
        Command command = new Command("getLiveStream", null, Command.returnValues.STRING);
        Command command2 = new Command("ping", null, Command.returnValues.STRING);
        List<Command> list = new ArrayList<>();
        list.add(command);
        list.add(command2);
        return list;
    }

    @Override
    public CommandReturn executeCommand(@Nonnull String command, String[] args) {
        CommandReturn commandReturn = null;
        switch(command.toLowerCase()){
            case "ping":
                commandReturn = new CommandReturn(new MyMessageBuilder().append("pong").build());
                break;
            case "getlivestream":

                if(args != null && args.length >= 1) {
                        int clientId = TwitchApiEndpoints.getClientID(args[0]);
                        StreamData data = TwitchApiEndpoints.getLiveStreamByUser(clientId);
                        if (data.isOnline()) commandReturn = new CommandReturn(
                                new EmbedWithPicture(
                                new MyEmbedBuilder().setDescription(data.toString() + " " +data.getStreamLink().toString()).setTitle(data.getStreamerName(), data.getStreamLink().toString()),
                                data.getPictureURL(),
                                data.getLogo()));
                        else commandReturn = new CommandReturn(data.toString());

                } else{
                    commandReturn = new CommandReturn("please use getLiveStream userHere");
                }
                break;
            case "getids":
                String res = "AllMessages: \n";
                for (MessageHolder messageHolder: messageHolders) {
                    res = res + messageHolder.toString() + "\n";
                }
                commandReturn = new CommandReturn(res);
                break;
            default:
                commandReturn =  new CommandReturn("no command given");

        }
        MessageHolder messageHolder = new MessageHolder();
        messageHolders.add(messageHolder);
        return commandReturn.setMessageHolder(messageHolder);
    }

    @Override
    public String getConfigDescription() {
        return null;
    }

    @Override
    public HashMap<String, String> getConfigVariables() {
        return null;
    }

    @Override
    public String setConfigVariable(String variable, String value) {
        return null;
    }
}
