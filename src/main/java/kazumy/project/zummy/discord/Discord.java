package kazumy.project.zummy.discord;

import javax.security.auth.login.LoginException;

import kazumy.project.zummy.configuration.Config;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

@Getter
@Data(staticConstructor = "of")
public class Discord {
	
	private final Config config;
	private JDA bot;
	
	@SneakyThrows
	public Discord startBot() {
		val token = String.valueOf(config.getJson().get("token"));
		System.out.println(token);
		
		try {
			bot = JDABuilder.createDefault(token).build().awaitReady();
			
		} catch (LoginException e) {
			System.out.println("[Zummy] Algo de errado com o token provido.");
		}
		
		return this;
	}
	
	public void registerEvents(Object... listeners) {
		bot.addEventListener(listeners);
	}
}
