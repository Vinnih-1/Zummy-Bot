package org.zplugins.bot.zummy.discord.commands;

import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.zplugins.bot.zummy.MainZummy;
import org.zplugins.bot.zummy.discord.commands.base.BaseCommand;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class PluginCMD extends BaseCommand {

    public PluginCMD() {
        super("plugin", "Envie um novo plugin para a loja", Permission.ADMINISTRATOR);
    }

    @Override
    public void menu(Member member, SelectMenuInteractionEvent event) {
    }

    @Override
    public void execute(Member member, SlashCommandInteractionEvent event) {
        try {
            new URL(event.getOption("link").getAsString());
        } catch (MalformedURLException e) {
            event.deferReply(true).setContent("O link provido deve ser um link válido!").queue();
            return;
        }
        val pluginEmbed = new EmbedBuilder()
                .setTitle("Novo Plugin")
                .setColor(Color.DARK_GRAY)
                .setAuthor("zPluginS", event.getOption("link").getAsString(), MainZummy.ICON_URL)
                .setFooter("Publicado por: " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl())
                .addField("Plugin", event.getOption("name").getAsString(), true)
                .addField("Valor", event.getOption("value").getAsString(), true)
                .addField("Descrição", event.getOption("description").getAsString(), false)
                .build();

        event.getTextChannel().sendMessageEmbeds(Arrays.asList(pluginEmbed))
                .setActionRow(Button.link(event.getOption("link").getAsString(), "Comprar")).queue();
        event.reply(String.format("<@%s>, seu plugin foi publicado no chat de produtos", event.getMember().getUser().getId())).queue();
    }

    @Override
    public CommandData commandData() {
        return new CommandDataImpl(this.getName(), this.getDescription())
                .addOptions(new OptionData(OptionType.STRING, "name", "Nome do Plugin", true),
                        new OptionData(OptionType.STRING, "value", "Preço do Plugin", true),
                        new OptionData(OptionType.STRING, "description", "Descrição do Plugin", true),
                        new OptionData(OptionType.STRING, "link", "Link do Plugin", true));
    }
}
