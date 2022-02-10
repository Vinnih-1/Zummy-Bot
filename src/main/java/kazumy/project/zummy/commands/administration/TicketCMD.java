package kazumy.project.zummy.commands.administration;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kazumy.project.zummy.MainZummy;
import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.commands.ticket.Ticket;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@SuppressWarnings("deprecation")
public class TicketCMD extends BaseCommand {
	
	public TicketCMD() {
		super("setticket", "", "[%s]setticket", "", Permission.ADMINISTRATOR);
		this.setButtonID("ticket");
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
		val ticketList = MainZummy.getInstance().getTicketStorage().getTickets();
		val config = MainZummy.getInstance().getConfig();
		val category = event.getGuild().getCategoryById(config.getString("ticket.category-id"));
		val name = "ticket-" + (ticketList.size() + 1);
		val channel = new ArrayList<TextChannel>();
		
		if (event.getButton().getId().equals(this.getButtonID() + "-" + "open-ticket")) {
			val hasTicketOpened = ticketList.stream().anyMatch(c -> c.getMember().getUser().getId().equals(event.getUser().getId()));

			if (hasTicketOpened) {
				event.deferReply(true).setContent("Você já tem um ticket aberto.").queue();
				return;
			}
			category.createTextChannel(name).queue(ticket -> {
				ticket.createPermissionOverride(event.getMember()).setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE).queue();

				channel.add(ticket);
				ticketList.add(Ticket.init()
						.member(event.getMember())
						.category(category)
						.channel(ticket)
						.build());
				ticketList.forEach(c -> {
					if (c.getMember().getUser().getId().equals(event.getUser().getId())) 
						c.setChannel(ticket);
				});
	            val embed = new EmbedBuilder();
	    		embed.setTitle("Atendimento da zPluginS");
	    		embed.setDescription(String.format("Seja bem vindo <@%s>, aqui a equipe irá lhe atender.", event.getUser().getId()));
	    		embed.setColor(Color.GREEN);
	    		
	    		ticket.sendMessage(embed.build()).setActionRow(Button.danger(this.getButtonID() + "-" + "close-ticket", "Fechar ticket")).queue();
			});
		}
		
		if (event.getButton().getId().equals(this.getButtonID() + "-" + "close-ticket")) {
			val ticket = ticketList.stream().filter(c -> c.getMember().getUser().getId().equals(event.getUser().getId()))
					.findFirst().orElse(null);
			MainZummy.getInstance().getTicketStorage().getTickets().remove(ticket);
			
			if (ticket == null) {
				event.getTextChannel().delete().queueAfter(1, TimeUnit.SECONDS);
				return;
			}
			ticket.getChannel().delete().queueAfter(1, TimeUnit.SECONDS);
		}
	}

	@Override
	public void execute(Member member, Message message) {
		message.delete().queue();
		
		val config = MainZummy.getInstance().getConfig();
		val description = new StringBuilder();
		config.getList("ticket.description")
			.forEach(description::append);
		val user = message.getJDA().getSelfUser();
		val embed = new EmbedBuilder();

			embed.setTitle(config.getString("ticket.title"));
			embed.setDescription(description.toString().replace(".", ". "));
			embed.setColor(Color.BLUE);
		val section = config.getConfigurationSection("ticket.fields");
		section.getKeys(false).forEach(s -> {
			embed.addField(section.getString(s + ".name"),
					section.getString(s + ".value").replace("\\n", "\n"), 
					section.getBoolean(s + ".inline"));
			
			System.out.println(section.getString(s + ".value"));
		});
			embed.setImage(config.getString("ticket.image"));
			embed.setFooter(user.getAsTag(), user.getAvatarUrl());
		
		message.getTextChannel().sendMessage(embed.build()).setActionRow(Button.secondary(this.getButtonID() + "-" + "open-ticket", "Abrir um ticket").withEmoji(this.getEmoji())).queue();
	}

	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode("U+1F4EB"));
	}
}
