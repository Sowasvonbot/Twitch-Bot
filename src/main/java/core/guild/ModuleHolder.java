package core.guild;

import java.util.ArrayList;
import java.util.List;

public class ModuleHolder {


    private List<Module> moduleList;

    public ModuleHolder() {
        moduleList = new ArrayList<>();
    }

    public Module addModule(Module module){
        moduleList.add(module);
        return module;
    }
}
