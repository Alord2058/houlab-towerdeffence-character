package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PassiveGlidingControl extends BukkitRunnable {
    Player player;

    public PassiveGlidingControl (Player player) {
        this.player = player;

    }

    @Override
    public void run(){
        if(player.getCooldown(Material.ELYTRA) == 0 && player.isOnGround()) {
            player.setGliding(true);
            this.cancel();
        }
    }
}
