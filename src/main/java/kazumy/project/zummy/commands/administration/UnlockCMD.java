package kazumy.project.zummy.commands.administration;

import kazumy.project.zummy.commands.BaseCommand;
import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class UnlockCMD extends BaseCommand {

	public UnlockCMD() {
		super("unlock", "", "[%s]unlock", "", Permission.MANAGE_CHANNEL);
	}

	@Override
	public void execute(Member member, Message message) {
		val role = message.getGuild().getRoleById("896553346738057226");
		
		message.getTextChannel().getManager().putPermissionOverride(role, 2048, 0).queue();;
		message.getTextChannel().sendMessage(String.format("Chat destravado por <@%s>", message.getAuthor().getId())).queue();
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}

	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode(""));
	}
}