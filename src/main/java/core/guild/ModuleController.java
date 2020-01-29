package core.guild;

import fileManagement.FileLoader;
import fileManagement.FileSaver;
import fileManagement.FileStringReader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class ModuleController {

    private ModuleHolder moduleHolder;
    private String filepath;

    public ModuleController(String filepath) {
        this.filepath = filepath;



        moduleHolder = new ModuleHolder();
        //TODO Add modules here

        moduleHolder.addModule(new Module(null,null, "Twitch", getActiveStatus("Twitch")));
        moduleHolder.addModule(new Module(null,null, "Test", getActiveStatus("Test")));

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
}
