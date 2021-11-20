package kazumy.project.zummy.commands.ticket;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

@Data
@ToString
@Builder(builderMethodName = "init", builderClassName = "build")
public class Ticket {
	
	private Member member;
	private Category category;
	private TextChannel channel;
}
