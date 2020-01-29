package core.guild;

import core.guild.modules.commands.Executor;
import fileManagement.FileLoader;
import fileManagement.FileSaver;
import fileManagement.FileStringReader;
import org.json.JSONException;
import org.json.JSONObject;
import twitch_api.Config;
import twitch_api.ModuleAPI;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ModuleController {

    private ModuleHolder moduleHolder;
    private String filepath;

    public ModuleController(String filepath, ModuleHolder moduleHolder) {
        this.filepath = filepath;

        this.moduleHolder = moduleHolder;
        //TODO Add modules here

        Module twitch = new ModuleAPI().getModule();
        moduleHolder.addModule(twitch);
        twitch.setOnline(getActiveStatus(twitch.getName()));


        System.out.println("Modules loaded");

    }

    public Boolean getActiveStatus(String name){
        String path = filepath + File.separator + name;
        try {
            FileLoader.getInstance().loadFileFromClasspath(path);
        } catch (IOException e){
            e.printStackTrace();
        }
        String content  = FileStringReader.getInstance().getFileContentAsString(path);
        JSONObject jsonContent;
        try{
            jsonContent = new JSONObject(content);
            System.out.println("Found isActive key in " + name);
            return jsonContent.getBoolean(name);
        }catch (JSONException e){
            System.out.println("Coulnd't found is active key in " + name);
            return false;
        }
    }

    public int shutdown() {
        int exitcode = 0;
        System.out.println("Set of modules shutdown");
        moduleHolder.getModuleList().forEach((module -> {
            if (module.getModuleData() == null) {
                FileSaver.getInstance().saveContentToFile(
                        filepath + File.separator + module.getName(),
                        "{\n"+module.getName()+":false\n}");
            } else {
                String config = module.getModuleData().saveConfig();
                FileSaver.getInstance().saveContentToFile(filepath + File.separator + module.getName(), config);
            }
        }));
        return exitcode;
    }


    public HashMap<String, Executor> getExecutorsForAllModules(){
        HashMap<String, Executor> executors = new HashMap<>();
        moduleHolder.getModuleList().forEach((module -> executors.put(module.getName(),new Executor(module.getController()))));
        return executors;
    }
}
