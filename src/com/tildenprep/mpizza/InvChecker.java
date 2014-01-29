package com.tildenprep.mpizza;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.Material.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by Max on 1/5/14.
 */
public class InvChecker
{
    public void punishSwagSpeaker(Player offender)
    {
    PlayerInventory inv = offender.getInventory();
    String name = "Sweg";
    ItemStack stack = new ItemStack(Material.DIRT, 64);
    ItemMeta itemMeta = stack.getItemMeta();
    itemMeta.setDisplayName(name);
   	itemMeta.setLore(Arrays.asList("line1", "line2", "line3"));
    stack.setItemMeta(itemMeta);
    clearInv(offender);
	for(int i = 0; i > 36; i++)
	{
		inv.setItem(i, stack);	
    }
    }

    public void clearInv(Player victim)
    {
        victim.getInventory().setContents(null);
        victim.getInventory().setArmorContents(null);
    }
}
