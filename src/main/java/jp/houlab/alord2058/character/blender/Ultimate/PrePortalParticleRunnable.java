package jp.houlab.alord2058.character.blender.Ultimate;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class PrePortalParticleRunnable extends BukkitRunnable {

    Location blender_ult_LocationS;
    Player player;
    Set<String> tag;
    int ult_CT;
    Material material;

    public PrePortalParticleRunnable (Location blender_ult_LocationS, Player player, Set<String> tag, int ult_CT, Material material) {

        this.blender_ult_LocationS = blender_ult_LocationS;
        this.player = player;
        this.tag = tag;
        this.ult_CT = ult_CT;
        this.material = material;

    }

    @Override
    public void run() {

        if (!player.isDead() && tag.contains("blender_Ulting")) {
            player.getWorld().spawnParticle(Particle.CRIT_MAGIC,blender_ult_LocationS,3 ,0 ,1 ,0);
            player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,blender_ult_LocationS,8 ,0.25 ,1 ,0.25,
                    new Particle.DustTransition(Color.BLACK, Color.AQUA, 1));

        } else if (player.isDead()) {
                player.removeScoreboardTag("blender_Ulting");
                cancel();

        } else if (!tag.contains("blender_Ulting")) {
            cancel();
        }
    }
}
