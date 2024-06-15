package jp.houlab.alord2058.character.kazenomatasaburou;

import jp.houlab.alord2058.character.Character;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MatasaburoDeathEvent implements Listener {

    private final Character javaplugin;

    public MatasaburoDeathEvent(Character javaplugin) {
        this.javaplugin = javaplugin;
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        int ultCT = this.javaplugin.getConfig().getInt("kazenomatasaburou.ultCT");
        @NotNull Material getIronSword = player.getInventory().getItemInMainHand().getType();

        @NotNull Set<String> tag = player.getScoreboardTags();

        if (tag.contains("matasaburo")) {
            player.removeScoreboardTag("matasaburo_Ulting");
            player.setCooldown(getIronSword, ultCT);
        }
    }
}
