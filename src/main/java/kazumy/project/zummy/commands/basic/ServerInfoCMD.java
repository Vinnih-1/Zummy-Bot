package kazumy.project.zummy.commands.basic;

import kazumy.project.zummy.commands.BaseCommand;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class ServerInfoCMD extends BaseCommand {

	public ServerInfoCMD() {
		super("serverinfo", "Lista as informações atuais do servidor", "", "[%s]serverinfo", null);
	}

	@Override
	public void execute(Member member, Message message) {
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
	
	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode("U+1F451"));
	}
}
