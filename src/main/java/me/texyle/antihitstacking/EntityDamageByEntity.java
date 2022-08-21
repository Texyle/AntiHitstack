package me.texyle.antihitstacking;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.*;

public class EntityDamageByEntity implements Listener {
    private static EntityDamageByEntity instance;
    private HashMap<UUID, List<UUID>> lastDamagers = new HashMap<UUID, List<UUID>>();
    private int maxDamagers;
    private int cooldown;

    public EntityDamageByEntity() {
        instance = this;

        reload();
    }

    public void reload() {
        maxDamagers = AntiHitstacking.getInstance().getConfig().getInt("max-damagers");
        cooldown = AntiHitstacking.getInstance().getConfig().getInt("damage-cooldown");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER))
            return;

        Entity damager = event.getDamager();
        Player damagerPlayer = null;

        if (damager instanceof Player) {
            damagerPlayer = (Player) damager;
        } else if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;
            ProjectileSource source = projectile.getShooter();

            if (source instanceof Player) {
                damagerPlayer = (Player) source;
            }
        }

        if (damagerPlayer == null) {
            return;
        }

        UUID damagerId = damagerPlayer.getUniqueId();
        UUID targetId = event.getEntity().getUniqueId();
        if (!lastDamagers.containsKey(targetId)) {
            lastDamagers.put(targetId, new ArrayList<>());
        }

        List<UUID> damagers = lastDamagers.get(targetId);

        if (damagers.size() < maxDamagers) {
            if (!damagers.contains(damagerPlayer.getUniqueId())) {
                damagers.add(damagerPlayer.getUniqueId());

                Bukkit.getScheduler().runTaskLater(AntiHitstacking.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if (Bukkit.getServer().getEntity(targetId) != null) {
                            lastDamagers.get(targetId).remove(damagerId);
                        } else {
                            lastDamagers.remove(targetId);
                        }
                    }
                }, cooldown);
            }
        } else {
            event.setCancelled(true);
        }
    }

    public static EntityDamageByEntity getInstance() {
        return instance;
    }
}
