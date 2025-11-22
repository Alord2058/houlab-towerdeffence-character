package jp.houlab.alord2058.character.kazenomatasaburou;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class UltHujinKnockBack extends BukkitRunnable {

    Player player;
    Set<String> tag;
    int ultCount;
    double getPLX;
    double getPLY;
    double getPLZ;
    double vx;
    double vy;
    double vz;
    Material playerTargetBlock;
    ArmorStand armorStand;

    public UltHujinKnockBack(Player player, Set<String> tag, int ultCount,
                             double getPLX, double getPLY, double getPLZ, double vx, double vy, double vz,
                             Material playerTargetBlock, ArmorStand armorStand) {
        if (ultCount < 1) {
            throw new IllegalArgumentException("count error.");
        } else {
            this.ultCount = ultCount;
        }

        this.player = player;
        this.tag = tag;
        this.getPLX = getPLX;
        this.getPLY = getPLY;
        this.getPLZ = getPLZ;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.playerTargetBlock = playerTargetBlock;
        this.armorStand = armorStand;
    }

    @Override
    public void run() {
        if (ultCount > 0) {
            ultCount--;

            getPLX += vx;
            getPLY += vy;
            getPLZ += vz;
            Location location = new Location(armorStand.getWorld(), getPLX, getPLY + 0.75, getPLZ);
            armorStand.teleport(location);

            armorStand.getWorld().spawnParticle(Particle.SWEEP_ATTACK,location,5,0.25,0.25,0.25);
            armorStand.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,location,10,1,0,1,new Particle.DustTransition(Color.GREEN,Color.LIME,1));

            Collection<Player> getNearbyPlayers = armorStand.getWorld().getNearbyPlayers(location, 1, 1, 1);
            Collection<Player> getNearbyPlayersKB = armorStand.getWorld().getNearbyPlayers(location, 5, 5, 5);

            for (Iterator<Player> i = getNearbyPlayers.iterator(); i.hasNext(); ) {
                Player playerIterator = i.next().getPlayer();
                if (playerIterator != null) {
                    if (playerIterator != player) {
                        playerIterator.damage(10,player);
                        playerIterator.getWorld().playSound(playerIterator.getLocation(),Sound.ENTITY_EVOKER_FANGS_ATTACK,1F,2F);
                        armorStand.remove();
                    }

                } else {
                    armorStand.remove();
                    return;
                }
            }

            if (playerTargetBlock.equals(location.getBlock().getType())) {

                UltParticleBuilder ultParticleBuilder = new UltParticleBuilder(armorStand);
                ultParticleBuilder.worldSpawnParticle(Particle.GUST,
                                                      Particle.DUST_COLOR_TRANSITION,
                                                      new Particle.DustTransition(Color.GREEN,Color.LIME, 3),
                                                      Particle.CLOUD);

                for (Iterator<Player> i = getNearbyPlayersKB.iterator(); i.hasNext(); ) {
                    Player playerIteratorKB = i.next().getPlayer();

                    if (playerIteratorKB != null) {
                        String tranceETPlayer = String.valueOf(player.getScoreboard().getEntityTeam(player));
                        String tranceETPlayerKB = String.valueOf(playerIteratorKB.getScoreboard().getEntityTeam(playerIteratorKB));

                        if(!tranceETPlayer.equals(tranceETPlayerKB)) {
                            double aSX = armorStand.getX();
                            double aSZ = armorStand.getZ();
                            double pIKBX = playerIteratorKB.getX();
                            double pIKBZ = playerIteratorKB.getZ();

                            double prePIKBvX = pIKBX - aSX;
                            BigDecimal pIKBvXbd = new BigDecimal(String.valueOf(prePIKBvX));
                            BigDecimal pIKBvXbd1 = pIKBvXbd.setScale(0, RoundingMode.UP);
                            double pIKBvX = pIKBvXbd1.doubleValue();
                            double pIKBvX1 = (1 / pIKBvX) * 8.0;

                            double prePIKBvZ = pIKBZ - aSZ;
                            BigDecimal pIKBvZbd = new BigDecimal(String.valueOf(prePIKBvZ));
                            BigDecimal pIKBvZbd1 = pIKBvZbd.setScale(0, RoundingMode.UP);
                            double pIKBvZ = pIKBvZbd1.doubleValue();
                            double pIKBvZ1 = (1 / pIKBvZ) * 8.0;

                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_ATTACK_IMPACT, 1F, 1F);
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1F, 1.1F);
                            Vector playerIteratorKBVec = playerIteratorKB.getLocation().getDirection().multiply(1).setX(pIKBvX1).setY(1.00).setZ(pIKBvZ1);
                            playerIteratorKB.setVelocity(playerIteratorKBVec);
                            armorStand.remove();

                            this.cancel();

                        } else {
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_ATTACK_IMPACT, 1F, 1F);
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1F, 1.1F);
                            Vector playerIteratorKBVec = playerIteratorKB.getLocation().getDirection().multiply(1).setX(0).setY(3.49).setZ(0);
                            playerIteratorKB.setVelocity(playerIteratorKBVec);
                            armorStand.remove();
                        }

                    } else {
                        armorStand.remove();
                        return;
                    }
                }
            }

        } else if (ultCount == 0) {
            armorStand.remove();
            this.cancel();
        }
    }
}