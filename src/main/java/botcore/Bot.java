package botcore;

import core.Guild;
import fileManagement.FileLoader;
import fileManagement.FileStringReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
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

    public static void addListener(ListenerAdapter myListenerAdapter ){
        getInstance().getMyJDA().addEventListener(myListenerAdapter);
    }

    public static void removeListener(ListenerAdapter listenerAdapter){
        getInstance().getMyJDA().removeEventListener(listenerAdapter);
    }

    public static User getBotUser(){
        return getInstance().myJDA.getSelfUser();
    }

    public static User getUser(long id){
        return getInstance().getMyJDA().getUserById(id);
    }

    public static net.dv8tion.jda.api.entities.Guild getGuild(long id){
        return getInstance().getMyJDA().getGuildById(id);
    }

    public static long getRoleIDforGuild(long guildID, String roleName){
        Role role = getInstance().getMyJDA().getGuildById(guildID).getRolesByName(roleName,true).get(0);
        if (role == null) return 0;
        return role.getIdLong();
    }

    public static List<String> getAllMembersWithRole(long roleID){
        List<String> result = new ArrayList<>();
        Role role =  getInstance().getMyJDA().getRoleById(roleID);
        net.dv8tion.jda.api.entities.Guild guild = role.getGuild();
        guild.getMembersWithRoles(role).forEach(member -> result.add(member.getUser().getName()));
        return result;
    }

    public static long getChannelIDByName(String name, long guildID){
        try {
            return getInstance().getMyJDA()
                    .getGuildById(guildID)
                    .getTextChannelsByName(name, true)
                    .get(0).getIdLong();
        } catch (Exception e){
            return 0;
        }
    }






}
