package core.guild;

import core.guild.modules.CommandController;
import core.guild.modules.MiscModuleData;

public class Module {


    private CommandController controller;
    private MiscModuleData moduleData;
    private String name;
    private boolean online;


    public Module(CommandController controller, MiscModuleData moduleData, String name, Boolean online) {
        this.controller = controller;
        this.moduleData = moduleData;
        this.name = name;
        this.online = online;
    }

    public CommandController getController() {
        return controller;
    }

    public MiscModuleData getModuleData() {
        return moduleData;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return online;
    }
}
