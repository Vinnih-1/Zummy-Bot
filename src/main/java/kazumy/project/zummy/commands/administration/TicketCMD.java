package kazumy.project.zummy.commands.administration;

import kazumy.project.zummy.commands.BaseCommand;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class TicketCMD extends BaseCommand {

	public TicketCMD() {
		super("setticket", "", "", Permission.ADMINISTRATOR);
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}

	@Override
	public void execute(Member member, Message message) {
		val embed = new EmbedBuilder();
		
	}
}
