package core.guild;

import fileManagement.FileLoader;
import fileManagement.FileStringReader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
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
            return jsonContent.getBoolean(name);
        }catch (JSONException e){
            jsonContent = new JSONObject().append(name, false);
            return false;
        }
    }
}
