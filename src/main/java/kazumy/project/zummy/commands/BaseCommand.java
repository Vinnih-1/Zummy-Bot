package kazumy.project.zummy.commands;

import kazumy.project.zummy.buttons.ButtonAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

@Getter
@AllArgsConstructor
public abstract class BaseCommand implements ButtonAction {
	
	private String name, aliases, usage;
	private Permission permission;
	
	public abstract void execute(Member member, String[] args);
}
