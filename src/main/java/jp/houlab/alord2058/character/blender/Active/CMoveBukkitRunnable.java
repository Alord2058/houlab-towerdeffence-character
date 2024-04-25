package jp.houlab.alord2058.character.blender.Active;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CMoveBukkitRunnable extends BukkitRunnable {

    Player player;
    int cMove_Timer;

    public CMoveBukkitRunnable (Player player, int cMove_Timer) {

        if (cMove_Timer < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.cMove_Timer = cMove_Timer;
        }

        this.player = player;
    }

    @Override
    public void run() {

        if (cMove_Timer > 0) {
            cMove_Timer--;
            Vector vector = player.getLocation().getDirection().multiply(1).setX(0).setY(0).setZ(0);
            player.setVelocity(vector);

        } else {
            this.cancel();
        }
    }
}
