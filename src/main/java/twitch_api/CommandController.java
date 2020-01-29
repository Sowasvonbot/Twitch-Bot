package twitch_api;

import botcore.EmbedWithPicture;
import botcore.MyEmbedBuilder;
import botcore.MyMessageBuilder;
import botcore.Output;
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

    public CommandController(Config config) {
        this.config = config;
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
    public Object executeCommand(@Nonnull String command, String[] args) {
        switch(command){
            case "ping":
                return new MyMessageBuilder().append("pong").build();
            case "getLiveStream":

                if(args != null && args.length >= 1) {
                        int clientId = TwitchApiEndpoints.getClientID(args[0]);
                        StreamData data = TwitchApiEndpoints.getLiveStreamByUser(clientId);
                        if (data.isOnline()) return new EmbedWithPicture(
                                new MyEmbedBuilder().setDescription(data.toString()),
                                data.getPictureURL());
                        else return data.toString();

                } else{
                    return "please use getLiveStream userHere";
                }
            default:
                return "no command given";

        }
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
