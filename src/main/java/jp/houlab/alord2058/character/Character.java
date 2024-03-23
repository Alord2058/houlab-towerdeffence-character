package jp.houlab.alord2058.character;

import jp.houlab.alord2058.character.kazenomatasaburou.ActiveSkill;
import jp.houlab.alord2058.character.kazenomatasaburou.PassiveSkill;
import jp.houlab.alord2058.character.kazenomatasaburou.UltimateSkill;
import org.bukkit.plugin.java.JavaPlugin;

public final class Character extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("on");
        getServer().getPluginManager().registerEvents(new PassiveSkill(this), this);
        getServer().getPluginManager().registerEvents(new ActiveSkill(),this);
        getServer().getPluginManager().registerEvents(new UltimateSkill(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("off");
    }
}
