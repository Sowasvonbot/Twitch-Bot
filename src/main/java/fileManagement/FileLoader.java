package fileManagement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FileLoader {

    private static FileLoader instance;
    private HashMap<String, File> myFiles;
    private final String filepath = System.getProperty("user.dir");


    private FileLoader(){
        myFiles = new HashMap<>();
        //Load all Files in Data
        File dataFolder = new File(filepath + File.separator + "data");
        if (!dataFolder.exists()) dataFolder.mkdir();

        for (File tempFile : dataFolder.listFiles()){
            try {
                if (tempFile.isFile()) loadFileFromClasspath("data" + File.separator + tempFile.getName());
            } catch (IOException e){
                System.out.println("File " + tempFile.getName() + " does not exist!");
            }
        }
    }

    public File loadFileFromClasspath (String name) throws IOException{
        File newFile ;
        URL fileUrl = getClass().getResource(name);
        try {
            newFile = new File(fileUrl.getFile());

        } catch (NullPointerException nullPointer){
            newFile = new File(filepath + File.separator + name);
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        myFiles.put(name,newFile);
        return newFile;

    }

    public File getFileContainsName(String name){
        for (Map.Entry<String, File> entry:myFiles.entrySet()) {
            if (entry.getKey().contains(name)){
                return entry.getValue();
            }
        }
        return null;
    }




    public static FileLoader getInstance(){
        if (instance == null) instance = new FileLoader();
        return instance;
    }

}
