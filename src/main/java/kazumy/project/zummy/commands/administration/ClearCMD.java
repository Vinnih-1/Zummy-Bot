package kazumy.project.zummy.commands.administration;

import kazumy.project.zummy.commands.BaseCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class ClearCMD extends BaseCommand {

	public ClearCMD(String name, String aliases, String usage, Permission permission) {
		super(name, aliases, usage, permission);
	}

	@Override
	public void execute(Member member, String[] args) {
	}

	@Override
	public void buttonClickEvent(ButtonClickEvent event) {
	}
}
