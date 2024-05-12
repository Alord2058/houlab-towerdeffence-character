package jp.houlab.alord2058.character.blender.Ultimate;

import jp.houlab.alord2058.character.Character;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
import org.bukkit.scoreboard.Team;

import java.util.Set;

public class UltimateSkill implements Listener {

    private final Character javaplugin;

    public UltimateSkill(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    SaveTextDisplaySData team1SaveTextDisplaySData = new SaveTextDisplaySData();
    SaveTextDisplaySData team2SaveTextDisplaySData = new SaveTextDisplaySData();

    @EventHandler
    public void UltimateDetect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material material = event.getMaterial();
        Set<String> tag = player.getScoreboardTags();
        int warpGate_Timer = this.javaplugin.getConfig().getInt("blender.warpGate_Timer");
        int warp_CT = this.javaplugin.getConfig().getInt("blender.warp_CT");
        Team pEntryTeam = Bukkit.getScoreboardManager().getMainScoreboard().getEntityTeam(player);
        String getTeamString = pEntryTeam.getName();

        if (event.getHand() == EquipmentSlot.HAND) {
            if (event.getAction().isRightClick()) {
                if (material.equals(Material.MUSIC_DISC_5) && player.getCooldown(material) == 0) {
                    if (tag.contains("blender") && tag.contains("blender_Ulting")) {
                        if(getTeamString.equals("1")) {

                            double warpXE = player.getX();
                            double warpYE = player.getY();
                            double warpZE = player.getZ();
                            Location warpLocationE = new Location(player.getWorld(), warpXE, warpYE, warpZE);
                            TextDisplay textDisplayE = (TextDisplay) player.getWorld().spawnEntity(warpLocationE, EntityType.TEXT_DISPLAY);
                            textDisplayE.setBackgroundColor(Color.fromRGB(0, 0, 0));
                            textDisplayE.text(Component.text("ABCDEFG"));
                            textDisplayE.setBillboard(Display.Billboard.CENTER);

                            TextDisplay textDisplayS = team1SaveTextDisplaySData.getTextDisplay();
                            Location warpLocationS = team1SaveTextDisplaySData.getWarpLocationS();
                            double warpXS = team1SaveTextDisplaySData.getWarpXS();
                            double warpYS = team1SaveTextDisplaySData.getWarpYS();
                            double warpZS = team1SaveTextDisplaySData.getWarpZS();

                            player.sendMessage(String.valueOf(textDisplayS));
                            player.sendMessage(String.valueOf(warpLocationS));

                            //warp method
                            new warpGateTimer(javaplugin, warpLocationS, warpLocationE, warpGate_Timer,warp_CT, textDisplayS, textDisplayE, player,
                                    warpXS, warpYS, warpZS, warpXE, warpYE, warpZE).runTaskTimer(javaplugin, 0, 1);

                            player.removeScoreboardTag("blender_Ulting");

                        } else if (getTeamString.equals("2")) {

                            double warpXE = player.getX();
                            double warpYE = player.getY();
                            double warpZE = player.getZ();
                            Location warpLocationE = new Location(player.getWorld(), warpXE, warpYE, warpZE);
                            TextDisplay textDisplayE = (TextDisplay) player.getWorld().spawnEntity(warpLocationE, EntityType.TEXT_DISPLAY);
                            textDisplayE.setBackgroundColor(Color.fromRGB(0, 0, 0));
                            textDisplayE.text(Component.text("ABCDEFG"));
                            textDisplayE.setBillboard(Display.Billboard.CENTER);

                            TextDisplay textDisplayS = team2SaveTextDisplaySData.getTextDisplay();
                            Location warpLocationS = team2SaveTextDisplaySData.getWarpLocationS();
                            double warpXS = team2SaveTextDisplaySData.getWarpXS();
                            double warpYS = team2SaveTextDisplaySData.getWarpYS();
                            double warpZS = team2SaveTextDisplaySData.getWarpZS();

                            player.sendMessage(String.valueOf(textDisplayS));
                            player.sendMessage(String.valueOf(warpLocationS));

                            //warp method
                            new warpGateTimer(javaplugin, warpLocationS, warpLocationE, warpGate_Timer, warp_CT, textDisplayS, textDisplayE, player,
                                    warpXS, warpYS, warpZS, warpXE, warpYE, warpZE).runTaskTimer(javaplugin, 0, 1);

                            player.removeScoreboardTag("blender_Ulting");
                        }

                    } else if (tag.contains("blender") && !tag.contains("blender_Ulting")) {
                        if (getTeamString.equals("1")) {
                            double warpXS = player.getX();
                            double warpYS = player.getY();
                            double warpZS = player.getZ();
                            Location warpLocationS = new Location(player.getWorld(), warpXS,warpYS,warpZS);
                            TextDisplay textDisplayS = (TextDisplay) player.getWorld().spawnEntity(warpLocationS, EntityType.TEXT_DISPLAY);
                            textDisplayS.text(Component.text("ABCDEFG"));
                            textDisplayS.setBackgroundColor(Color.fromRGB(0, 0, 0));
                            textDisplayS.setBillboard(Display.Billboard.CENTER);

                            team1SaveTextDisplaySData.setTextDisplay(textDisplayS);
                            team1SaveTextDisplaySData.setWarpLocationS(warpLocationS);
                            team1SaveTextDisplaySData.setWarpXS(warpXS);
                            team1SaveTextDisplaySData.setWarpYS(warpYS);
                            team1SaveTextDisplaySData.setWarpZS(warpZS);

                            player.sendMessage("ok");
                            player.addScoreboardTag("blender_Ulting");

                        } else if (getTeamString.equals("2")) {
                            double warpXS = player.getX();
                            double warpYS = player.getY();
                            double warpZS = player.getZ();
                            Location warpLocationS = new Location(player.getWorld(), warpXS,warpYS,warpZS);
                            TextDisplay textDisplayS = (TextDisplay) player.getWorld().spawnEntity(warpLocationS, EntityType.TEXT_DISPLAY);
                            textDisplayS.text(Component.text("ABCDEFG"));
                            textDisplayS.setBackgroundColor(Color.fromRGB(0, 0, 0));
                            textDisplayS.setBillboard(Display.Billboard.CENTER);

                            team2SaveTextDisplaySData.setTextDisplay(textDisplayS);
                            team2SaveTextDisplaySData.setWarpLocationS(warpLocationS);
                            team2SaveTextDisplaySData.setWarpXS(warpXS);
                            team2SaveTextDisplaySData.setWarpYS(warpYS);
                            team2SaveTextDisplaySData.setWarpZS(warpZS);

                            player.sendMessage("ok");
                            player.addScoreboardTag("blender_Ulting");
                        }
                    }
                }
            }
        }
    }
}