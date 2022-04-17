package org.zplugins.bot.zummy;

import lombok.Getter;
import org.zplugins.bot.zummy.configuration.Configuration;
import org.zplugins.bot.zummy.discord.Discord;

@Getter
public class MainZummy {

    public static final String ICON_URL = "https://cdn.discordapp.com/attachments/835219525825462272/856232582727204884/z_-_Copia.png";

    public static void main(String[] args) {
        new Discord(
                new Configuration().buildIfNotExists()
                        .addDefaults(config -> {
                            config.addDefault("bot.token", "your-token");
                        })
        ).registerCommands();
    }
}
