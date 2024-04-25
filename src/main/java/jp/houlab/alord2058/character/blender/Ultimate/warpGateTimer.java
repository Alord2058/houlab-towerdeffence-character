package jp.houlab.alord2058.character.blender.Ultimate;

import jp.houlab.alord2058.character.Character;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

public class warpGateTimer extends BukkitRunnable {

    Character javaplugin;
    Location warpLocationS;
    Location warpLocationE;
    int warpGate_Timer;
    TextDisplay textDisplayS;
    TextDisplay textDisplayE;
    Player player;
    double warpXS;
    double warpYS;
    double warpZS;
    double warpXE;
    double warpYE;
    double warpZE;
    double vxS;
    double vzS;
    double vxE;
    double vzE;

    public warpGateTimer(Character javaplugin, Location warpLocationS, Location warpLocationE, int warpGate_Timer,
                         TextDisplay textDisplayS, TextDisplay textDisplayE, Player player,
                         double warpXS, double warpYS, double warpZS, double warpXE, double warpYE,double warpZE,
                         double vxS, double vzS, double vxE, double vzE) {
        this.javaplugin = javaplugin;
        this.warpLocationS = warpLocationS;
        this.warpLocationE = warpLocationE;
        this.textDisplayS = textDisplayS;
        this.textDisplayE = textDisplayE;
        this.player = player;
        this.warpXS = warpXS;
        this.warpYS = warpYS;
        this.warpZS = warpZS;
        this.warpXE = warpXE;
        this.warpYE = warpYE;
        this.warpZE = warpZE;
        this.vxS = vxS;
        this.vzS = vzS;
        this.vxE = vxE;
        this.vzE = vzE;

        if (warpGate_Timer < 1) {
            throw new IllegalArgumentException("warpGate_Timer error.");
        } else {
            this.warpGate_Timer = warpGate_Timer;
        }
    }

    @Override
    public void run() {
        if (warpGate_Timer > 0) {

            warpGate_Timer--;

            Collection<Player> getNearByPlayers_S = textDisplayS.getWorld().getNearbyPlayers(warpLocationS, 1, 1, 1);
            Collection<Player> getNearByPlayers_E = textDisplayE.getWorld().getNearbyPlayers(warpLocationE, 1, 1, 1);

            for (Iterator<Player> i = getNearByPlayers_S.iterator(); i.hasNext(); ) {
                @Nullable Player playerIteratorS = i.next().getPlayer();
                if (playerIteratorS != null) {
                    Location vWarpLocationE = new Location(playerIteratorS.getWorld(),warpXE + vxE*2, warpYE, warpZE + vzE*2);
                    playerIteratorS.teleport(vWarpLocationE);
                } else {
                    return;
                }
            }

            for (Iterator<Player> i = getNearByPlayers_E.iterator(); i.hasNext(); ) {
                @Nullable Player playerIteratorE = i.next().getPlayer();
                if (playerIteratorE != null) {
                    Location vWarpLocationS = new Location(playerIteratorE.getWorld(),warpXS + vxS*2, warpYS, warpZS + vxS*2);
                    playerIteratorE.teleport(vWarpLocationS);
                } else {
                    return;
                }
            }

        } else if (warpGate_Timer == 0) {
            this.cancel();
        }
    }
}

