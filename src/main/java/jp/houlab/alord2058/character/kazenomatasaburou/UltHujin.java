package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class UltHujin extends BukkitRunnable {

    Player player;
    Set<String> tag;
    int ultCount;
    double getPLX;
    double getPLY;
    double getPLZ;
    double vx;
    double vy;
    double vz;
    ArmorStand armorStand;
    String teamName;

    public UltHujin (Player player, Set<String> tag, int ultCount,
                     double getPLX, double getPLY, double getPLZ, double vx, double vy, double vz,
                     ArmorStand armorStand, String teamName) {
        if (ultCount < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.ultCount = ultCount;
        }

        this.player = player;
        this.tag =  tag;
        this.getPLX = getPLX;
        this.getPLY = getPLY;
        this.getPLZ = getPLZ;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.armorStand = armorStand;
        this.teamName = teamName;
    }

    @Override
    public void run() {
        if (ultCount > 0) {
            ultCount--;
            getPLX += vx;
            getPLY += vy;
            getPLZ += vz;
            Location location = new Location(armorStand.getWorld(), getPLX,getPLY + 0.75 ,getPLZ);
            armorStand.teleport(location);

            UltParticleBuilder ultParticleBuilder = new UltParticleBuilder(armorStand);
            Particle particle = Particle.SWEEP_ATTACK;
            ultParticleBuilder.worldSpawnParticle(particle,location,1);

                @NotNull Collection<Player> getNearbyPlayers = armorStand.getWorld().getNearbyPlayers(location,1,1,1);

            for (Iterator<Player> i = getNearbyPlayers.iterator(); i.hasNext(); ) {
                Player playerIterator = i.next().getPlayer();
                if (playerIterator != null) {
                    String getTeamName = Bukkit.getScoreboardManager().getMainScoreboard().getEntityTeam(playerIterator).getName();

                    if (teamName.equals("1") && getTeamName.equals("2")) {
                        if (playerIterator != player) {
                            playerIterator.damage(2);
                        }

                    } else if (teamName.equals("2") && getTeamName.equals("1")) {
                        if (playerIterator != player) {
                            playerIterator.damage(2);
                        }
                    }
                } else {
                    return;
                }
            }

            } else if (ultCount == 0) {
                this.cancel();
        }
    }
}