package com.tildenprep.mpizza;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import java.util.*;

import java.io.File;

import java.io.IOException;

/*
 Created by kenny on 12/18/13.
 */
public class MPizza extends JavaPlugin implements Listener {


    InvChecker ic = new InvChecker();
    HashMap<String, Boolean> abl = new HashMap<String, Boolean>();

    public void onEnable(){
        getLogger().info("onEnable has been invoked!");

    }


    public void onDisable(){
        getLogger().info("onDisable has been invoked!");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
       
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
            if(abl.get(player.getName()+"focusblast")==true){
                if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
                    player.launchProjectile(WitherSkull.class);
                    Vector v = player.getLocation().getDirection();
                    Location loc = player.getLocation();
                    for (int i = 100; i > 0; i--)
                    {
                        player.getWorld().playEffect(loc, Effect.SMOKE, 0);
                        loc = loc.add(v);
                    }
                }
            }
            else if(abl.get(player.getName()+"thunderpunch")==true){
                event.setCancelled(true);
                player.getWorld().strikeLightning(player.getTargetBlock(null, 30).getLocation());
                Vector v = player.getLocation().getDirection();
                Location loc = player.getLocation();
                for (int i = 100; i > 0; i--)
                {
                    player.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, 0);
                    loc = loc.add(v);
                }
            }
                //Creates a bolt of lightning at a given location. In this case, that location is where the player is looking.
                //Can only create lightning up to 30 blocks away.
            
            else if (abl.get(player.getName()+"thunderpunch")!=true)
            {
                player.sendMessage("Lightning Out of Thunderpunch Range");
            }
        }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        String text = event.getMessage();
        String[] words = text.split(" ");
        for (int i=0;i<words.length;i++){
            if (words[i].equalsIgnoreCase("swag")||words[i].equalsIgnoreCase("sweg")||words[i].toLowerCase().contains("sweg")||words[i].toLowerCase().contains("swag")){
                event.setCancelled(true);
                event.getPlayer().chat("§6§lPUNCH ME IN THE FACE IRL!!!!");
                ic.punishSwagSpeaker(event.getPlayer());

            }
            if(words[i].equalsIgnoreCase("yolo")||words[i].toLowerCase().contains("yolo")){
                event.setCancelled(true);
                event.getPlayer().chat("§4§lI IZ A IDYUT!!!!");
                event.getPlayer().sendMessage("§kCheeseIsGoodCheeseIsGoodCheeseIsGoodCheeseIsGoodCheeseIsGood");
                event.getPlayer().sendMessage("§2§lYou believe YOLO, so you should test that theory and die.");
                event.getPlayer().sendMessage("§kCheeseIsGoodCheeseIsGoodCheeseIsGoodCheeseIsGoodCheeseIsGood");
                PotionEffect yolonausea = new PotionEffect(PotionEffectType.CONFUSION, 999999999, 1);
                yolonausea.apply(event.getPlayer());
                PotionEffect yololock = new PotionEffect(PotionEffectType.SLOW, 999999999, 255);
                yololock.apply(event.getPlayer());
                PotionEffect yoloantipunch = new PotionEffect(PotionEffectType.SLOW_DIGGING, 999999999, 255);
                yoloantipunch.apply(event.getPlayer());
                PotionEffect yolohunger = new PotionEffect(PotionEffectType.HUNGER, 999999999, 127);
                yolohunger.apply(event.getPlayer());
                PotionEffect testTheTheory = new PotionEffect(PotionEffectType.WITHER, 999999999, 4);
                testTheTheory.apply(event.getPlayer());
            }
        }
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("nick")){ // If the player typed /nick then do the following...
            player.setDisplayName(args[0]);
            player.sendMessage("§6§lYour nickname " + args[0] + " has been assigned.");
            return true;
        }
        else if(cmd.getName().equalsIgnoreCase("superjump")){ // If the player typed /superjump then do the following...
            Player victim = null;
            PotionEffect jump = new PotionEffect(PotionEffectType.JUMP,30,255);
            if (args.length == 1){
                victim = Bukkit.getPlayerExact(args[0]);
                if(victim == null)
                {
                    sender.sendMessage("The player could not be found");
                    return true;
                }
            }
            else if(args.length == 0)
            {
                victim = player;
            }
            jump.apply(victim);
            victim.setVelocity(victim.getVelocity().setY(5.0));
            return true;
        }
        else if(cmd.getName().equalsIgnoreCase("thunderpunch")){ // If the player typed /thunderpunch then do the following...
        	Player victim = null;
            if (args.length!=1 && args.length!=2){
        		player.sendMessage("Must be on or off");
        		return true;
        	}
        	if (args.length == 2)
            {
                victim = Bukkit.getPlayerExact(args[1]);
                if(victim == null)
                {
                    sender.sendMessage("The player could not be found");
                    return true;
                }
            }
            else if (args.length == 1)
            {
                victim = player;
            }
        	if (args[0].equalsIgnoreCase("on")){
        		if (abl.get(victim.getName()+"thunderpunch")!=true){
        			abl.put(victim.getName()+"icepunch", false);
        			abl.put(victim.getName()+"thunderpunch", true);
        			player.sendMessage("Thunderpunch has been toggled on");
        		}
        		else {
        			player.sendMessage("Thunderpunch is already on");
        			return true;
        		}
        	}
        	else if (args[0].equalsIgnoreCase("off")){
        		if (abl.get(player.getName()+"thunderpunch")==true){
        			abl.put(player.getName()+"thunderpunch", false);
        			player.sendMessage("Thunderpunch has been toggled off");
        		}
        		else{
        			player.sendMessage("Thunderpunch is already off");
        			return true;
        		}
        	}
        	return true;
        }
        else if(cmd.getName().equalsIgnoreCase("icepunch")){ // If the player typed /icepunch then do the following...
        	Player victim = null;
            if (args.length!=1 && args.length!=2){
        		player.sendMessage("Must be on or off");
        		return true;
        	}
        	if (args.length == 2)
            {
                victim = Bukkit.getPlayerExact(args[1]);
                if(victim == null)
                {
                    sender.sendMessage("The player could not be found");
                    return true;
                }
            }
            else if (args.length == 1)
            {
                victim = player;
            }
        	if (args[0].equalsIgnoreCase("on")){
        		if (abl.get(victim.getName()+"icepunch")!=true){
        			abl.put(victim.getName()+"thunderpunch", false);
        			abl.put(victim.getName()+"icepunch", true);
        			player.sendMessage("Icepunch has been toggled on");
        		}
        		else {
        			player.sendMessage("Icepunch is already on");
        			return true;
        		}
        	}
        	else if (args[0].equalsIgnoreCase("off")){
        		if (abl.get(player.getName()+"Icepunch")==true){
        			abl.put(player.getName()+"Icepunch", false);
        			player.sendMessage("Icepunch has been toggled off");
        		}
        		else{
        			player.sendMessage("Icepunch is already off");
        			return true;
        		}
        	}
        	return true;
        }
        // Cam's wither skull command
        else if(cmd.getName().equalsIgnoreCase("focusblast")){
            Player victim = null;
            if (args.length!=1 && args.length !=2){
                player.sendMessage("/focusblast (on/off) (playername)");
                return true;
            }
            if (args.length == 2)
            {
                victim = Bukkit.getPlayerExact(args[1]);
                if(victim == null)
                {
                    sender.sendMessage("The player could not be found");
                    return true;
                }
            }
            else if (args.length == 1)
            {
                victim = player;
            }
            if (args[0].equalsIgnoreCase("on")){
                if (abl.get(victim.getName()+"focusblast")!=true){
        			abl.put(victim.getName()+"focusblast", true);
                    player.sendMessage("Focus Blast has been toggled on");
                    return true;
                }
                else {
                    player.sendMessage("Focus Blast is already on");
                    return true;
                }
            }
            else if (args[0].equalsIgnoreCase("off"))
                if (abl.get(victim.getName()+"focusblast")==true){
        			abl.put(victim.getName()+"focusblast", false);
                    player.sendMessage("Focus Blast has been toggled off");
                    return true;
                }
                else {
                    player.sendMessage("Focus Blast is already off");
                    return true;
                }
        }
        return false;
    }



   @EventHandler(priority= EventPriority.LOWEST)
    public void onEarlyJoin(PlayerJoinEvent event) {
        ((CraftPlayer)event.getPlayer()).getHandle().playerConnection.checkMovement = false;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onLateJoin(PlayerJoinEvent event) {
        ((CraftPlayer)event.getPlayer()).getHandle().playerConnection.checkMovement = false;
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onMonitorJoin(PlayerJoinEvent event) {
        if (!((CraftPlayer)event.getPlayer()).getHandle().playerConnection.checkMovement)
            getLogger().info("Successfully removed 'moving too quickly' from " + event.getPlayer().getName());
        else
            getLogger().info("Could not remove 'moving too quickly' from " + event.getPlayer().getName());
    }
}
