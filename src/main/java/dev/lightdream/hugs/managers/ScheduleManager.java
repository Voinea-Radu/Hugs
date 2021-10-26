package dev.lightdream.hugs.managers;

import dev.lightdream.hugs.Main;
import dev.lightdream.hugs.dto.User;
import org.bukkit.Bukkit;

import java.util.List;

public class ScheduleManager {

    private final Main plugin;
    public List<User> top;

    public ScheduleManager(Main plugin) {
        this.plugin = plugin;
        registerTopCalculator();
    }

    public void registerTopCalculator() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,()->{
            plugin.getLogger().info("Calculation top");
            List<User> users = plugin.databaseManager.getAll(User.class);

            users.sort((u1, u2) -> u2.hugs - u1.hugs);
            top = users;
            plugin.getLogger().info("Calculated top");
        }, 0, 30*60*20L);
    }

}
