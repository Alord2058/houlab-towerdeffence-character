package jp.houlab.alord2058.character.blender.Utility;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DisplayTeleportSpace extends BukkitRunnable {

    Player player;

    public DisplayTeleportSpace(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        Block playerTargetBlock = player.getTargetBlockExact(20);

        if (player.getCooldown(Material.ECHO_SHARD) == 0) {
            if (playerTargetBlock != null) {
                if (playerTargetBlock.getRelative(0, 1, 0).getType().isEmpty() && playerTargetBlock.getRelative(0, 2, 0).getType().isEmpty()) {
                    double TargetBlock_X = playerTargetBlock.getLocation().getBlockX() + 0.5;
                    double TargetBlock_Y = playerTargetBlock.getLocation().getBlockY() + 2;
                    double TargetBlock_Z = playerTargetBlock.getLocation().getBlockZ() + 0.5;

                    Location TargetBlock_location = new Location(player.getWorld(), TargetBlock_X, TargetBlock_Y, TargetBlock_Z);
                    player.spawnParticle(Particle.DUST_COLOR_TRANSITION, TargetBlock_location, 10, 0.25, 0.5, 0.25,
                            new Particle.DustTransition(Color.BLACK, Color.AQUA, 1));

                    if (!player.getInventory().getItemInMainHand().toString().contains("ECHO_SHARD")) {
                        cancel();
                    }
                }
            }
        }
    }
}
