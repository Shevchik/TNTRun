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

package tntrun.commands.setup.arena;

import org.bukkit.entity.Player;

import tntrun.TNTRun;
import tntrun.arena.Arena;
import tntrun.commands.setup.CommandHandlerInterface;
import tntrun.messages.Messages;
import tntrun.utils.Utils;

public class SetRegenerationDelay implements CommandHandlerInterface {
	
	private final TNTRun plugin;

	public SetRegenerationDelay(TNTRun plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean handleCommand(Player player, String[] args) {
		Arena arena = plugin.amanager.getArenaByName(args[0]);
		if (arena != null) {
			if (arena.getStatusManager().isArenaEnabled()) {
				Messages.sendMessage(player, Messages.trprefix + Messages.arenanotdisabled.replace("{ARENA}", args[0]));
				return true;
			}
			if (!Utils.isNumber(args[1]) || Integer.parseInt(args[1]) <= 0) {
				Messages.sendMessage(player, Messages.trprefix + "&c RegenerationDelay must be a positive integer");
				return true;
			}
			arena.getStructureManager().setRegenerationDelay(Integer.parseInt(args[1]));
			Messages.sendMessage(player, Messages.trprefix + "&7 Arena &6" + args[0] + "&7 RegenerationDelay set to &6" + args[1] + "&7 ticks");
			
		} else {
			Messages.sendMessage(player, Messages.trprefix + Messages.arenanotexist.replace("{ARENA}", args[0]));
		}
		return true;
	}

	@Override
	public int getMinArgsLength() {
		return 2;
	}

}
