package jp.houlab.alord2058.character.blender.Utility;

import jp.houlab.alord2058.character.Character;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemHeldEvent implements Listener {

    private final Character javaplugin;

    public ItemHeldEvent(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        Block getPTargetBlock = player.getTargetBlockExact(20);

        int currently_Slot = event.getNewSlot();
        @Nullable ItemStack mainHand = player.getInventory().getItem(currently_Slot);

        if (mainHand != null) {
            if (mainHand.toString().contains("ECHO_SHARD")) {
                if (getPTargetBlock != null) {
                    if (getPTargetBlock.getRelative(0, 1, 0).getType().isEmpty() && getPTargetBlock.getRelative(0, 2, 0).getType().isEmpty()) {
                        new DisplayTeleportSpace(player).runTaskTimer(javaplugin, 0, 1);
                    }
                }
            }
        }
    }
}