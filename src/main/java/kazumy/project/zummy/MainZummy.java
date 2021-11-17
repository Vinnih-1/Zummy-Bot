package kazumy.project.zummy;

import kazumy.project.zummy.configuration.Config;
import kazumy.project.zummy.discord.Discord;

public class MainZummy {
	
	private final Config config;
	
	static {
		new MainZummy();
	}
	
	{
		config = Config.of(this).createConfig().readConfig();
		Discord.of(config).startBot();
	}
	
	public static void main(String[] args ) {
	}
}
