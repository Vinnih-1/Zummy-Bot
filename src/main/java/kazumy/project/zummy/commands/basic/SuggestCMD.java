package kazumy.project.zummy.commands.basics;

import java.awt.Color;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@SuppressWarnings("deprecation")
public class SuggestCMD extends BaseCommand {

	public SuggestCMD() {
		super("sugerir", "", "[%s]sugerir [Sugestão]", null);
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
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
		
		message.getJDA().getTextChannelById("880937751380377731").sendMessage(embed.build())
		.queue(m -> {
			m.addReaction("U+2705").queue();
			m.addReaction("U+274E").queue();
		});
	}
}
