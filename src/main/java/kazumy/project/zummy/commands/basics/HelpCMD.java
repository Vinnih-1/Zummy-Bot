package kazumy.project.zummy.commands.basics;

import java.awt.Color;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@SuppressWarnings("deprecation")
public class HelpCMD extends BaseCommand {

	public HelpCMD() {
		super("help", "ajuda", "[%s]help", null);
	}

	@Override
	public void execute(Member member, Message message) {
		val embed = new EmbedBuilder();
		embed.setColor(Color.YELLOW);

		embed.setTitle("Comandos Atuais");
		embed.setThumbnail("https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");
		
		val commands = new EventListener().getCommands();
		val usages = new StringBuilder();

		commands.stream().filter(c -> c.getPermission() == null).forEach(c -> usages.append("`").append(String.format(c.getUsage(), EventListener.PREFIX)).append("`").append("\n"));
		
		embed.setDescription(usages.toString());
		message.reply(embed.build()).queue();
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
}
