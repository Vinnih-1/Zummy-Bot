package kazumy.project.zummy.discord;

import javax.security.auth.login.LoginException;

import kazumy.project.zummy.configuration.Config;
import kazumy.project.zummy.listener.EventListener;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Getter
@Data(staticConstructor = "of")
public class Discord {
	
	private final Config config;
	private JDA bot;
	
	@SneakyThrows
	public Discord startBot() {
		val token = String.valueOf(config.getJson().get("token"));
		val status = String.valueOf(config.getJson().get("status"));
		
		try {
			bot = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES).build().awaitReady();
			bot.addEventListener(new EventListener());
			
			bot.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing(status));
		} catch (LoginException e) {
			System.out.println("[Zummy] Algo de errado com o token provido.");
		}
		
		return this;
	}
}
