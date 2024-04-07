package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Set;

public class UltTask extends BukkitRunnable {

    Player player;
    Set<String> tag;
    int timer;
    ItemStack getBrush;
    int ultCount;
    int ultCT;

    public UltTask (int ultCount, Player player, Set<String> tag, int timer, ItemStack getBrush, int ultCT) {
        if (ultCount < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.ultCount = ultCount;
        }

        this.player = player;
        this.tag =  tag;
        this.timer = timer;
        this.getBrush = getBrush;
        this.ultCT = ultCT;

    }

     @Override
     public void run() {
         if (tag.contains("kazenomatasaburou")) {
             if (ultCount <= timer && ultCount >= timer - 10) {
                 ultCount--;

             } else if (timer - 10 <= 100 && ultCount > 0) {
                 ultCount--;
                 Vector vector = player.getLocation().getDirection().multiply(1).setX(0).setY(0).setZ(0);
                 player.setVelocity(vector);

             } else {
                 player.setCooldown(getBrush.getType(), ultCT);
                 player.removeScoreboardTag("ultNow");
                 player.addScoreboardTag("ultReady");
                 this.cancel();
             }
         }
     }
}
