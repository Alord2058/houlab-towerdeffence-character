package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class UltHujinKnockBack extends BukkitRunnable {

    Player player;
    Set<String> tag;
    int ultCount;
    double getPLX;
    double getPLY;
    double getPLZ;
    double vx;
    double vy;
    double vz;
    Material playerTargetBlock;
    ArmorStand armorStand;

    public UltHujinKnockBack(Player player, Set<String> tag, int ultCount, double getPLX, double getPLY, double getPLZ, double vx, double vy, double vz, Material playerTargetBlock, ArmorStand armorStand) {
        if (ultCount < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.ultCount = ultCount;
        }

        this.player = player;
        this.tag = tag;
        this.getPLX = getPLX;
        this.getPLY = getPLY;
        this.getPLZ = getPLZ;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.playerTargetBlock = playerTargetBlock;
        this.armorStand = armorStand;
    }

    @Override
    public void run() {
        if (ultCount > 0) {
            ultCount--;
            getPLX += vx;
            getPLY += vy;
            getPLZ += vz;
            Location location = new Location(armorStand.getWorld(), getPLX, getPLY + 0.75, getPLZ);
            armorStand.teleport(location);

            UltParticleBuilder ultParticleBuilder = new UltParticleBuilder(armorStand);
            Particle particle = Particle.SWEEP_ATTACK;
            ultParticleBuilder.worldSpawnParticle(particle, location, 1);

            Player getNearbyPlayers = (Player) armorStand.getWorld().getNearbyPlayers(location, 1, 1, 1);

            if (!tag.contains("kazenomatasaburou")) {
                getNearbyPlayers.damage(2);
            }

            if (playerTargetBlock.equals(location.getBlock().getType())) {
                Particle particleExplosion = Particle.EXPLOSION_LARGE;
                ultParticleBuilder.worldSpawnParticle(particleExplosion, location, 1);

                if (!tag.contains("kazenomatasaburou")) {
                    getNearbyPlayers.knockback(5, 1, 1);

                }
            }

        } else {
            this.cancel();
        }
    }
}