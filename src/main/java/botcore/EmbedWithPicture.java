package botcore;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.net.URL;

public class EmbedWithPicture {

    private EmbedBuilder embedBuilder;
    private URL picture;


    public EmbedWithPicture(EmbedBuilder embedBuilder, URL picture) {
        this.embedBuilder = embedBuilder;
        this.picture = picture;
    }

    public EmbedBuilder getEmbedBuilder() {
        return embedBuilder;
    }

    public URL getPicture() {
        return picture;
    }
}
