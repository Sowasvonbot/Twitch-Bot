package botcore;

import core.Guild;
import fileManagement.FileLoader;
import fileManagement.FileStringReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot {

    private static Bot instance;
    private JDA myJDA;


    private Bot(){
        try {
            FileLoader.getInstance().loadFileFromClasspath("data/token.txt");
            myJDA = new JDABuilder()
                    .setActivity(Activity.watching("faule Freunde"))
                    .setStatus(OnlineStatus.ONLINE)
                    .setToken(FileStringReader.getInstance().getFileContentAsString("token"))
                    .build().awaitReady();
        } catch (LoginException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException ex){
            ex.printStackTrace();
            System.exit(1);
        } catch (InterruptedException interrupted){
            interrupted.printStackTrace();
            System.exit(1);
        }
    }

    public JDA getMyJDA() {
        return myJDA;
    }

    protected static Bot getInstance(){
        if (instance == null) instance = new Bot();
        return instance;
    }

    public static OkHttpClient getHTTPClient(){
        return Bot.getInstance().getMyJDA().getHttpClient();
    }

    public static void shutdown(){Bot.getInstance().getMyJDA().shutdownNow();}

    public static List<Guild> getActiveGuilds(){
        List<Guild> guilds = new ArrayList<>();
        getInstance().getMyJDA().getGuilds().forEach((activeGuild) ->{
            guilds.add(new Guild(
                    activeGuild.getIdLong(),
                    activeGuild.getName(),
                    activeGuild.getOwnerIdLong()
            ));
        });
        return guilds;
    }






}
