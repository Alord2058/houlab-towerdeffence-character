package jp.houlab.alord2058.character;

import org.bukkit.plugin.java.JavaPlugin;

public final class Character extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("on");
        getServer().getPluginManager().registerEvents(new jp.houlab.alord2058.character.kazenomatasaburou.PassiveSkill(this), this);
        getServer().getPluginManager().registerEvents(new jp.houlab.alord2058.character.kazenomatasaburou.ActiveSkill(),this);
        getServer().getPluginManager().registerEvents(new jp.houlab.alord2058.character.kazenomatasaburou.UltimateSkill(this),this);
        getServer().getPluginManager().registerEvents(new jp.houlab.alord2058.character.blender.Active.ActiveSkill(this),this);
        getServer().getPluginManager().registerEvents(new jp.houlab.alord2058.character.blender.Ultimate.UltimateSkill(this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("off");
    }
}
