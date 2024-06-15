package jp.houlab.alord2058.character.blender.Ultimate;

import jp.houlab.alord2058.character.Character;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class warpGateTimer extends BukkitRunnable {

    Character javaplugin;
    Location warpLocationS;
    Location warpLocationE;
    int warpGate_Timer;
    int warp_CT;
    TextDisplay textDisplayS;
    TextDisplay textDisplayE;
    Player player;
    double warpXS;
    double warpYS;
    double warpZS;
    double warpXE;
    double warpYE;
    double warpZE;

    public warpGateTimer(Character javaplugin, Location warpLocationS, Location warpLocationE, int warpGate_Timer,int warp_CT,
                         TextDisplay textDisplayS, TextDisplay textDisplayE, Player player,
                         double warpXS, double warpYS, double warpZS, double warpXE, double warpYE,double warpZE) {

        this.javaplugin = javaplugin;
        this.warpLocationS = warpLocationS;
        this.warpLocationE = warpLocationE;
        this.warp_CT = warp_CT;
        this.textDisplayS = textDisplayS;
        this.textDisplayE = textDisplayE;
        this.player = player;
        this.warpXS = warpXS;
        this.warpYS = warpYS;
        this.warpZS = warpZS;
        this.warpXE = warpXE;
        this.warpYE = warpYE;
        this.warpZE = warpZE;

        if (warpGate_Timer < 1) {
            throw new IllegalArgumentException("warpGate_Timer error.");
        } else {
            this.warpGate_Timer = warpGate_Timer;
        }
    }

    //Player Portal_Warp CoolDown Manager
    Map<UUID, Integer> portalWarpCoolDown_Map = new HashMap<>();

    @Override
    public void run() {
        if (warpGate_Timer > 0) {

            warpGate_Timer--;

            Collection<Player> getNearByPlayers_S = textDisplayS.getWorld().getNearbyPlayers(warpLocationS, 0.5, 0.5, 0.5);
            Collection<Player> getNearByPlayers_E = textDisplayE.getWorld().getNearbyPlayers(warpLocationE, 0.5, 0.5, 0.5);


            for (Iterator<Player> i = getNearByPlayers_S.iterator(); i.hasNext(); ) {
                Player getPlayer_S = i.next().getPlayer();
                UUID getPlayerUUID_S = getPlayer_S.getUniqueId();
                if (!portalWarpCoolDown_Map.containsKey(getPlayerUUID_S)) {
                    portalWarpCoolDown_Map.put(getPlayerUUID_S,warp_CT);

                } else {
                    if (portalWarpCoolDown_Map.get(getPlayerUUID_S) < 0) {
                        getPlayer_S.teleport(warpLocationE);
                        portalWarpCoolDown_Map.put(getPlayerUUID_S,warp_CT);
                    }
                }

            }

            for (Iterator<Player> i = getNearByPlayers_E.iterator(); i.hasNext(); ) {
                Player getPlayer_E = i.next().getPlayer();
                UUID getPlayerUUID_E = getPlayer_E.getUniqueId();
                if (!portalWarpCoolDown_Map.containsKey(getPlayerUUID_E)) {
                    portalWarpCoolDown_Map.put(getPlayerUUID_E,warp_CT);

                } else {
                    if (portalWarpCoolDown_Map.get(getPlayerUUID_E) < 0) {
                        getPlayer_E.teleport(warpLocationS);
                        portalWarpCoolDown_Map.put(getPlayerUUID_E,warp_CT);
                    }
                }

            }

            if (!portalWarpCoolDown_Map.isEmpty()) {
                    portalWarpCoolDown_Map.forEach((key,value) -> portalWarpCoolDown_Map.put(key, value - 1));
            }


        } else if (warpGate_Timer == 0) {
            textDisplayS.remove();
            textDisplayE.remove();
            this.cancel();
        }
    }
}

