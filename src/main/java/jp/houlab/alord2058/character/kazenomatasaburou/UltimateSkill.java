package jp.houlab.alord2058.character.kazenomatasaburou;

import jp.houlab.alord2058.character.Character;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Set;

import static java.lang.Math.*;

public class UltimateSkill implements Listener {

    private final Character javaplugin;

    public UltimateSkill(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    @EventHandler
    public void ultDetect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Set<String> tag = player.getScoreboardTags();
        Material getBrush = event.getMaterial();
        boolean isBrush = event.getMaterial().equals(Material.BRUSH);
        ItemStack getInMainHandBrush = player.getInventory().getItemInMainHand();

        float gYaw = player.getYaw();
        float gPitch = player.getPitch();

        double vx = -sin(gYaw * PI / 180) * cos(gPitch * PI / 180);
        double vy = -sin(gPitch * PI / 180);
        double vz = cos(gYaw * PI / 180) * cos(gPitch * PI / 180);

        double ultHeight = this.javaplugin.getConfig().getDouble("kazenomatasaburou.ultHeight");
        int ultTimer = this.javaplugin.getConfig().getInt("kazenomatasaburou.ultTimer");
        int ultCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.ultCT");
        int use_HujinCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.use_HujinCT");
        int vector_ratio = this.javaplugin.getConfig().getInt("kazenomatasaburou.vector_ratio");

        if (tag.contains("kazenomatasaburou")) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getAction().isRightClick()) {
                    if (isBrush && player.getCooldown(getBrush) == 0 && !tag.contains("kazenomatasaburou_Ulting")) {
                        Vector vector = player.getLocation().getDirection().multiply(1).setX(-vector_ratio*vx).setY(1).setZ(-vector_ratio*vz);
                        player.setVelocity(vector);
                        player.setCooldown(getBrush, 10);
                        new UltFlying(ultTimer, player, tag, ultTimer, getInMainHandBrush,ultCT).runTaskTimer(javaplugin, 0, 1);
                        player.addScoreboardTag("kazenomatasaburou_Ulting");

                    } else if (isBrush && player.getCooldown(getBrush) == 0 && tag.contains("kazenomatasaburou_Ulting")) {
                        Block getPTargetBlock = player.getTargetBlockExact(100);
                        String getTeamName = Bukkit.getScoreboardManager().getMainScoreboard().getEntityTeam(player).getName();

                        double getPLX = player.getLocation().getX();
                        double getPLY = player.getLocation().getY() + 0.75;
                        double getPLZ = player.getLocation().getZ();

                        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(new Location(player.getWorld(),getPLX,getPLY,getPLZ),EntityType.ARMOR_STAND);
                        armorStand.setVisible(false);
                        player.setCooldown(getBrush,10);

                        if (getPTargetBlock != null) {
                            Material targetBlockType = getPTargetBlock.getType();
                            new UltHujinKnockBack(player, tag, ultTimer, getPLX, getPLY, getPLZ, vx, vy, vz, targetBlockType, armorStand, getTeamName).runTaskTimer(javaplugin, 0, 1);

                        } else {
                            new UltHujin(player, tag, ultTimer, getPLX, getPLY, getPLZ, vx, vy, vz, armorStand, getTeamName).runTaskTimer(javaplugin, 0, 1);
                        }

                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}



