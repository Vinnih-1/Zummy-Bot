package kazumy.project.zummy.listener;

import java.util.ArrayList;

import org.reflections.Reflections;
import org.simpleyaml.configuration.file.YamlFile;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.commands.administration.TicketCMD;
import lombok.val;
import lombok.var;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
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

		if (args.length == 1
				&& event.getMessage().getMentionedUsers().contains(event.getJDA().getUserById("833105015639244870"))) {
			event.getMessage().reply(String.format("Use `[%s]help` para saber meus comandos", PREFIX)).queue();
			return;
		}

		if (!event.getMessage().getContentRaw().startsWith(PREFIX))
			return;
		val classes = new Reflections("kazumy.project.zummy.commands").getSubTypesOf(BaseCommand.class);
		var commands = new ArrayList<BaseCommand>();

		if (config.getBoolean("bot.commands") && !event.getTextChannel().getId().equals(config.getString("bot.chat-id"))
				&& !event.getMember().hasPermission(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNEL,
						Permission.MESSAGE_MANAGE)) {
			event.getMessage().reply("Você não pode executar comandos aqui.").queue();
			return;
		}

		classes.forEach(c -> {
			try {
				commands.add(c.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		if (commands.stream().noneMatch(c -> args[0].equals(c.getName()) || args[0].equals(c.getAliases()))) {
			event.getMessage().reply("command-not-found-message").queue();
		}

		commands.forEach(c -> {
			if (args[0].equals(c.getName()) || args[0].equals(c.getAliases())) {
				if (c.getPermission() == null || event.getMember().hasPermission(c.getPermission())) {
					c.execute(event.getMember(), event.getMessage());
				} else {
					event.getMessage().reply("no-permission-message").queue();
					;
				}
			}
		});
	}

	public void onButtonClick(ButtonClickEvent event) {
		new TicketCMD().buttonClickEvent(event);
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		val role = event.getGuild().getRoleById("896872524619587655");
		event.getGuild().addRoleToMember(event.getMember(), role).queue();

		System.out.println(String.format("[Join] %s entrou em nosso discord", event.getMember().getUser().getAsTag()));
	}
}