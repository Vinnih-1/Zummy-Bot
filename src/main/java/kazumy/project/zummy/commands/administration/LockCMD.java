package kazumy.project.zummy.commands.administration;

import kazumy.project.zummy.commands.BaseCommand;
import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class LockCMD extends BaseCommand {

	public LockCMD() {
		super("lock", "", "[%s]lock", "", Permission.MANAGE_CHANNEL);
	}

	@Override
	public void execute(Member member, Message message) {
		val role = message.getGuild().getRoleById("896553346738057226");
		val permOverrides = message.getTextChannel().getRolePermissionOverrides();
		val overrided = permOverrides.stream().anyMatch(c -> c.getRole().equals(role) && c.getDenied().contains(Permission.MESSAGE_WRITE));
		message.getTextChannel().getManager().putPermissionOverride(role, (!overrided ? 0 : 2048), (overrided ? 0 : 2048)).queue();
		message.getTextChannel().sendMessage(String.format("Chat " + (overrided ? "destravado" : "travado") + " por <@%s>", message.getAuthor().getId())).queue();
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}

	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode(""));
	}
}
