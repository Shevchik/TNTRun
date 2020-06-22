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

package tntrun.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import tntrun.TNTRun;
import tntrun.arena.Arena;
import tntrun.messages.Messages;

public class Utils {

	public static boolean isNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {}
        return false;
    }

	public static boolean isDouble(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {}
		return false;
	}

	public static int playerCount() {
		int pCount = 0;
		for (Arena arena : TNTRun.getInstance().amanager.getArenas()) {
			pCount += arena.getPlayersManager().getPlayersCount();			
		}
		return pCount;
	}

	public static void displayInfo(CommandSender sender) {
		Messages.sendMessage(sender, "&7============" + Messages.trprefix + "============");
		Messages.sendMessage(sender, "&bPlugin Version: &f" + TNTRun.getInstance().getDescription().getVersion());
		Messages.sendMessage(sender, "&bWebsite: &fhttps://www.spigotmc.org/resources/tntrun_reloaded.53359/");
		Messages.sendMessage(sender, "&bTNTRun_reloaded Author: &fsteve4744");
	}

	public static void displayUpdate(Player player) {
		if (player.hasPermission("tntrun.version.check")) {
			TextComponent tc = getTextComponentPrefix();
			TextComponent message = new TextComponent(" New version available!");
			message.setColor(ChatColor.WHITE);
			tc.addExtra(message);

			TextComponent link = new TextComponent(" Click here to download");
			link.setColor(ChatColor.AQUA);
			link.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/tntrun_reloaded.53359/"));
			tc.addExtra(link);

			tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getUpdateMessage().create()));
			player.spigot().sendMessage(tc);
		}
	}

	private static TextComponent getTextComponentPrefix() {
		TextComponent tc = new TextComponent("[");
		tc.setColor(ChatColor.GRAY);
		TextComponent tc2 = new TextComponent("TNTRun");
		tc2.setColor(ChatColor.GOLD);
		TextComponent tc3 = new TextComponent("]");
		tc3.setColor(ChatColor.GRAY);
		tc.addExtra(tc2);
		tc.addExtra(tc3);
		return tc;
	}

	private static ComponentBuilder getUpdateMessage() {
		ComponentBuilder cb = new ComponentBuilder("Current version : ").color(ChatColor.AQUA).append(TNTRun.getInstance().getDescription().getVersion()).color(ChatColor.GOLD);
		cb.append("\nLatest version : ").color(ChatColor.AQUA).append(TNTRun.getInstance().version[0]).color(ChatColor.GOLD);
		return cb;
	}

	public static void displayHelp(Player player) {
		player.spigot().sendMessage(Utils.getTextComponent("/trsetup setlobby", true), Utils.getTextComponent(Messages.setuplobby));
		player.spigot().sendMessage(Utils.getTextComponent("/trsetup create {arena}", true), Utils.getTextComponent(Messages.setupcreate));
		player.spigot().sendMessage(Utils.getTextComponent("/trsetup setarena {arena}", true), Utils.getTextComponent(Messages.setupbounds));
		player.spigot().sendMessage(Utils.getTextComponent("/trsetup setloselevel {arena}", true), Utils.getTextComponent(Messages.setuploselevel));
		player.spigot().sendMessage(Utils.getTextComponent("/trsetup setspawn {arena}", true), Utils.getTextComponent(Messages.setupspawn));
		player.spigot().sendMessage(Utils.getTextComponent("/trsetup setspectate {arena}", true), Utils.getTextComponent(Messages.setupspectate));
		player.spigot().sendMessage(Utils.getTextComponent("/trsetup finish {arena}", true), Utils.getTextComponent(Messages.setupfinish));
	}

	public static String getTitleCase(String input) {
		return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
	}

	public static boolean isAir(Material material) {
		return material == Material.AIR || material == Material.CAVE_AIR || material == Material.VOID_AIR;
	}

	public static TextComponent getTextComponent(String text) {
		return getTextComponent(text, false);
	}

	public static TextComponent getTextComponent(String text, Boolean click) {
		TextComponent tc = new TextComponent(text);
		if (click) {
			String splitter = "[";
			if (text.contains("{")) {
				splitter = "{";
			}
			tc.setColor(ChatColor.GOLD);
			tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringUtils.substringBefore(text, splitter)));
			tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to select").create()));
			tc.addExtra(getTextComponentDelimiter(" - "));
		} else {
			tc.setColor(ChatColor.RED);
		}
		return tc;
	}

	private static TextComponent getTextComponentDelimiter(String delim) {
		TextComponent tc = new TextComponent(delim);
		tc.setColor(ChatColor.WHITE);
		return tc;
	}
}
