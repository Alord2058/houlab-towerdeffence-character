package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import java.util.Set;

public class UltimateSkill implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Set<String> tag = player.getScoreboardTags();
        Material getBrush = event.getMaterial();

        if (tag.contains("kazenomatasaburou")) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getAction().isRightClick()) {
                    if (event.getMaterial().equals(Material.BRUSH) && player.getCooldown(getBrush) == 0) {
                        player.setCooldown(getBrush,20*15);

                    }
                }
            }
        }
    }
}
