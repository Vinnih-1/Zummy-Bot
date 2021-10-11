package kazumy.project.zummy.commands.moderation;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class UnmuteCMD extends BaseCommand {

	public UnmuteCMD() {
		super("unmute", "unmutar", "[%s]unmute [Membro]", Permission.MESSAGE_MANAGE);
	}

	@Override
	public void execute(Member member, Message message) {
		val role = message.getGuild().getRoleById("896996113331130391");
		val args = message.getContentRaw().split(" ");
		
		if (args.length <= 1) {
			message.reply(String.format(this.getUsage(), EventListener.PREFIX)).queue();
			return;
		}
		val target = message.getMentionedMembers();
		
		if (target.isEmpty()) {
			message.reply("O membro mencionado não está em nosso discord").queue();
			return;
		}
		
		if (!target.get(0).getRoles().contains(role)) {
			message.reply("Este usuário não está mutado").queue();
			return;
		}
		
		message.getGuild().removeRoleFromMember(target.get(0), role).queue();
		message.reply(String.format("<@%s>, usuário desmutado com sucesso", message.getAuthor().getId())).queue();
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
}
