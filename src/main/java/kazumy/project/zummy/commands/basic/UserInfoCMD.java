package kazumy.project.zummy.commands.basics;

import kazumy.project.zummy.commands.BaseCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class UserInfoCMD extends BaseCommand {

	public UserInfoCMD() {
		super("userinfo", "", "[%s]userinfo", null);
	}

	@Override
	public void execute(Member member, Message message) {
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
}