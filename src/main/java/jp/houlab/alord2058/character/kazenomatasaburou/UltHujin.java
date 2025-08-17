package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class UltHujin extends BukkitRunnable {

    Player player;
    Set<String> tag;
    int ultCount;
    double getPLX;
    double getPLY;
    double getPLZ;
    double vx;
    double vy;
    double vz;
    ArmorStand armorStand;

    public UltHujin (Player player, Set<String> tag, int ultCount,
                     double getPLX, double getPLY, double getPLZ, double vx, double vy, double vz,
                     ArmorStand armorStand) {
        if (ultCount < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.ultCount = ultCount;
        }

        this.player = player;
        this.tag =  tag;
        this.getPLX = getPLX;
        this.getPLY = getPLY;
        this.getPLZ = getPLZ;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.armorStand = armorStand;
    }

    @Override
    public void run() {
        if (ultCount > 0) {
            ultCount--;
            getPLX += vx;
            getPLY += vy;
            getPLZ += vz;
            Location location = new Location(armorStand.getWorld(), getPLX,getPLY + 0.75 ,getPLZ);
            armorStand.teleport(location);

            armorStand.getWorld().spawnParticle(Particle.SWEEP_ATTACK,location,5,0.5,0.5,0.5);

            @NotNull Collection<Player> getNearbyPlayers = armorStand.getWorld().getNearbyPlayers(location,1,1,1);

            for (Iterator<Player> i = getNearbyPlayers.iterator(); i.hasNext(); ) {
                Player playerIterator = i.next().getPlayer();
                if (playerIterator != null) {
                    if (playerIterator != player) {
                        playerIterator.damage(20,player);
                        playerIterator.getWorld().playSound(playerIterator.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK,1F,2F);
                        armorStand.remove();
                    }

                    if (playerIterator != player) {
                        playerIterator.damage(20,player);
                        playerIterator.getWorld().playSound(playerIterator.getLocation(),Sound.ENTITY_EVOKER_FANGS_ATTACK,1F,2F);
                        armorStand.remove();
                    }

                } else {
                    armorStand.remove();
                    return;
                }
            }

            } else if (ultCount == 0) {
                armorStand.remove();
                this.cancel();
        }
    }
}