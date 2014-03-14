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

package tntrun.commands.setup;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tntrun.TNTRun;
import tntrun.commands.setup.arena.Create;
import tntrun.commands.setup.arena.Delete;
import tntrun.commands.setup.arena.Disable;
import tntrun.commands.setup.arena.Enable;
import tntrun.commands.setup.arena.Finish;
import tntrun.commands.setup.arena.SetArena;
import tntrun.commands.setup.arena.SetCountdown;
import tntrun.commands.setup.arena.SetGameLevel;
import tntrun.commands.setup.arena.SetGameLevelDestroyDelay;
import tntrun.commands.setup.arena.SetItemsRewards;
import tntrun.commands.setup.arena.SetLoseLevel;
import tntrun.commands.setup.arena.SetMaxPlayers;
import tntrun.commands.setup.arena.SetMinPlayers;
import tntrun.commands.setup.arena.SetMoneyRewards;
import tntrun.commands.setup.arena.SetSpawn;
import tntrun.commands.setup.arena.SetTeleport;
import tntrun.commands.setup.arena.SetTimeLimit;
import tntrun.commands.setup.arena.SetVotePercent;
import tntrun.commands.setup.lobby.SetLobby;
import tntrun.commands.setup.reload.ReloadBars;
import tntrun.commands.setup.reload.ReloadMSG;
import tntrun.commands.setup.selection.Clear;
import tntrun.commands.setup.selection.SetP1;
import tntrun.commands.setup.selection.SetP2;
import tntrun.messages.Messages;
import tntrun.selectionget.PlayerSelection;

public class SetupCommandsHandler implements CommandExecutor {

	private PlayerSelection plselection = new PlayerSelection();

	private HashMap<String, CommandHandlerInterface> commandHandlers = new HashMap<String, CommandHandlerInterface>();

	public SetupCommandsHandler(TNTRun plugin) {
		commandHandlers.put("setp1", new SetP1(plselection));
		commandHandlers.put("setp2", new SetP2(plselection));
		commandHandlers.put("clear", new Clear(plselection));
		commandHandlers.put("setlobby", new SetLobby(plugin));
		commandHandlers.put("reloadmsg", new ReloadMSG(plugin));
		commandHandlers.put("reloadbars", new ReloadBars(plugin));
		commandHandlers.put("create", new Create(plugin));
		commandHandlers.put("delete", new Delete(plugin));
		commandHandlers.put("setarena", new SetArena(plugin, plselection));
		commandHandlers.put("setgamelevel", new SetGameLevel(plugin, plselection));
		commandHandlers.put("setgameleveldestroydelay", new SetGameLevelDestroyDelay(plugin));
		commandHandlers.put("setloselevel", new SetLoseLevel(plugin, plselection));
		commandHandlers.put("setspawn", new SetSpawn(plugin));
		commandHandlers.put("setmaxplayers", new SetMaxPlayers(plugin));
		commandHandlers.put("setminplayers", new SetMinPlayers(plugin));
		commandHandlers.put("setvotepercent", new SetVotePercent(plugin));
		commandHandlers.put("setcountdown", new SetCountdown(plugin));
		commandHandlers.put("setitemsrewards", new SetItemsRewards(plugin));
		commandHandlers.put("setmoneyrewards", new SetMoneyRewards(plugin));
		commandHandlers.put("settimelimit", new SetTimeLimit(plugin));
		commandHandlers.put("setteleport", new SetTeleport(plugin));
		commandHandlers.put("finish", new Finish(plugin));
		commandHandlers.put("disable", new Disable(plugin));
		commandHandlers.put("enable", new Enable(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Player is expected");
			return true;
		}
		Player player = (Player) sender;
		// check permissions
		if (!player.hasPermission("tntrun.setup")) {
			Messages.sendMessage(player, Messages.nopermission);
			return true;
		}
		//execute command
		if (args.length > 0 && commandHandlers.containsKey(args[0])) {
			CommandHandlerInterface commandh = commandHandlers.get(args[0]);
			boolean result = commandh.handleCommand(player, Arrays.copyOfRange(args, 1, args.length));
			return result;
		}
		return false;
	}

}