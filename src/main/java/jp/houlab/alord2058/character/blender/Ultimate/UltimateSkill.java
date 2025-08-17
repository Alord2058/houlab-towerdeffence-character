package jp.houlab.alord2058.character.blender.Ultimate;

import jp.houlab.alord2058.character.Character;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UltimateSkill implements Listener {

    private final Character javaplugin;

    public UltimateSkill(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    Map<Player,Location> Blender_ult_LocationS = new HashMap<>();

    @EventHandler
    public void UltimateDetect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material material = event.getMaterial();
        Set<String> tag = player.getScoreboardTags();
        int portal_Timer = this.javaplugin.getConfig().getInt("blender.portal_Timer");
        int warp_CT = this.javaplugin.getConfig().getInt("blender.warp_CT");
        int ult_CT = this.javaplugin.getConfig().getInt("blender.ult_CT");

        if (event.getHand() == EquipmentSlot.HAND) {
            if (event.getAction().isRightClick()) {
                if (material.equals(Material.RECOVERY_COMPASS) && player.getCooldown(material) == 0) {
                    if (!tag.contains("blender_Ulting")) {

                        Blender_ult_LocationS.put(player,player.getLocation());
                        Location location = Blender_ult_LocationS.get(player);

                        double location_GetX = location.getX();
                        double location_GetY = location.getY();
                        double location_GetZ = location.getZ();
                        Location particle_Location = new Location(player.getWorld(),location_GetX, location_GetY + 1,location_GetZ);

                        player.getWorld().playSound(particle_Location, Sound.ENTITY_ELDER_GUARDIAN_DEATH,1F, 1.5F);
                        player.addScoreboardTag("blender_Ulting");
                        new PrePortalParticleRunnable(particle_Location, player, tag, ult_CT,material).runTaskTimer(javaplugin, 0, 1);

                    } else {
                        Location location = Blender_ult_LocationS.get(player);
                        double locationS_getX = location.getX();
                        double locationS_getY = location.getY();
                        double locationS_getZ = location.getZ();
                        Location locationS = new Location(player.getWorld(), locationS_getX,locationS_getY + 0.25,locationS_getZ);

                        TextDisplay textDisplayS = (TextDisplay) player.getWorld().spawnEntity(locationS, EntityType.TEXT_DISPLAY);
                        textDisplayS.setBackgroundColor(Color.fromRGB(0, 0, 0));
                        textDisplayS.text(Component.text("PortalSPortalSPortalSPortalSPortalSPortalSPortalSPortalSPortalSPortalSPortalSPortalS")
                                    .color(TextColor.color(0x0)));
                        textDisplayS.setBillboard(Display.Billboard.VERTICAL);
                        textDisplayS.setDisplayHeight(500);
                        textDisplayS.setRotation(0,0);
                        textDisplayS.setLineWidth(50);

                        double locationE_getX = player.getLocation().getX();
                        double locationE_getY = player.getLocation().getY();
                        double locationE_getZ = player.getLocation().getZ();

                        Location textDisplayE_Location = new Location(player.getWorld(),locationE_getX,locationE_getY + 0.25,locationE_getZ);

                        TextDisplay textDisplayE = (TextDisplay) player.getWorld().spawnEntity(textDisplayE_Location, EntityType.TEXT_DISPLAY);
                        textDisplayE.setBackgroundColor(Color.fromRGB(0, 0, 0));
                        textDisplayE.text(Component.text("PortalEPortalEPortalEPortalEPortalEPortalEPortalEPortalEPortalEPortalEPortalEPortalE")
                                    .color(TextColor.color(0x0)));
                        textDisplayE.setBillboard(Display.Billboard.VERTICAL);
                        textDisplayE.setDisplayHeight(500);
                        textDisplayE.setRotation(0,0);
                        textDisplayE.setLineWidth(50);

                        player.getWorld().playSound(textDisplayE_Location, Sound.ENTITY_ELDER_GUARDIAN_DEATH,1F, 2F);

                        new Active_Portal_Manager_Runnable(location,textDisplayE_Location,
                                textDisplayS, textDisplayE,
                                warp_CT, portal_Timer).runTaskTimer(javaplugin,warp_CT,1);

                        player.removeScoreboardTag("blender_Ulting");
                        player.setCooldown(material,ult_CT);
                    }
                }
            }
        }
    }
}