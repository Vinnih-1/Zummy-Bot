package kazumy.project.zummy.commands.basic;

import kazumy.project.zummy.commands.BaseCommand;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class PingCMD extends BaseCommand {

	public PingCMD() {
		super("ping", "Retorna o ping atual da minha API", "", "[%s]ping", null);
	}

	@Override
	public void execute(Member member, Message message) {
		message.reply("Meu ping atual é de " + message.getJDA().getGatewayPing() + "ms").queue();
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
	
	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode("U+1F49A"));
	}
}