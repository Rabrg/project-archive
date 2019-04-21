/*
 * This file is part of TopPvP, PvP leader stats plugin!.
 * TopPvP is licensed under GNU General Public License v3.
 * Copyright (C) 2013 The Castaway Development Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
//Test
package org.CastawayDevelopment.TopPvP.Listeners;

import java.util.Iterator;
import java.util.LinkedHashSet;
import org.CastawayDevelopment.TopPvP.Managers.PlayerClass;
import org.CastawayDevelopment.TopPvP.Managers.PlayerManager;
import org.CastawayDevelopment.TopPvP.TopPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener
{

    private TopPvP plugin;

    public EntityListener(TopPvP plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Player Death Listener
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent event)
    {
        Entity victim = event.getEntity();

        if (victim instanceof Player)
        {
            // Player was killed

            if (victim.getLastDamageCause() instanceof EntityDamageByEntityEvent)
            {
                EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) victim.getLastDamageCause();
                Entity killer = entityDamageByEntityEvent.getDamager();

                if (killer instanceof Player || (killer instanceof Projectile && ((Projectile)killer).getShooter() instanceof Player))
                {
                    // Setting the killer to the shooter of Projectile, if it was a Projectile
                    if((killer instanceof Projectile && ((Projectile)killer).getShooter() instanceof Player))
                    {
                        killer = ((Projectile)killer).getShooter();
                    }
                    
                    // Player killed by player
                    // log it.
                    PlayerManager manager = plugin.getPlayerManager();
                    PlayerClass pcKiller = manager.getPlayer((Player) killer);
                    PlayerClass pcVictim = manager.getPlayer((Player) victim);
                    
                    pcVictim.getDeaths().increment();

                    
                    LinkedHashSet<String> lastKills = pcKiller.getLastKills();
                    if(!lastKills.contains(pcVictim.getName()))
                    {
                        pcKiller.getKills().increment();
                        
                        lastKills.add(pcVictim.getName());
                        if(lastKills.size() > 3)
                        {
                            Iterator<String> it = lastKills.iterator();
                            if(it.hasNext())
                            {
                                it.next();
                                it.remove();
                            }
                        }
                        // It holds now that the 'queue' does not contain
                        // more than three elements
                    }
                    
                    if(this.plugin.economy.isEnabled() && pcVictim.hasBountyIssued())
                    {
                        String issuerName = pcVictim.getBountyIssuer();
                        this.plugin.economy.depositPlayer(pcKiller.getName(), pcVictim.getBounty());
                        pcKiller.sendMessage(ChatColor.GREEN+"You have collected a bounty from %s for killing %s", issuerName, pcVictim.getName());
                        Player issuer = Bukkit.getPlayerExact(issuerName);
                        if(issuer != null)
                        {
                            issuer.sendMessage(ChatColor.GREEN+String.format("The bounty hunt was succesful! %s has been eliminated!", pcVictim.getName()));
                        }
                        pcVictim.resetBounty();
                    }
                    
                    pcKiller.update(this.plugin);
                    pcVictim.update(this.plugin);
                }
                else if (killer instanceof Monster || (killer instanceof Projectile && ((Projectile)killer).getShooter() instanceof Skeleton))
                {
                    // Player killed by monster
                }
            }
        }
        else if (victim instanceof Monster)
        {
            // Monster was killed
        }
    }
}
