package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;

public class UltParticleBuilder {

    ArmorStand armorStand;

    public UltParticleBuilder (ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public void worldSpawnParticle(Particle particle1, Particle particle2, Particle.DustTransition dustTransition, Particle particle3) {
        Location armorStand_Location = armorStand.getLocation();
        armorStand.getWorld().spawnParticle(particle1,armorStand_Location,15,1,1.5,1);
        armorStand.getWorld().spawnParticle(particle2,armorStand_Location,15,2,2,2,dustTransition);
        armorStand.getWorld().spawnParticle(particle3,armorStand_Location,5,1,1,1);
    }
}
