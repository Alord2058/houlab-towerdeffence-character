package jp.houlab.alord2058.character.blender.Ultimate;

import jp.houlab.alord2058.character.Character;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Set;

import static java.lang.Math.*;
import static java.lang.Math.PI;

public class UltimateSkill implements Listener {

    private final Character javaplugin;

    public UltimateSkill(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    SaveTextDisplaySData saveTextDisplaySData = new SaveTextDisplaySData();

    @EventHandler
    public void UltimateDetect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material material = event.getMaterial();
        Set<String> tag = player.getScoreboardTags();
        int warpGate_Timer = this.javaplugin.getConfig().getInt("blender.warpGate_Timer");

        if (event.getHand() == EquipmentSlot.HAND) {
            if (event.getAction().isRightClick()) {
                if (material.equals(Material.MUSIC_DISC_5) && player.getCooldown(material) == 0) {
                    if (tag.contains("blender_Ulting") && tag.contains("blender")) {
                        double playerYaw = player.getYaw();
                        double playerPitch = player.getPitch();

                        double vxE = -sin(playerYaw * PI / 180) * cos(playerPitch * PI / 180);
                        double vzE =  cos(playerYaw * PI / 180) * cos(playerPitch * PI / 180);

                        double warpXE = player.getX();
                        double warpYE = player.getY();
                        double warpZE = player.getZ();
                        Location warpLocationE = new Location(player.getWorld(), warpXE,warpYE,warpZE);
                        TextDisplay textDisplayE = (TextDisplay) player.getWorld().spawnEntity(warpLocationE, EntityType.TEXT_DISPLAY);
                        textDisplayE.setBackgroundColor(Color.fromRGB(0, 0, 0));
                        textDisplayE.text(Component.text("ABCDEFG"));
                        textDisplayE.setBillboard(Display.Billboard.CENTER);

                        TextDisplay textDisplayS = saveTextDisplaySData.getTextDisplay();
                        Location warpLocationS = saveTextDisplaySData.getWarpLocationS();
                        double warpXS = saveTextDisplaySData.getWarpXS();
                        double warpYS = saveTextDisplaySData.getWarpYS();
                        double warpZS = saveTextDisplaySData.getWarpZS();
                        double vxS = saveTextDisplaySData.getVx();
                        double vzS = saveTextDisplaySData.getVz();

                        player.sendMessage(String.valueOf(textDisplayS));
                        player.sendMessage(String.valueOf(warpLocationS));

                        //warp method
                        new warpGateTimer(javaplugin, warpLocationS, warpLocationE, warpGate_Timer, textDisplayS, textDisplayE,player,
                                warpXS,warpYS,warpZS,warpXE,warpYE,warpZE,vxS,vzS,vxE,vzE).runTaskTimer(javaplugin,0,1);

                        player.removeScoreboardTag("blender_Ulting");

                    } else if (tag.contains("blender")) {
                        double playerYaw = player.getYaw();
                        double playerPitch = player.getPitch();

                        double vxS = -sin(playerYaw * PI / 180) * cos(playerPitch * PI / 180);
                        double vzS =  cos(playerYaw * PI / 180) * cos(playerPitch * PI / 180);

                        double warpXS = player.getX();
                        double warpYS = player.getY();
                        double warpZS = player.getZ();
                        Location warpLocationS = new Location(player.getWorld(), warpXS,warpYS,warpZS);
                        TextDisplay textDisplayS = (TextDisplay) player.getWorld().spawnEntity(warpLocationS, EntityType.TEXT_DISPLAY);
                        textDisplayS.text(Component.text("ABCDEFG"));
                        textDisplayS.setBackgroundColor(Color.fromRGB(0, 0, 0));
                        textDisplayS.setBillboard(Display.Billboard.CENTER);

                        saveTextDisplaySData.setTextDisplay(textDisplayS);
                        saveTextDisplaySData.setWarpLocationS(warpLocationS);
                        saveTextDisplaySData.setWarpXS(warpXS);
                        saveTextDisplaySData.setWarpYS(warpYS);
                        saveTextDisplaySData.setWarpZS(warpZS);
                        saveTextDisplaySData.setVx(vxS);
                        saveTextDisplaySData.setVz(vzS);

                        player.sendMessage("ok");
                        player.addScoreboardTag("blender_Ulting");

                    }
                }
            }
        }
    }
}