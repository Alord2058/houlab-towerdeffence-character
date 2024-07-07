package jp.houlab.alord2058.character.blender.Ultimate;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Active_Portal_Manager_Runnable extends BukkitRunnable {

    Location textDisplayS_Location;
    Location textDisplayE_Location;
    TextDisplay textDisplayS;
    TextDisplay textDisplayE;
    int warp_CT;
    int portal_Timer;

    public Active_Portal_Manager_Runnable(Location textDisplayS_Location, Location textDisplayE_Location,
                                          TextDisplay textDisplayS, TextDisplay textDisplayE,
                                          int warp_CT, int portal_Timer) {

        this.textDisplayS_Location = textDisplayS_Location;
        this.textDisplayE_Location = textDisplayE_Location;
        this.textDisplayS = textDisplayS;
        this.textDisplayE = textDisplayE;
        this.warp_CT = warp_CT;
        this.portal_Timer = portal_Timer;

    }

    Map<Player, Integer> portal_Warp_CT = new HashMap<>();

    @Override
    public void run() {
        //Portal Timer
        portal_Timer--;

        if (portal_Timer > 0) {

            //Player Warp_CT subtraction
            portal_Warp_CT.forEach((key,value) -> portal_Warp_CT.put(key,value - 1));

            //Portal Particle
            textDisplayE.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, textDisplayE.getLocation(), 20, 0.5, 1, 0.5,
                    new Particle.DustTransition(Color.BLACK, Color.AQUA, 1));

            textDisplayS.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, textDisplayS.getLocation(), 20, 0.5, 1, 0.5,
                    new Particle.DustTransition(Color.BLACK, Color.AQUA, 1));


            //textDisplayS
            Collection<Player> get_TextDisplayS_Near_by_Players = textDisplayS.getWorld().getNearbyPlayers(textDisplayS_Location, 0, 1.5, 0);

            for (Iterator<Player> i = get_TextDisplayS_Near_by_Players.iterator(); i.hasNext(); ) {
                Player playerIteratorS = i.next().getPlayer();
                if (playerIteratorS != null) {
                    portal_Warp_CT.putIfAbsent(playerIteratorS, 0);

                    if (portal_Warp_CT.get(playerIteratorS) <= 0) {
                        //teleport
                        Location l = textDisplayE.getLocation();

                        playerIteratorS.teleport(l);
                        playerIteratorS.getWorld().playSound(textDisplayE_Location, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1F, 2F);
                        playerIteratorS.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, l, 20, 0.5, 1, 0.5,
                                    new Particle.DustTransition(Color.BLACK, Color.AQUA, 2));

                        //Warp_Ct setting
                        portal_Warp_CT.put(playerIteratorS, warp_CT);
                    }

                } else {
                    return;
                }
            }

            //textDisplayE
            Collection<Player> get_TextDisplayE_Near_by_Players = textDisplayE.getWorld().getNearbyPlayers(textDisplayE_Location, 0, 1.5,0);

            for (Iterator<Player> i = get_TextDisplayE_Near_by_Players.iterator(); i.hasNext(); ) {
                Player playerIteratorE = i.next().getPlayer();
                if (playerIteratorE != null) {
                    portal_Warp_CT.putIfAbsent(playerIteratorE, 0);

                    if (portal_Warp_CT.get(playerIteratorE) <= 0) {
                        //teleport
                        Location l = textDisplayS.getLocation();
                        playerIteratorE.teleport(l);
                        playerIteratorE.getWorld().playSound(textDisplayS_Location, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1F, 2F);
                        playerIteratorE.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, l, 20, 0.5, 1, 0.5,
                                    new Particle.DustTransition(Color.BLACK, Color.AQUA, 2));

                        //Warp_Ct setting
                        portal_Warp_CT.put(playerIteratorE, warp_CT);
                    }

                } else {
                    return;
                }
            }

        } else if (portal_Timer == 0) {
            textDisplayE.getWorld().playSound(textDisplayE_Location,Sound.BLOCK_ENCHANTMENT_TABLE_USE,0.5F,2F);
            textDisplayS.getWorld().playSound(textDisplayS_Location,Sound.BLOCK_ENCHANTMENT_TABLE_USE,0.5F,2F);
            textDisplayS.remove();
            textDisplayE.remove();
            cancel();
        }
    }
}