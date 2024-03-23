package jp.houlab.alord2058.character.kazenomatasaburou;

import jp.houlab.alord2058.character.Character;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class PassiveSkill implements Listener {

    //Javaplugin constructor
    private final Character javaplugin;
    public PassiveSkill(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    //Count until EnergyRecharge
    private int count = 0;

    //Passive Skill
    @EventHandler
    public void onElytraGride(EntityToggleGlideEvent event) {
        Player player = (Player) event.getEntity();
        int maxEnergy = this.javaplugin.getConfig().getInt("kazenomatasaburou.maxEnergy");
        int rechargeEnergy = this.javaplugin.getConfig().getInt("kazenomatasaburou.rechargeEnergy");
        int rechargeEnergyCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.rechargeEnergyCT");
        int minEnergy = this.javaplugin.getConfig().getInt("kazenomatasaburou.minEnergy");
        Set<String> tag = player.getScoreboardTags();


        new BukkitRunnable() {
            @Override
            public void run() {
                if (tag.contains("kazenomatasaburou")) {
                    int playerCurrentEnergy = player.getLevel();
                    boolean isGliding = player.isGliding();

                    if (playerCurrentEnergy > 0 && isGliding) {
                        player.setExp((float) playerCurrentEnergy / maxEnergy);
                        player.setLevel((playerCurrentEnergy - 1));
                        count = 0;

                    } else if (playerCurrentEnergy == 0 && isGliding) {
                        player.damageItemStack(EquipmentSlot.CHEST, -432);
                        player.damageItemStack(EquipmentSlot.CHEST, 431);

                    } else if (!isGliding) {
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer(javaplugin,0,1);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (tag.contains("kazenomatasaburou")) {
                    int playerCurrentEnergy = player.getLevel();
                    boolean isGliding = player.isGliding();

                    if (count == 40) {
                        if (playerCurrentEnergy == minEnergy && !isGliding) {
                            player.damageItemStack(EquipmentSlot.CHEST, -432);
                            player.setLevel(playerCurrentEnergy + rechargeEnergy);

                        } else if (playerCurrentEnergy <= maxEnergy - 2 && !isGliding){
                            player.setLevel(playerCurrentEnergy + rechargeEnergy );
                            player.setExp((float) playerCurrentEnergy / maxEnergy);

                        } else if (playerCurrentEnergy == (maxEnergy - 1) && !isGliding){
                            player.setLevel(playerCurrentEnergy + (rechargeEnergy -1) );
                            player.setExp((float) playerCurrentEnergy / maxEnergy);

                        } else if (playerCurrentEnergy == maxEnergy) {
                            player.setExp((float) playerCurrentEnergy / maxEnergy);
                        }

                    } else if (count < rechargeEnergyCT && !isGliding) {
                        count++;

                    } else if (isGliding) {
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer(javaplugin,0,1);
    }

    @EventHandler
    public void onElytraClick(InventoryClickEvent event) {
        int grSlot = event.getRawSlot();
        @NotNull HumanEntity player = event.getWhoClicked();
        Set<String> tag = player.getScoreboardTags();

        if (tag.contains("kazenomatasaburou")) {
            if(grSlot == 6) {
                event.setCancelled(true);
            }
        }
    }
}
