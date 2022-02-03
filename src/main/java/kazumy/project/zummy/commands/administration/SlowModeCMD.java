package kazumy.project.zummy.commands.administration;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class SlowModeCMD extends BaseCommand {

	public SlowModeCMD() {
		super("slowmode", "", "[%s]slowmode [Tempo]", "", Permission.MANAGE_CHANNEL);
	}

	@Override
	public void execute(Member member, Message message) {
		val args = message.getContentRaw().split(" ");
		
		if (args.length <= 1) {
			message.reply(String.format(this.getUsage(), EventListener.PREFIX)).queue();
			return;
		}
		
		try { Integer.parseInt(args[1]); } catch (NumberFormatException e) {
			message.reply(String.format(this.getUsage(), EventListener.PREFIX)).queue();;
			return;
		}
		message.getTextChannel().getManager().setSlowmode(Integer.parseInt(args[1])).queue();;
		message.getTextChannel().sendMessage(String.format("Modo lento habilitado para %s segundos", args[1])).queue();
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}

	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode(""));
	}
}
