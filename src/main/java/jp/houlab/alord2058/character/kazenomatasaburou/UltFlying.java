package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class UltFlying extends BukkitRunnable {

    Player player;
    Set<String> tag;
    int timer;
    ItemStack getBrush;
    int ultCount;
    int ultCT;

    public UltFlying(int ultCount, Player player, Set<String> tag, int timer, ItemStack getBrush, int ultCT) {
        if (ultCount < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.ultCount = ultCount;
        }

        this.player = player;
        this.tag = tag;
        this.timer = timer;
        this.getBrush = getBrush;
        this.ultCT = ultCT;

    }

    @Override
    public void run() {
        if (ultCount <= timer && ultCount >= timer - 10) {
            ultCount--;

        } else if (timer - 10 <= 150 && ultCount > 0) {
            ultCount--;
            player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, player.getLocation(), 5, 0.2, -0.2, 0.2, new Particle.DustTransition(Color.GREEN, Color.LIME, 1));

        } else {
            player.setCooldown(getBrush.getType(), ultCT);
            player.removeScoreboardTag("matasaburo_Ulting");
            this.cancel();
        }
    }
}
