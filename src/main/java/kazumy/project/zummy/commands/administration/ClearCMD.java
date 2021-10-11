package kazumy.project.zummy.commands.administration;

import java.util.List;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class ClearCMD extends BaseCommand {

	public ClearCMD() {
		super("clear", "limpar", "[%s]clear [Quantidade]", Permission.MESSAGE_MANAGE);
	}

	@Override
	public void execute(Member member, Message message) {
		val args = message.getContentRaw().split(" ");
		val channel = message.getTextChannel();
		
		if (args.length <= 1) {
			message.reply(String.format(this.getUsage(), EventListener.PREFIX)).queue();
			return;
		}
		
		try { Integer.parseInt(args[1]); } catch (NumberFormatException e) {
			message.reply(String.format(this.getUsage(), EventListener.PREFIX)).queue();
			return;
		}
		
		if (Integer.parseInt(args[1]) >= 100) {
			message.reply("O limite máximo para exclusão é de 100").queue();
			return;
		}
		
		final List<Message> messages = channel.getHistory().retrievePast(Integer.parseInt(args[1])).complete();
		channel.purgeMessages(messages);

		channel.sendMessage(String.format("Um total de %s mensagens foram deletadas", String.valueOf(messages.size()))).queue();;
		
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
}
