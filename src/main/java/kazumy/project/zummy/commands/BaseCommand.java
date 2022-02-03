package kazumy.project.zummy.commands;

import kazumy.project.zummy.components.ButtonAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

@Getter
@ToString
@RequiredArgsConstructor
public abstract class BaseCommand implements ButtonAction {
	
	private final String name, description, aliases, usage;
	private final Permission permission;
	@Setter private String buttonID;
	@Setter private Emoji emoji;
	
	public abstract void configure();
	
	public abstract void execute(Member member, Message message);
}
