package org.zplugins.bot.zummy.discord.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.zplugins.bot.zummy.discord.commands.base.BaseCommand;

public class UpdateCMD extends BaseCommand {

    public UpdateCMD() {
        super("update", "Atualize algum plugin já existente", Permission.ADMINISTRATOR);
    }

    @Override
    public void menu(Member member, SelectMenuInteractionEvent event) {

    }

    @Override
    public void execute(Member member, SlashCommandInteractionEvent event) {

    }

    @Override
    public CommandData commandData() {
        return new CommandDataImpl(this.getName(), this.getDescription());
    }
}
