package org.zplugins.bot.zummy.discord.commands;

import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.zplugins.bot.zummy.discord.commands.base.BaseCommand;
import org.zplugins.bot.zummy.lavaplayer.PlayerManager;

public class MusicCMD extends BaseCommand {

    public MusicCMD() {
        super("music", "Habilite a funcionalidade musical especificando o chat", Permission.ADMINISTRATOR);
    }

    @Override
    public void menu(Member member, SelectMenuInteractionEvent event) {
    }

    @Override
    public void execute(Member member, SlashCommandInteractionEvent event) {
        val channel = event.getGuild().getVoiceChannelById(event.getOption("channel-id").getAsString());
        if (channel == null) {
            event.reply("O ID do canal especificado deve ser válido").queue();
            return;
        }
        event.getGuild().getAudioManager().openAudioConnection(channel);
        PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), "https://www.youtube.com/watch?v=0KvlwMd3C4Y");
        event.reply(String.format("<@%s>, Perfeito! Entrei no canal de rádio", event.getMember().getUser().getId())).queue();
    }

    @Override
    public CommandData commandData() {
        return new CommandDataImpl(this.getName(), this.getDescription())
                .addOptions(new OptionData(OptionType.STRING, "channel-id", "ID do canal que irá tocar a música", true));
    }
}
