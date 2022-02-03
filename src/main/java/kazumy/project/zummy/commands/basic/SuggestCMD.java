package kazumy.project.zummy.commands.basic;

import java.awt.Color;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@SuppressWarnings("deprecation")
public class SuggestCMD extends BaseCommand {

	public SuggestCMD() {
		super("sugerir", "Lança uma sugestão que possa ser usada por nós", "", "[%s]sugerir [Sugestão]", null);
		this.setButtonID("suggest");
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
		val clickedMember = event.getMember();
		
		if (event.getButton().getId().contains(clickedMember.getUser().getId())) {
			val action = event.getButton().getId().split("_")[1];
			
			if (action.equals("suggest")) {
				
				 event.getGuild().getTextChannelById("880937751380377731").sendMessage(new EmbedBuilder(event.getMessage().getEmbeds().get(0)).build())
				 .queue(m -> {
						m.addReaction("U+2705").queue();
						m.addReaction("U+274E").queue();
					});
				val embed = new EmbedBuilder();
				embed.setColor(Color.GREEN);
				embed.setDescription(String.format("<@%s>, sugestão enviada com sucesso", clickedMember.getUser().getId()));
				event.getTextChannel().sendMessage(embed.build()).queue();
			} else if (action.equals("cancel")) {
				val embed = new EmbedBuilder();
				embed.setColor(Color.RED);
				embed.setDescription(String.format("<@%s>, sugestão cancelada com sucesso", clickedMember.getUser().getId()));
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
			event.getMessage().delete().queue();
			
		} else event.deferReply(true).setContent("Você não pode interferir nesta ação.").queue();
	}

	@Override
	public void execute(Member member, Message message) {
		val args = message.getContentRaw().split(" ");
		
		if (args.length <= 1) {
			message.reply(String.format(this.getUsage(), EventListener.PREFIX)).queue();
			return;
		}
		message.delete().queue();
		
		val embed = new EmbedBuilder();
		embed.setTitle(member.getUser().getName() + " • Sugestão");
		embed.setColor(Color.BLUE);
		embed.setDescription(String.format("Sugestão de <@%s> \n \n", member.getUser().getId()) + "**Sugestão** \n \n" +
		String.join(" ", args).replaceFirst(EventListener.PREFIX + this.getName(), ""));
		embed.setThumbnail(member.getUser().getAvatarUrl());
		embed.setFooter("zPluginS © Todos os direitos reservados.", "https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");
		
		message.getJDA().getTextChannelById("881849685453717514").sendMessage(embed.build())
		.setActionRow(Button.success(this.getButtonID() + "-" + member.getUser().getId() + "_suggest", "Sugerir"), 
				Button.danger(this.getButtonID() + "-" + member.getUser().getId() + "_cancel", "Cancelar")).queue();

	}
	
	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode("U+1F4D7"));
	}
}
