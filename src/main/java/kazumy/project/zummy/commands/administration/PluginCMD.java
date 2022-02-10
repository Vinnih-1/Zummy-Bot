package kazumy.project.zummy.commands.administration;

import java.awt.Color;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.components.MenuAction;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@SuppressWarnings("deprecation")
public class PluginCMD extends BaseCommand implements MenuAction {

	public PluginCMD() {
		super("plugin", "", "", "[%s]plugin <Nome do Plugin>; <Preço>; <Link>; <Descrição>", Permission.ADMINISTRATOR);
		this.setButtonID("plugin");
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
		val clickedMember = event.getMember();

		if (event.getButton().getId().contains(clickedMember.getUser().getId())) {
			val action = event.getButton().getId().split("_")[1];

			if (action.equals("plugin")) {
				val link = event.getMessage().getEmbeds().get(0).getFooter().getText();

				event.getGuild().getTextChannelById("832817270598139934").sendMessage(new EmbedBuilder(event.getMessage().getEmbeds().get(0))
								.setFooter("zPluginS © Todos os direitos reservados.").build())
						.setActionRow(Button.primary("ticket" + "-" + "open-ticket", "Perguntar").withEmoji(Emoji.fromUnicode("U+2753")),
								Button.link(link, "Comprar"))
						.queue();
				val embed = new EmbedBuilder();
				embed.setColor(Color.GREEN);
				embed.setDescription(String.format("<@%s>, plugin enviado com sucesso", clickedMember.getUser().getId()));
				event.getTextChannel().sendMessage(embed.build()).queue();
			} else if (action.equals("cancel")) {
				val embed = new EmbedBuilder();
				embed.setColor(Color.RED);
				embed.setDescription(String.format("<@%s>, ação cancelada sucesso", clickedMember.getUser().getId()));
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
			event.getMessage().delete().queue();
		} else event.deferReply(true).setContent("Você não pode interferir nesta ação.");
	}

	@Override
	public void execute(Member member, Message message) {
		val args = message.getContentRaw().split(" ");
		
		if (args.length <= 1) {
			message.reply(String.format("`" + this.getUsage() + "`", EventListener.PREFIX)).queue();
			return;
		}
		message.delete().queue();

		val text = String.join(" ", args).replaceFirst(EventListener.PREFIX + this.getName(), "").split(";");
		val description = String.join(" ", args).split(";")[3];

		val embed = new EmbedBuilder();
		embed.setTitle("Novo Plugin");
		embed.setDescription(String.format("**Plugin**\n%s\n**Preço**\n%s\n**Descrição**\n%s", text[0], text[1], description));
		embed.setColor(Color.DARK_GRAY);
		embed.setAuthor("zPluginS", "https://discord.gg/CUGf2wrbAW", "https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");
		embed.setFooter(text[2], "https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");
		embed.setThumbnail("https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");

		message.getTextChannel().sendMessage(embed.build())
				.setActionRow(Button.success(this.getButtonID() + "-" + member.getUser().getId() + "_plugin", "Enviar"),
						Button.danger(this.getButtonID() + "-" + member.getUser().getId() + "_cancel", "Cancelar")).queue();
	}

	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode(""));
	}

	@Override
	public void selectionMenu(SelectionMenuEvent event) {	
	}
}
