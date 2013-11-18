/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */

package tntrun.arena;

import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Rewards {

	private Object economy = null;
	public Rewards()
	{
		if (Bukkit.getPluginManager().getPlugin("Vault") != null)
		{
	        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	        }
		}
	}
	
	private List<ItemStack> itemrewards = new ArrayList<ItemStack>();
	public List<ItemStack> getItemRewads()
	{
		return itemrewards;
	}
	private int moneyreward = 0;
	public int getMoneyReward()
	{
		return moneyreward;
	}
	
	protected void setRewards(ItemStack[] rewards)
	{
		this.itemrewards.clear();
		for (ItemStack reward : rewards)
		{
			if (reward != null)
			{
				this.itemrewards.add(reward);
			}
		}
	}
	protected void setRewards(int money)
	{
		this.moneyreward = money;
	}
	
	
	protected void rewardPlayer(Player player)
	{
		for (ItemStack reward : itemrewards)
		{
			if (player.getInventory().firstEmpty() != -1)
			{
				player.getInventory().addItem(reward);
			}
			else
			{
				player.getWorld().dropItemNaturally(player.getLocation(), reward);
			}
		}
		if (moneyreward != 10)
		{
			rewardMoney(player.getName(), moneyreward);
		}
	}
	private void rewardMoney(String playername, int money)
	{
		if (economy != null)
		{
			Economy econ = (Economy) economy;
			econ.depositPlayer(playername, money);
		}
	}
	
	
	protected void saveToConfig(FileConfiguration config)
	{
		config.set("rewards.money", moneyreward);
		int rc = 1;
		for (ItemStack reward : itemrewards)
		{
			config.set("rewards.items."+rc, reward);
			rc++;
		}
	}
	
	protected void loadFromConfig(FileConfiguration config)
	{
		moneyreward = config.getInt("rewards.money", moneyreward);
		itemrewards.clear();
		ConfigurationSection cs = config.getConfigurationSection("rewards.items");
		if (cs != null)
		{
			for (String key : cs.getKeys(false))
			{
				itemrewards.add(config.getItemStack("rewards.items."+key));
			}
		}
	}
	
}
