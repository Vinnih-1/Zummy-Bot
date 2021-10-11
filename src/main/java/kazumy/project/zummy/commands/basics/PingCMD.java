package kazumy.project.zummy.commands.basics;

import kazumy.project.zummy.commands.BaseCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class PingCMD extends BaseCommand {

	public PingCMD() {
		super("ping", "", "[%s]ping", null);
	}

	@Override
	public void execute(Member member, Message message) {
		message.reply("Meu ping atual é de " + message.getJDA().getGatewayPing() + "ms").queue();
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
}