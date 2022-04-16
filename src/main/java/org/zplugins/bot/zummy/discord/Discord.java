package org.zplugins.bot.zummy.discord;

import lombok.Getter;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.reflections.Reflections;
import org.zplugins.bot.zummy.configuration.Configuration;
import org.zplugins.bot.zummy.discord.commands.base.BaseCommand;
import org.zplugins.bot.zummy.discord.listener.EventListener;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;

public class Discord {

    private Configuration configuration;
    @Getter private JDA originalJda;

    public Discord (Configuration configuration) {
        this.configuration = configuration;
        try {
            originalJda = JDABuilder.createDefault(this.configuration.getString("bot.token"))
                    .addEventListeners(
                            new EventListener()
                    ).build().awaitReady();
        } catch (LoginException | InterruptedException e) {
            System.out.println("Há algo de errado com o token provido!");
        }
    }

    public Discord registerCommands() {
        if (this.originalJda == null) return null;
        new Reflections("org.zplugins.bot.zummy.discord.commands").getSubTypesOf(BaseCommand.class)
                .stream().map(command -> {
                    try {
                        return command.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        System.out.println("Ocorreu uma falha ao tentar carregar os comandos!");
                        return null;
                    }
                }).forEach(command -> {
                    val guild = this.originalJda.getGuildById("832601856403701771");
                    guild.upsertCommand(command.commandData()).queue();

                    System.out.println("Comando " + command.getName() + " registrado com êxito!");
                });
        return this;
    }
}
