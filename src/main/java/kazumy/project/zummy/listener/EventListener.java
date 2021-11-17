package kazumy.project.zummy.listener;

import java.util.ArrayList;

import org.reflections.Reflections;

import kazumy.project.zummy.commands.BaseCommand;
import lombok.val;
import lombok.var;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
	
	public static String PREFIX = null;
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.getMessage().getContentRaw().startsWith(PREFIX)) return;
		val classes = new Reflections("kazumy.project.zummy.commands").getSubTypesOf(BaseCommand.class);
		val args = event.getMessage().getContentRaw().replaceFirst(PREFIX, "").split(" ");
		var commands = new ArrayList<BaseCommand>(); 
		
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
					event.getMessage().reply("no-permission-message").queue();;
				}
			}
		});
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		val role = event.getGuild().getRoleById("896872524619587655");
		event.getGuild().addRoleToMember(event.getMember(), role).queue();
		
		System.out.println(String.format("[Join] %s entrou em nosso discord", event.getMember().getUser().getAsTag()));
	}
}
