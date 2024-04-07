package jp.houlab.alord2058.character.kazenomatasaburou;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class UltHujin extends BukkitRunnable {

    Player player;
    Set<String> tag;
    int timer;
    int ultCount;
    ArmorStand armorStand;
    double getPLX;
    double getPLY;
    double getPLZ;
    double vx;
    double vy;
    double vz;

    public UltHujin (Player player, int ultCount, Set<String> tag, int timer, ArmorStand armorStand, double getPLX, double getPLY, double getPLZ, double vx, double vy, double vz) {
        if (ultCount < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.ultCount = ultCount;
        }

        this.player = player;
        this.tag =  tag;
        this.timer = timer;
        this.armorStand = armorStand;
        this.getPLX = getPLX;
        this.getPLY = getPLY;
        this.getPLZ = getPLZ;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    public void worldSpawnParticle(Particle particle, Location location, int count) {
        armorStand.getWorld().spawnParticle(particle,location,count);
    }

    @Override
    public void run() {
            if (ultCount <= timer && ultCount > 0) {
                ultCount--;
                getPLX += vx;
                getPLY += vy;
                getPLZ += vz;
                Location location = new Location(armorStand.getWorld(), getPLX,getPLY,getPLZ);
                armorStand.teleport(location);

                Particle particle = Particle.EXPLOSION_LARGE;
                worldSpawnParticle(particle,location,1);

            } else {

                this.cancel();
            }
    }
}

