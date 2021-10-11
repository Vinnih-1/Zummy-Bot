package kazumy.project.zummy.commands.basics;

import kazumy.project.zummy.commands.BaseCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class ServerInfoCMD extends BaseCommand {

	public ServerInfoCMD() {
		super("serverinfo", "", "[%s]serverinfo", null);
	}

	@Override
	public void execute(Member member, Message message) {
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
}
