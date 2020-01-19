package bot;

import fileManagement.FileStringReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.File;

public class Bot {

    private static Bot instance;
    private JDA myJDA;


    private Bot(){


        try {
            myJDA = new JDABuilder()
                    .setActivity(Activity.watching("faule Freunde"))
                    .setStatus(OnlineStatus.ONLINE)
                    .setToken(FileStringReader.getInstance().getFileContentAsString("token"))
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public JDA getMyJDA() {
        return myJDA;
    }

    public static Bot getInstance(){
        if (instance == null) instance = new Bot();
        return instance;
    }
}
