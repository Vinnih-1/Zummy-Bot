package kazumy.project.zummy.commands.moderation;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class TaskCMD extends BaseCommand {

	public TaskCMD() {
		super("task", "", "[%s]task [Tarefa concluídas]", "", Permission.ADMINISTRATOR);
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}

	@Override
	public void execute(Member member, Message message) {
		val args = message.getContentRaw().split(" ");
		
		if (args.length <= 2) {
			message.reply(String.format(this.getUsage(), EventListener.PREFIX)).queue();
			return;
		}
		
		
	}

	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode(""));
	}
}
