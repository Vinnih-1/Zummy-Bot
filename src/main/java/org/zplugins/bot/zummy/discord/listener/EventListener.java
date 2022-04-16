package org.zplugins.bot.zummy.discord.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.zplugins.bot.zummy.discord.commands.TicketCMD;
import org.zplugins.bot.zummy.discord.commands.base.BaseCommand;

import java.lang.reflect.InvocationTargetException;

public class EventListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        new Reflections("org.zplugins.bot.zummy.discord.commands").getSubTypesOf(BaseCommand.class)
                .stream().map(command -> {
                    try {
                        return command.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                             InvocationTargetException e) {
                        System.out.println("Ocorreu uma falha ao tentar carregar os comandos!");
                        return null;
                    }
                }).filter(command -> event.getName().equals(command.getName()))
                .filter(command -> event.getMember().hasPermission(command.getPermission())).findFirst()
                .ifPresentOrElse(command -> command.execute(event.getMember(), event),
                        () -> event.deferReply(true).setContent("Oops! Algo deu errado neste comando..."));
    }

    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        if (event.getComponent().getId().equals("menu-ticket"))
            new TicketCMD().menu(event.getMember(), event);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getButton().getId().equals("close-ticket"))
            event.getTextChannel().delete().queue(success -> System.out.println("Canal de ticket deletado: " + event.getTextChannel().getName()));

    }
}
