package kazumy.project.zummy.listener;

import java.util.ArrayList;

import org.reflections.Reflections;
import org.simpleyaml.configuration.file.YamlFile;

import kazumy.project.zummy.commands.BaseCommand;
import lombok.val;
import lombok.var;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

	public static String PREFIX = null;
	private final YamlFile config;

	public EventListener(YamlFile config) {
		this.config = config;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		val args = event.getMessage().getContentRaw().replaceFirst(PREFIX, "").split(" ");
		
		if (args.length == 1 && event.getMessage().getMentionedUsers().contains(event.getJDA().getUserById("833105015639244870"))) {
			event.getMessage().reply(String.format("Use `[%s]help` para saber meus comandos", PREFIX)).queue();
			return;
		}

		if (!event.getMessage().getContentRaw().startsWith(PREFIX))
			return;
		val clazz = new Reflections("kazumy.project.zummy.commands").getSubTypesOf(BaseCommand.class);
		var commands = new ArrayList<BaseCommand>();

		if (config.getBoolean("bot.commands") 
				&& !event.getTextChannel().getId().equals(config.getString("bot.chat-id"))
				&& !event.getMember().hasPermission(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNEL,Permission.MESSAGE_MANAGE)) {
			
			event.getMessage().reply("Você não pode executar comandos aqui.").queue();
			return;
		}

		clazz.forEach(c -> {
			try {
				commands.add(c.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		if (commands.stream().noneMatch(c -> args[0].equals(c.getName()) || args[0].equals(c.getAliases()))) {
			event.getMessage().reply("Comando não encontrado.").queue();
		}

		commands.forEach(c -> {
			if (args[0].equals(c.getName()) || args[0].equals(c.getAliases())) {
				if (c.getPermission() == null || event.getMember().hasPermission(c.getPermission())) {
					c.execute(event.getMember(), event.getMessage());
				} else {
					event.getMessage().reply("Você não tem permissão para isto.").queue();
					;
				}
			}
		});
	}

	public void onButtonClick(ButtonClickEvent event) {
		val clazz = new Reflections("kazumy.project.zummy.commands").getSubTypesOf(BaseCommand.class);
		
		clazz.stream().map(c -> {
			try {
				return c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}).filter(c -> c.getButtonID() != null)
		.filter(c -> event.getButton().getId().startsWith(c.getButtonID()))
		.forEach(c -> c.buttonClickEvent(event));
	}

}