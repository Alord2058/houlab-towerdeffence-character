package jp.houlab.alord2058.character.blender.Active;

import jp.houlab.alord2058.character.Character;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;


import java.util.Set;

public class ActiveSkill implements Listener {

    private final Character javaplugin;
    public ActiveSkill(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    @EventHandler
    public void onActiveSkillDetect(org.bukkit.event.player.PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Set<String> tag = player.getScoreboardTags();
        Material getEcho_Shard = event.getMaterial();
        Block getPTargetBlock = player.getTargetBlockExact(20);

        float gYaw = player.getYaw();
        float gPitch = player.getPitch();

        int cMove_Timer = this.javaplugin.getConfig().getInt("blender.cMove_Timer");

        if (tag.contains("blender")) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getAction().isRightClick()) {
                    if (event.getMaterial().equals(Material.ECHO_SHARD) && player.getCooldown(getEcho_Shard) == 0) {
                        if(getPTargetBlock != null) {
                            if(getPTargetBlock.getRelative(0,1,0).getType().isEmpty() && getPTargetBlock.getRelative(0,2,0).getType().isEmpty()) {
                                int getBlockX = getPTargetBlock.getX();
                                int getBlockY = getPTargetBlock.getY();
                                int getBlockZ = getPTargetBlock.getZ();
                                Location location = new Location(player.getWorld(), getBlockX,getBlockY + 1,getBlockZ);
                                player.teleport(location);
                                player.setRotation(gYaw,gPitch);
                                player.setCooldown(getEcho_Shard, 20);

                                new CMoveBukkitRunnable(player,cMove_Timer).runTaskTimer(javaplugin,0,1);

                            } else {
                                player.sendMessage("cant tp");
                            }

                        }
                    }
                }
            }
        }
    }
}