package jp.houlab.alord2058.character.kazenomatasaburou;

import jp.houlab.alord2058.character.Character;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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
        int getBrushCT = player.getCooldown(Material.BRUSH);
        int getFeatherCT = player.getCooldown(Material.FEATHER);
        Set<String> tag = player.getScoreboardTags();

        if (getBrushCT > 0 && tag.contains("matasaburo_Ulting")) {
            player.setCooldown(Material.BRUSH, ultCT);
            player.removeScoreboardTag("matasaburo_Ulting");
        }

        if (getFeatherCT > 0) {
            player.setCooldown(Material.FEATHER, ultCT);
        }
    }
}
