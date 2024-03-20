package jp.houlab.alord2058.character.kazenomatasaburou;

import jp.houlab.alord2058.character.Character;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;


public class KazenoMatasaburou implements Listener {

    private Character javaplugin;
    public KazenoMatasaburou(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    @EventHandler
    public void onElytraGride(EntityToggleGlideEvent event) {
        Player player = (Player) event.getEntity();
        int maxEnergy = this.javaplugin.getConfig().getInt("kazenomatasaburou.maxEnergy");

        new BukkitRunnable() {
            @Override
            public void run() {
                int playerCurrentEnergy = player.getLevel();
                boolean isGliding = player.isGliding();

                if (playerCurrentEnergy > 0 && isGliding) {
                    player.setLevel((playerCurrentEnergy - 1));
                    player.setExp((float) playerCurrentEnergy / maxEnergy);
                    player.damageItemStack(EquipmentSlot.CHEST, -10);

                } else if (playerCurrentEnergy == 0 && isGliding) {
                    player.damageItemStack(EquipmentSlot.CHEST, 431);
                    this.cancel();

                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(javaplugin,0,1);
    }

    @EventHandler
    public void detectElytraDamage(PlayerItemDamageEvent event){
        Player player = event.getPlayer();
        int itemStack = event.getDamage();
        int rechargeEnergy = this.javaplugin.getConfig().getInt("kazenomatasaburou.rechargeEnergy");
        int rechargeEnergyCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.rechargeEnergyCT");

        new BukkitRunnable() {
            @Override
            public void run() {
                int playerCurrentEnergy = player.getLevel();
                boolean isGliding = player.isGliding();

                if (itemStack == 431 && playerCurrentEnergy <= 20) {
                    player.setLevel(playerCurrentEnergy + rechargeEnergy);

                } else if (playerCurrentEnergy >= 18 && playerCurrentEnergy <= 22 && !isGliding){
                    player.damageItemStack(EquipmentSlot.CHEST, -431);

                } else if (playerCurrentEnergy >= 22 && !isGliding && playerCurrentEnergy <= 78) {
                    player.setLevel(playerCurrentEnergy + rechargeEnergy);

                } else if (isGliding) {
                    this.cancel();
                }

            }
        }.runTaskTimer(javaplugin,rechargeEnergyCT,1);

    }
}
