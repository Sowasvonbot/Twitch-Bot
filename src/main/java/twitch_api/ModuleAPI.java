package twitch_api;

import core.guild.Module;

public class ModuleAPI implements core.guild.modules.ModuleAPI {

    CommandController commandController;
    Config config;

    public ModuleAPI() {
        config = new Config();
        commandController = new CommandController(config);
    }

    @Override
    public Module getModule() {
        return new Module(commandController, config, "Twitch");


    }
}
