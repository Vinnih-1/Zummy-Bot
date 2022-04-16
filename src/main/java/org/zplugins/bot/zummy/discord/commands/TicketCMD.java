package org.zplugins.bot.zummy.discord.commands;

import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.zplugins.bot.zummy.discord.commands.base.BaseCommand;

import java.awt.*;
import java.util.Collections;

public class TicketCMD extends BaseCommand {

    public TicketCMD() {
        super("ticket", "Crie um menu de seleção para tickets", Permission.ADMINISTRATOR);
    }

    @Override
    public void menu(Member member, SelectMenuInteractionEvent event) {
        if (member.isTimedOut()) {
            event.deferReply(true).setContent("Você está de castigo, e não pode abrir um ticket!").queue();
            return;
        }
        val category = event.getGuild().getCategoryById("911343293676281928");
        val ticketChannels = category.getTextChannels();

        ticketChannels.stream()
                .filter(channel -> channel.getName().contains(member.getUser().getId()))
                .findFirst().ifPresentOrElse(channel -> {
                    event.deferReply(true).setContent("Aparentemente você já tem um ticket aberto!" +
                            " Vamos excluir o antigo, " +
                            "tente novamente em algums segundos...").queue();
                    channel.delete().queue(success -> System.out.println("Canal de ticket deletado: " + channel.getName()));
                }, () -> {
                    category.createTextChannel(event.getSelectedOptions().get(0).getValue().replace("ticket", member.getUser().getId())).queue(channel -> {
                        channel.createPermissionOverride(event.getMember()).setAllow(
                                Permission.VIEW_CHANNEL,
                                Permission.MESSAGE_SEND,
                                Permission.MESSAGE_ATTACH_FILES
                        ).queue();
                        val supportEmbed = new EmbedBuilder()
                                .setColor(Color.GREEN)
                                .setTitle(":incoming_envelope: Atendimento da zPluginS")
                                .setDescription(String.format("Seja bem vindo <@%s>, aqui a equipe irá lhe atender.", event.getUser().getId()))
                                .build();
                        channel.sendMessageEmbeds(Collections.singleton(supportEmbed))
                                .setActionRow(Button.danger("close-ticket", "Fechar ticket")
                                        .withEmoji(Emoji.fromUnicode("U+1F4E4"))).queue();
                        event.deferReply(true).setContent("Você abriu um ticket, aguarde o atendimento").queue();
                    });
                });
    }

    @Override
    public void execute(Member member, SlashCommandInteractionEvent event) {
        if (event.getGuild().getTextChannelById(event.getOption("chat-id").getAsString()) == null) {
            event.deferReply(true).setContent("O ID do chat provido está inválido").queue();
            return;
        }
        val ticketEmbed = new EmbedBuilder()
                .setColor(Color.BLUE)
                .setTitle(":mailbox: Central de Atendimento")
                .setDescription("Olá, seja bem-vindo ao atendimento da zPluginS." +
                        " Para iniciar seu atendimento clique no botão abaixo." +
                        " Seu atendimento será realizado por meio de um canal privado.")
                .addField("Links", ":earth_americas: [Hospedagem](https://discord.gg/vNpQNmK8Qw)" +
                        "\n:bookmark: [Loja](https://mercado-minecraft.com.br/)", true)
                .addField("Horário de Atendimento", ":timer: `08:00 as 19:00 (UTC-3)`", true)
                .setImage("https://cdn.discordapp.com/attachments/835219525825462272/911376737965834311/z_-_Copia.png")
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getAvatarUrl())
                .build();
        val menu = SelectMenu.create("menu-ticket");
        menu.addOption("Fazer uma Pergunta",
                "duvida-ticket", "Caso esteja com dúvida, não hesite", Emoji.fromUnicode("U+2753"));
        menu.addOption("Comprar algum Plugin",
                "comprar-ticket", "Se gostou de algum plugin, considere comprá-lo", Emoji.fromUnicode("U+1F4B0"));
        menu.addOption("Outros Serviços",
                "outros-ticket", "Também estamos disponíveis para outros serviços", Emoji.fromUnicode("U+1F6E0"));

        event.getGuild().getTextChannelById(event.getOption("chat-id").getAsString()).sendMessageEmbeds(Collections.singleton(ticketEmbed))
                .setActionRow(menu.build()).queue();
        event.reply(String.format("<@%s>, mensagem de ticket publicada", event.getMember().getUser().getId())).queue();
    }

    @Override
    public CommandData commandData() {
        return new CommandDataImpl(this.getName(), this.getDescription())
                .addOptions(new OptionData(OptionType.STRING, "chat-id", "ID do chat que será enviada a mensagem", true));
    }
}
