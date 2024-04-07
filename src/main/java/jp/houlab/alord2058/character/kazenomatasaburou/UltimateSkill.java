package jp.houlab.alord2058.character.kazenomatasaburou;

import jp.houlab.alord2058.character.Character;
import org.bukkit.Location;
import org.bukkit.Material;
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
    public void onPreUltimate(PlayerInteractEvent event) {
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

        double ultHeight = this.javaplugin.getConfig().getInt("kazenomatasaburou.ultHeight");
        int ultTimer = this.javaplugin.getConfig().getInt("kazenomatasaburou.ultTimer");
        int ultCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.ultCT");
        int use_HujinCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.use_HujinCT");
        int vector_ratio = this.javaplugin.getConfig().getInt("kazenomatasaburou.vector_ratio");

        if (tag.contains("kazenomatasaburou")) {
                if (event.getHand() == EquipmentSlot.HAND) {
                    if (event.getAction().isRightClick()) {
                        if (isBrush && player.getCooldown(getBrush) == 0 && tag.contains("ultReady")) {
                                Vector vector = player.getLocation().getDirection().multiply(1).setX(-vector_ratio*vx).setY(1).setZ(-vector_ratio*vz);
                                player.setVelocity(vector);
                                player.setCooldown(getBrush, 10);
                                new UltTask(ultTimer, player, tag, ultTimer, getInMainHandBrush,ultCT).runTaskTimer(javaplugin, 0, 1);
                                player.removeScoreboardTag("ultReady");
                                player.addScoreboardTag("ultNow");

                        } else if (isBrush && player.getCooldown(getBrush) == 0 && tag.contains("ultNow")) {

                            double getPLX = player.getLocation().getX();
                            double getPLY = player.getLocation().getY();
                            double getPLZ = player.getLocation().getZ();
                            Location location = new Location(player.getWorld(),getPLX,getPLY,getPLZ);

                            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(location), EntityType.ARMOR_STAND);
                            armorStand.setVisible(false);
                            armorStand.addScoreboardTag("kazenoUlt");

                            new UltHujin(player, ultTimer, tag, ultTimer, armorStand, getPLX, getPLY, getPLZ, vx, vy, vz).runTaskTimer(javaplugin, 0, 1);

                        } else if (tag.contains("ultCT") && isBrush && player.getCooldown(getBrush) <= ultCT && player.getCooldown(getBrush) > use_HujinCT) {
                            event.setCancelled(true);

                        }
                }
            }
        }
    }
}



