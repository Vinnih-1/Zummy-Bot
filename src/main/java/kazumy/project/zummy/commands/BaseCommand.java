package kazumy.project.zummy.commands;

import kazumy.project.zummy.buttons.ButtonAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

@Getter
@AllArgsConstructor
public abstract class BaseCommand implements ButtonAction {
	
	private String name, aliases, usage;
	private Permission permission;
	
	public abstract void execute(Member member, Message message);
}
