package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class KazenoMatasaburou implements Listener {

    @EventHandler
    public void onElytraGride(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();
        entity.sendMessage("on");


    }

}
