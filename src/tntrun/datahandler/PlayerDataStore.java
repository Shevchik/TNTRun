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

package tntrun.datahandler;

import java.util.Collection;
import java.util.HashMap;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class PlayerDataStore {

	private HashMap<String, ItemStack[]> plinv = new HashMap<String, ItemStack[]>();
	private HashMap<String, ItemStack[]> plarmor = new HashMap<String, ItemStack[]>();
	private HashMap<String, Collection<PotionEffect>> pleffects = new HashMap<String, Collection<PotionEffect>>();
	private HashMap<String, Location> plloc = new HashMap<String, Location>();
	private HashMap<String, Integer> plhunger = new HashMap<String, Integer>();
	private HashMap<String, GameMode> plgamemode = new HashMap<String, GameMode>();
	private HashMap<String, Integer> pllevel = new HashMap<String, Integer>();
	private HashMap<String, Boolean> plflight = new HashMap<String, Boolean>();

	public void storePlayerInventory(Player player) {
		PlayerInventory pinv = player.getInventory();
		plinv.put(player.getName(), pinv.getContents());
		pinv.clear();
	}

	public void storePlayerFlight(Player player) {
		plflight.put(player.getName(), player.getAllowFlight());
	}

	public void storePlayerArmor(Player player) {
		PlayerInventory pinv = player.getInventory();
		plarmor.put(player.getName(), pinv.getArmorContents());
		pinv.setArmorContents(null);
	}

	public void storePlayerPotionEffects(Player player) {
		Collection<PotionEffect> peff = player.getActivePotionEffects();
		pleffects.put(player.getName(), peff);
		for (PotionEffect peffect : peff) {
			player.removePotionEffect(peffect.getType());
		}
	}

	public void storePlayerLocation(Player player) {
		plloc.put(player.getName(), player.getLocation());
	}

	public void storePlayerHunger(Player player) {
		plhunger.put(player.getName(), player.getFoodLevel());
		player.setFoodLevel(20);
	}

	public void storePlayerGameMode(Player player) {
		plgamemode.put(player.getName(), player.getGameMode());
		player.setGameMode(GameMode.SURVIVAL);
	}

	public void storePlayerLevel(Player player) {
		pllevel.put(player.getName(), player.getLevel());
		player.setLevel(0);
	}

	public void restorePlayerInventory(Player player) {
		player.getInventory().setContents(plinv.remove(player.getName()));
	}

	public void restorePlayerArmor(Player player) {
		player.getInventory().setArmorContents(plarmor.remove(player.getName()));
	}

	public void restorePlayerPotionEffects(Player player) {
		player.addPotionEffects(pleffects.remove(player.getName()));
	}

	public void restorePlayerLocation(Player player) {
		player.teleport(plloc.remove(player.getName()));
	}
	
	public void restorePlayerFlight(Player player) {
		player.setAllowFlight(plflight.get(player.getName()));
	}

	public void clearPlayerLocation(Player player) {
		plloc.remove(player.getName());
	}

	public void restorePlayerHunger(Player player) {
		player.setFoodLevel(plhunger.remove(player.getName()));
	}

	public void restorePlayerGameMode(Player player) {
		player.setGameMode(plgamemode.remove(player.getName()));
	}

	public void restorePlayerLevel(Player player) {
		player.setLevel(pllevel.remove(player.getName()));
	}

}
