package twitch_api;

import core.guild.modules.MiscModuleData;
import core.guild.modules.commands.Command;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

public class CommandController implements core.guild.modules.CommandController {

    private Config config;

    public CommandController(Config config) {
        this.config = config;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }

    @Override
    public <T> T executeCommand(@Nonnull String command, String[] args) {
        return null;
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
