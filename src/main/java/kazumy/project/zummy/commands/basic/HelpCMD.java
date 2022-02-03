package kazumy.project.zummy.commands.basic;

import org.reflections.Reflections;

import kazumy.project.zummy.commands.BaseCommand;
import kazumy.project.zummy.components.MenuAction;
import lombok.val;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu.Builder;

public class HelpCMD extends BaseCommand implements MenuAction {

	public HelpCMD() {
		super("help", "Exibe a listagem de comandos básicos", "ajuda", "[%s]help", null);
	}

	@Override
	public void execute(Member member, Message message) {
		Builder menu = SelectionMenu.create("help.menu");
		val classes = new Reflections("kazumy.project.zummy.commands.basic").getSubTypesOf(BaseCommand.class);
 
		classes.forEach(c -> {
			try {
				val command = c.newInstance();
				command.configure();
				
				menu.addOption(command.getName(), 
						command.getName().concat(".value"),
						command.getDescription(),
						command.getEmoji());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		message.getTextChannel().sendMessage("Central de Ajuda para novos Membros").setActionRow(menu.build()).queue();
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}

	@Override
	public void configure() {
		this.setEmoji(Emoji.fromUnicode("U+2714"));
	}

	@Override
	public void selectionMenu(SelectionMenuEvent event) {
	}
}
