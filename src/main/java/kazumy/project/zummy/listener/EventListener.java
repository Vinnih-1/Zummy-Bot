package kazumy.project.zummy.listener;

import java.util.Arrays;
import java.util.List;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.commands.administration.ClearCMD;
import kazumy.project.zummy.commands.administration.LockCMD;
import kazumy.project.zummy.commands.administration.SlowModeCMD;
import kazumy.project.zummy.commands.administration.UnlockCMD;
import kazumy.project.zummy.commands.basics.AvatarCMD;
import kazumy.project.zummy.commands.basics.HelpCMD;
import kazumy.project.zummy.commands.basics.PingCMD;
import kazumy.project.zummy.commands.basics.ServerInfoCMD;
import kazumy.project.zummy.commands.basics.UserInfoCMD;
import kazumy.project.zummy.commands.moderation.BanCMD;
import kazumy.project.zummy.commands.moderation.KickCMD;
import kazumy.project.zummy.commands.moderation.MuteCMD;
import kazumy.project.zummy.commands.moderation.UnmuteCMD;
import lombok.Getter;
import lombok.val;
import lombok.var;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
	
	public static final String PREFIX = "-";
	
	@Getter private final List<BaseCommand> commands = Arrays.asList(
			new ClearCMD(), new LockCMD(), new UnlockCMD(), new SlowModeCMD(), new AvatarCMD(), new HelpCMD(),
			new PingCMD(), new ServerInfoCMD(), new UserInfoCMD(), new BanCMD(), new KickCMD(), new MuteCMD(), new UnmuteCMD());
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		var message = event.getMessage().getContentRaw();
		if (!message.startsWith(PREFIX)) return;
		val args = message.replaceFirst(PREFIX, "").split(" ");
		
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
