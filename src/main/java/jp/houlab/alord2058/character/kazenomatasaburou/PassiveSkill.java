package jp.houlab.alord2058.character.kazenomatasaburou;

import jp.houlab.alord2058.character.Character;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
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

    //Passive Skill
    @EventHandler
    public void onElytraGride(EntityToggleGlideEvent event) {
        Player player = (Player) event.getEntity();
        Set<String> tag = player.getScoreboardTags();

        @NotNull Material equipElytra = player.getEquipment().getChestplate().getType();
        int maxEnergy = this.javaplugin.getConfig().getInt("kazenomatasaburou.maxEnergy");
        int rechargeEnergy = this.javaplugin.getConfig().getInt("kazenomatasaburou.rechargeEnergy");
        int decreaseEnergy = this.javaplugin.getConfig().getInt("kazenomatasaburou.decreaseEnergy");

            new BukkitRunnable() {
                @Override
                public void run() {
                    int playerCurrentEnergy = player.getLevel();
                    boolean isGliding = player.isGliding();

                    if(tag.contains("matasaburo_Ulting")) {
                        player.setLevel(80);
                        player.setExp(1);

                    } else {
                        if (isGliding) {
                            if (!tag.contains("glidingFlag")) {
                                player.addScoreboardTag("glidingFlag");
                            }

                            if(equipElytra.equals(Material.ELYTRA)) {
                                if (playerCurrentEnergy >= 81) {
                                    player.setLevel(80);
                                    player.setLevel(playerCurrentEnergy - decreaseEnergy);
                                    player.setExp((float) playerCurrentEnergy / maxEnergy);

                                } else if (playerCurrentEnergy > 0){
                                    player.setLevel(playerCurrentEnergy - decreaseEnergy);
                                    player.setExp((float) playerCurrentEnergy/maxEnergy);

                                } else if (playerCurrentEnergy == 0) {
                                    player.removeScoreboardTag("glidingFlag");
                                    player.setLevel(0);
                                    player.setExp(0);
                                    player.setGliding(false);
                                    player.setCooldown(Material.ELYTRA,40);
                                    new PassiveGlidingControl(player).runTaskTimer(javaplugin, 0, 1);
                                }
                            }

                        } else {
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(javaplugin, 0, 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                int playerCurrentEnergy = player.getLevel();
                boolean isGliding = player.isGliding();
                int elytraCT = player.getCooldown(Material.ELYTRA);
                boolean isOnGround = player.isOnGround();

                if (!isGliding) {
                    if (elytraCT == 0) {
                        if (playerCurrentEnergy != 0 && tag.contains("glidingFlag")) {
                            player.setCooldown(Material.ELYTRA,40);
                            player.removeScoreboardTag("glidingFlag");

                        } else {
                            if(isOnGround) {
                                if (playerCurrentEnergy <= 78) {
                                    player.setLevel(playerCurrentEnergy + rechargeEnergy);
                                    player.setExp((float) playerCurrentEnergy/maxEnergy);


                                } else if (playerCurrentEnergy == 79) {
                                    player.setLevel(playerCurrentEnergy + (rechargeEnergy - 1));
                                    player.setExp((float) playerCurrentEnergy/maxEnergy);

                                } else if (playerCurrentEnergy == 80) {
                                    player.setLevel(80);
                                    player.setExp((float) playerCurrentEnergy/maxEnergy);
                                }
                            }
                        }

                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(javaplugin, 0, 1);
    }

    @EventHandler
    public void onElytraClick (InventoryClickEvent event){
        InventoryType.SlotType clickedSlottype = event.getSlotType();
        int clickedSlotNumber = event.getRawSlot();
        ItemStack playerEquipment = event.getWhoClicked().getEquipment().getChestplate();
        String transPE = String.valueOf(playerEquipment);

        if (clickedSlottype == InventoryType.SlotType.ARMOR) {
            if (clickedSlotNumber == 6){
                if (transPE.contains("ELYTRA")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
