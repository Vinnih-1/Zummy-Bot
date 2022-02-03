package kazumy.project.zummy.commands.moderation;

import java.awt.Color;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.SneakyThrows;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@SuppressWarnings("deprecation")
public class MuteCMD extends BaseCommand {

	public MuteCMD() {
		super("mute", "mutar", "[%s]mute [Membro]", "", Permission.MESSAGE_MANAGE);
		this.setButtonID("mute");
	}

	@Override
	public void execute(Member member, Message message) {
		val role = message.getGuild().getRoleById("844763776199819265");
		val args = message.getContentRaw().split(" ");
		
		if (args.length <= 1) {
			message.reply(String.format(this.getUsage(), EventListener.PREFIX)).queue();
			return;
		}
		val possibleTarget = message.getMentionedMembers();
		
		if (possibleTarget.isEmpty()) {
			message.reply("O membro mencionado não está em nosso discord").queue();
			return;
		}
		val target = possibleTarget.get(0);
		
		if (possibleTarget.get(0).getRoles().contains(role)) {
			message.reply("Este usuário já está mutado").queue();
			return;
		}
		val embed = new EmbedBuilder();
		
		embed.setTitle(target.getUser().getName());
		embed.setDescription("Você tem certeza que deseja mutar este membro?");
		embed.setThumbnail(target.getUser().getAvatarUrl());
		embed.setFooter(target.getUser().getId(), target.getUser().getAvatarUrl());
		embed.setColor(Color.YELLOW);
		
		message.reply(embed.build())
		.setActionRow(
				Button.success(this.getButtonID() + "-" + member.getUser().getId() + "_mute", "Mutar"), 
				Button.danger(this.getButtonID() + "-" + member.getUser().getId() + "_cancel", "Cancelar"))
		.queue();
	}

	@SneakyThrows
	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
		val clickedMember = event.getMember();
		
		if (event.getButton().getId().contains(clickedMember.getUser().getId())) {
			val action = event.getButton().getId().split("_")[1];
			val guild = event.getGuild();
			
			if (action.equals("mute")) {
				val userId = event.getMessage().getEmbeds().get(0).getFooter().getText(); 
				
				guild.loadMembers().onSuccess(members -> members.stream()
						.filter(loaded -> loaded.getUser().getId().equals(userId))
						.forEach(loaded -> {
							event.getGuild().addRoleToMember(loaded, guild.getRoleById("844763776199819265")).queue();
							val embed = new EmbedBuilder();
							embed.setColor(Color.YELLOW);
							embed.setDescription(String.format("<@%s>, usuário mutado com sucesso", clickedMember.getUser().getId()));
							event.getTextChannel().sendMessage(embed.build()).queue();
						}));
			} else if (action.equals("cancel")) {
				val embed = new EmbedBuilder();
				embed.setColor(Color.RED);
				embed.setDescription(String.format("<@%s>, ação cancelada com sucesso", clickedMember.getUser().getId()));
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
			event.getMessage().delete().queue();
			
		} else event.deferReply(true).setContent("Você não pode interferir nesta ação.").queue();
	}

	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode(""));
	}
}
