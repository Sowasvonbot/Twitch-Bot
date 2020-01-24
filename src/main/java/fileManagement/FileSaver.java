package fileManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {


    private FileSaver instance;



    public boolean saveContentToFile(String filename, String content){
        BufferedWriter writer = getWriter(filename);
        if (writer == null) return false;
        try {
            writer.write(content);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



    }

    private BufferedWriter getWriter(String fileName){
        try{
            return new BufferedWriter(new FileWriter(
                    FileLoader.getInstance().getFileContainsName(fileName)
            ));

        } catch (IOException e){
            System.err.println("File: " + fileName + " couldn't be found. (Caller: getWriter, Filesaver");
            return null;
        }

    }


    public FileSaver getInstance() {
        if (instance == null) instance = new FileSaver();
        return instance;
    }
}
