package kazumy.project.zummy.commands.administration;

import java.awt.Color;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.listener.EventListener;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@SuppressWarnings("deprecation")
public class UpdateCMD extends BaseCommand {

	public UpdateCMD() {
		super("update", "", "[%s]update <Nome do Plugin>; <Versão>; <Atualizações>; <Link>", Permission.ADMINISTRATOR);
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}

	@Override
	public void execute(Member member, Message message) {
		val args = message.getContentRaw().split(" ");
		
		if (args.length <= 1) {
			message.reply(String.format("`" + this.getUsage() + "`", EventListener.PREFIX)).queue();
			return;
		}	
		val text = String.join(" ", args).replaceFirst(EventListener.PREFIX + this.getName(), "").split(";");
		val embed = new EmbedBuilder();
		embed.setTitle("Atualização");
		embed.setDescription(String.format("**Plugin**\n%s\n**Versão**\n%s\n**Mudanças**\n%s", text[0], text[1], text[2]));
		embed.setColor(Color.DARK_GRAY);
		embed.setAuthor("zPluginS", "https://discord.gg/CUGf2wrbAW", "https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");
		embed.setFooter("zPluginS © Todos os direitos reservados.", "https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");
		embed.setThumbnail("https://cdn.discordapp.com/icons/832601856403701771/94a08bab250ed87791c68bec4e7a4013.png");		
		
		message.getJDA().getTextChannelById("865572465115267103").sendMessage(embed.build()).setActionRow(Button.link(text[3], "Download")).queue();
	}
}
