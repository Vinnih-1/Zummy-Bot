package kazumy.project.zummy.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

import org.simpleyaml.configuration.file.YamlFile;

import kazumy.project.zummy.MainZummy;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.val;

@SuppressWarnings("all")
public class Config {
	
	private static final String PATH = new File("").getAbsolutePath(); 
	private YamlFile yamlfile;
	
	@SneakyThrows
	public YamlFile basicConfig() {
		yamlfile = new YamlFile("configuration/config.yml");
		
		if (!yamlfile.exists()) yamlfile.createNewFile(false);
		else yamlfile.load();
		
		yamlfile.addDefault("bot.token", "Seu token");
		yamlfile.addDefault("bot.prefix", "-");
		yamlfile.addDefault("bot.chat-id", "");
		yamlfile.addDefault("bot.commands", false);
		yamlfile.addDefault("bot.status", "[%s] para informações");
		
		ticketConfig();
		yamlfile.save();
		
		
		return yamlfile;
	}
	
	public void ticketConfig() {
		yamlfile.setComment("ticket", " - ");
		yamlfile.setComment("ticket", " - ");
		
		
		yamlfile.addDefault("ticket.title", "📫  Central de Atendimento");
		yamlfile.addDefault("ticket.image", "https://i.imgur.com/cX8Czip.png");
		yamlfile.addDefault("ticket.suporte-id", "");
		yamlfile.addDefault("ticket.category-id", "");
		yamlfile.addDefault("ticket.description", Arrays.asList("Olá, seja bem-vindo a central de atendimento da zPluginS.",
				"Para iniciar seu atendimento reaja no ícone abaixo.",
				"Seu atendimento será realizado por meio de um canal privado."));
		
		if (yamlfile.contains("ticket.fields")) return;
		
		yamlfile.addDefault("ticket.fields.1.name", "Tickets:");
		yamlfile.addDefault("ticket.fields.1.value", ":incoming_envelope: **Abertos**: ?\\n:mailbox: **Totais**: ?");
		yamlfile.addDefault("ticket.fields.1.inline", true);
		yamlfile.addDefault("ticket.fields.2.name", "Links:");
		yamlfile.addDefault("ticket.fields.2.value", ":earth_americas: [Site](https://google.com/)\\n:globe_with_meridians: [Central](https://google.com/)\\n:bookmark: [Tutoriais](https://google.com/)");
		yamlfile.addDefault("ticket.fields.2.inline", true);
		yamlfile.addDefault("ticket.fields.3.name", "Horário de Atendimento:");
		yamlfile.addDefault("ticket.fields.3.value", ":timer: `08:00 as 19:00 (UTC-3)`");
		yamlfile.addDefault("ticket.fields.3.inline", true);
	}
}
