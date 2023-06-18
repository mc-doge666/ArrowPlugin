package com.example.arrowplugin;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public class ArrowPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(this, this);
        String plugin = ("ArrowPlugin");
        Logger logger = this.getLogger();
        logger.info(plugin + "插件已加载");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack bow = player.getInventory().getItemInMainHand();
            if (bow.getType() == Material.BOW) {
                // 取消事件，防止原始箭矢发射
                event.setCancelled(true);
                // 发射箭矢
                new BukkitRunnable() {
                    int count = 0;
                    @Override
                    public void run() {
                        if (count < 10) {
                            Arrow arrow = player.launchProjectile(Arrow.class);
                            arrow.setVelocity(event.getProjectile().getVelocity());
                            count++;
                        } else {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(this, 0L, 5L);
            }
        }
    }
    @Override
    public void onDisable() {
        String plugin = ("*ArrowPlugin");
        Logger logger = this.getLogger();
        logger.info(plugin + "插件已卸载");
    }
}
