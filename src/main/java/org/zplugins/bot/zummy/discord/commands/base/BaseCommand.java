package org.zplugins.bot.zummy.discord.commands.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

@Data
@AllArgsConstructor
public abstract class BaseCommand {

    private final String name, description;
    private final Permission permission;

    public abstract void menu(Member member, SelectMenuInteractionEvent event);

    public abstract void execute(Member member, SlashCommandInteractionEvent event);

    public abstract CommandData commandData();
}
