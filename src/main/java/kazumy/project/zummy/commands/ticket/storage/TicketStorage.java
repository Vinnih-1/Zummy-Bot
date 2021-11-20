package kazumy.project.zummy.commands.ticket.storage;

import java.util.ArrayList;
import java.util.List;

import kazumy.project.zummy.commands.ticket.Ticket;
import lombok.Getter;

public class TicketStorage {
	
	@Getter private final List<Ticket> tickets = new ArrayList<>();
	
}
