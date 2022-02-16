package kazumy.project.zummy.discord;

import javax.security.auth.login.LoginException;

import org.simpleyaml.configuration.file.YamlFile;

import kazumy.project.zummy.listener.EventListener;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Getter
@Data(staticConstructor = "of")
public class Discord {
	
	private final YamlFile config;
	private JDA bot;
	
	@SneakyThrows
	public Discord startBot() {
		try {
			System.out.println(config);
			System.out.println(config.getString("bot.token"));
			
			bot = JDABuilder.createDefault(config.getString("bot.token")).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
					.addEventListeners(new EventListener())
					.build().awaitReady();
			EventListener.PREFIX = config.getString("bot.prefix");
			bot.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing(String.format(config.getString("bot.status"), EventListener.PREFIX)));
		} catch (LoginException e) {
			System.out.println("[Zummy] Algo de errado com o token provido.");
		}
		
		return this;
	}
}
