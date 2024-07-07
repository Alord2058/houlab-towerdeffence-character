package jp.houlab.alord2058.character.kazenomatasaburou;

import jp.houlab.alord2058.character.Character;
import org.bukkit.*;
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
        Material getIronSword = event.getMaterial();
        boolean isIronSword = event.getMaterial().equals(Material.IRON_SWORD);
        ItemStack getInMainHandIronSword = player.getInventory().getItemInMainHand();

        float gYaw = player.getYaw();
        float gPitch = player.getPitch();

        double vx = -sin(gYaw * PI / 180) * cos(gPitch * PI / 180);
        double vy = -sin(gPitch * PI / 180);
        double vz = cos(gYaw * PI / 180) * cos(gPitch * PI / 180);

        int ultTimer = this.javaplugin.getConfig().getInt("kazenomatasaburou.ultTimer");
        int ultCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.ultCT");
        int use_HujinCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.use_HujinCT");
        int vector_ratio = this.javaplugin.getConfig().getInt("kazenomatasaburou.vector_ratio");

        if (tag.contains("matasaburo")) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getAction().isRightClick()) {
                    if (isIronSword && player.getCooldown(getIronSword) == 0 && !tag.contains("matasaburo_Ulting")) {
                        Vector vector = player.getLocation().getDirection().multiply(1).setX(-vector_ratio*vx).setY(1).setZ(-vector_ratio*vz);
                        player.setVelocity(vector);
                        player.setCooldown(getIronSword, 10);
                        player.getWorld().spawnParticle(Particle.CRIT_MAGIC,player.getLocation(),25,0.5,0,0.5);
                        player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,player.getLocation(),10,0.5,2,0.5,new Particle.DustTransition(Color.GREEN,Color.LIME,1));
                        new UltFlying(ultTimer, player, tag, ultTimer, getInMainHandIronSword,ultCT).runTaskTimer(javaplugin, 0, 1);
                        player.getWorld().playSound(player.getLocation(),Sound.BLOCK_CAVE_VINES_HIT,1F,0.7F);
                        player.addScoreboardTag("matasaburo_Ulting");

                    } else if (isIronSword && player.getCooldown(getIronSword) == 0 && tag.contains("matasaburo_Ulting")) {
                        Block getPTargetBlock = player.getTargetBlockExact(100);
                        String getTeamName = Bukkit.getScoreboardManager().getMainScoreboard().getEntityTeam(player).getName();

                        double getPLX = player.getLocation().getX();
                        double getPLY = player.getLocation().getY() + 0.75;
                        double getPLZ = player.getLocation().getZ();

                        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(new Location(player.getWorld(),getPLX,getPLY,getPLZ),EntityType.ARMOR_STAND);
                        armorStand.setVisible(false);
                        player.setCooldown(getIronSword,use_HujinCT);
                        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_ENDER_DRAGON_FLAP,1F,1.5F);
                        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_BREEZE_IDLE_AIR,1F,0.1F);
                        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_BREEZE_IDLE_GROUND,1F,1F);

                        if (getPTargetBlock != null) {
                            Material targetBlockType = getPTargetBlock.getType();
                            new UltHujinKnockBack(player, tag, ultTimer, getPLX, getPLY, getPLZ, vx, vy, vz, targetBlockType, armorStand, getTeamName).runTaskTimer(javaplugin, 0, 1);

                        } else {
                            new UltHujin(player, tag, ultTimer, getPLX, getPLY, getPLZ, vx, vy, vz, armorStand, getTeamName).runTaskTimer(javaplugin, 0, 1);
                        }
                    }
                }
            }
        }
    }
}



