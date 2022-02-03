package kazumy.project.zummy.commands.basic;

import kazumy.project.zummy.commands.BaseCommand;
import lombok.val;
import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@SuppressWarnings("deprecation")
public class AvatarCMD extends BaseCommand {

	public AvatarCMD() {
		super("avatar", "Retorna sua própria foto, ou do usuário mencionado", "", "[%s]avatar [Membro]", null);
	}

	@Override
	public void execute(Member member, Message message) {
		val args = message.getContentRaw().split(" ");
		val embed = new EmbedBuilder();
		var target = message.getMentionedMembers().isEmpty() ? null : message.getMentionedMembers().get(0).getUser();
		
		if (target == null) target = member.getUser();
		
		embed.setTitle(target.getName());
		embed.setDescription("**Avatar**");
		embed.setFooter("zPluginS © Todos os direitos reservados.", "https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");
		
		if (args.length <= 1) {
			embed.setImage(member.getUser().getAvatarUrl().concat("?size=256"));
			message.reply(embed.build()).queue();
		} else if (!message.getMentionedMembers().isEmpty()) {
			embed.setImage(message.getMentionedMembers().get(0).getUser().getAvatarUrl().concat("?size=256"));
			message.reply(embed.build()).queue();
		}
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
	
	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode("U+1F468"));
	}
}