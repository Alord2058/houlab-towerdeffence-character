package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;

public class UltParticleBuilder {

    ArmorStand armorStand;

    public UltParticleBuilder (ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public void worldSpawnParticle(Particle particle, Location location, int count) {
        armorStand.getWorld().spawnParticle(particle,location,count);
    }
}
