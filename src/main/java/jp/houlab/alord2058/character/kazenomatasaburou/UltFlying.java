package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Set;

public class UltFlying extends BukkitRunnable {

    Player player;
    Set<String> tag;
    int timer;
    ItemStack getIronSword;
    int ultCount;
    int ultCT;

    public UltFlying(int ultCount, Player player, Set<String> tag, int timer, ItemStack getIronSword, int ultCT) {
        if (ultCount < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.ultCount = ultCount;
        }

        this.player = player;
        this.tag = tag;
        this.timer = timer;
        this.getIronSword = getIronSword;
        this.ultCT = ultCT;

    }

    @Override
    public void run() {
        if (ultCount <= timer && ultCount >= timer - 10) {
            ultCount--;

        } else if (timer - 10 <= 100 && ultCount > 0) {
            ultCount--;
            Vector vector = player.getLocation().getDirection().multiply(1).setX(0).setY(0).setZ(0);
            player.setVelocity(vector);
            player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, player.getLocation(), 5, 0.2, -0.2, 0.2, new Particle.DustTransition(Color.GREEN, Color.LIME, 1));

        } else {
            player.setCooldown(getIronSword.getType(), ultCT);
            player.removeScoreboardTag("matasaburo_Ulting");
            this.cancel();
        }
    }
}
