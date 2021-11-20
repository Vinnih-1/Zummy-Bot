package kazumy.project.zummy;

import org.simpleyaml.configuration.file.YamlFile;

import kazumy.project.zummy.commands.ticket.storage.TicketStorage;
import kazumy.project.zummy.configuration.Config;
import kazumy.project.zummy.discord.Discord;
import lombok.Getter;

public class MainZummy {
	
	@Getter private final TicketStorage ticketStorage = new TicketStorage();
	@Getter private static final MainZummy instance = new MainZummy();
	@Getter private final YamlFile config;
	
	{
		config = new Config().basicConfig();
		Discord.of(config).startBot();
	}
	
	public static void main(String[] args ) {
	}
}