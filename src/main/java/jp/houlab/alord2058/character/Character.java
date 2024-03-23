package jp.houlab.alord2058.character;

import jp.houlab.alord2058.character.kazenomatasaburou.PassiveSkill;
import org.bukkit.plugin.java.JavaPlugin;

public final class Character extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("on");
        getServer().getPluginManager().registerEvents(new PassiveSkill(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("off");
    }
}
