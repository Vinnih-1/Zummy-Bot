package kazumy.project.zummy.listener;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import kazumy.project.zummy.commands.BaseCommand;
import lombok.val;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@SuppressWarnings("deprecation")
public class EventListener extends ListenerAdapter {

	public static String PREFIX = null;

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.getMessage().getContentRaw().startsWith(PREFIX)) return;
		
		val args = event.getMessage().getContentRaw().replaceFirst(PREFIX, "").split(" ");
		val command = new Reflections("kazumy.project.zummy.commands").getSubTypesOf(BaseCommand.class)
				.stream()
				.map(c -> {
					try {
						return c.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
						return null;
					}
				}).collect(Collectors.toList());
		Predicate<BaseCommand> predicate = c -> c.getName().equalsIgnoreCase(args[0]);

		if (command.stream().anyMatch(predicate))
			command.stream().filter(predicate).forEach(c -> {
				if (c.getPermission() == null || event.getMember().hasPermission(c.getPermission()))
					c.execute(event.getMember(), event.getMessage());
				else
					event.getMessage().reply("Você não tem permissão para isto.").queue();
			});
		else event.getMessage().reply("Comando não encontrado.").queue();
	}

	public void onButtonClick(ButtonClickEvent event) {
		new Reflections("kazumy.project.zummy.commands").getSubTypesOf(BaseCommand.class).stream()
			.map(c -> {
				try {
					return c.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}).filter(c -> c.getButtonID() != null)
			.filter(c -> event.getButton().getId().startsWith(c.getButtonID()))
			.forEach(c -> c.buttonClickEvent(event));
	}

}