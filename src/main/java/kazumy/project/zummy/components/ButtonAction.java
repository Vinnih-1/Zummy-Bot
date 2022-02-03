package kazumy.project.zummy.buttons;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public interface ButtonAction {
	void buttonClickEvent(ButtonClickEvent event);
}
