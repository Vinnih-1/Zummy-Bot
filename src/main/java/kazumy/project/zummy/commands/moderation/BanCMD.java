package kazumy.project.zummy.commands.moderation;

import kazumy.project.zummy.commands.BaseCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class BanCMD extends BaseCommand {

	public BanCMD() {
		super("ban", "banir", "[%s]ban [Membro]", "", Permission.BAN_MEMBERS);
	}

	@Override
	public void execute(Member member, Message message) {
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
	
	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode(""));
	}
}
