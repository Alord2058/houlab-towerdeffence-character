package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.Set;

public class ActiveSkill implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Set<String> tag = player.getScoreboardTags();
        Vector vector = player.getLocation().getDirection().multiply(1).setX(0).setY(1).setZ(0);
        Material getFeather = event.getMaterial();
        Location player_Location = player.getLocation();

        if (tag.contains("matasaburo")) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getAction().isRightClick()) {
                    if (event.getMaterial().equals(Material.FEATHER) && player.getCooldown(getFeather) == 0) {
                        player.setCooldown(getFeather,20*15);
                        player.setVelocity(vector);
                        player.getWorld().spawnParticle(Particle.CRIT,player_Location,25,0.5,0,0.5);
                        player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,player_Location,10,0.5,2,0.5,new Particle.DustTransition(Color.GREEN,Color.LIME,1));

                    }
                }
            }
        } else {
            return;
        }
    }
}