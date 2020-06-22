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

package tntrun.arena.structure;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import tntrun.arena.Arena;
import tntrun.utils.Utils;

public class StructureManager {

	private Arena arena;
	private GameZone gamezone;

	public StructureManager(Arena arena) {
		this.arena = arena;
		gamezone = new GameZone(arena);
	}

	private String world;
	private Vector p1 = null;
	private Vector p2 = null;
	private int gameleveldestroydelay = 8;
	private LoseLevel loselevel = new LoseLevel();
	private Vector spectatorspawn = null;
	private Vector spawnpoint = null;
	private int minPlayers = 2;
	private int maxPlayers = 15;
	private double votesPercent = 0.75;
	private int timelimit = 300;
	private int countdown = 10;
	private Rewards rewards = new Rewards();
	private TeleportDestination teleportDest = TeleportDestination.PREVIOUS;
	private DamageEnabled damageEnabled = DamageEnabled.NO;
	private boolean punchDamage = true;
	private boolean kitsEnabled = false;
	private boolean testmode = false;
	private int regenerationdelay = 60;
	private String currency;
	private double fee = 0;
	private boolean finished = false;

	public String getWorldName() {
		return world;
	}

	public World getWorld() {
		return Bukkit.getWorld(world);
	}

	public Vector getP1() {
		return p1;
	}


	public Vector getP2() {
		return p2;
	}

	public GameZone getGameZone() {
		return gamezone;
	}

	public int getGameLevelDestroyDelay() {
		return gameleveldestroydelay;
	}

	public LoseLevel getLoseLevel() {
		return loselevel;
	}

	public Vector getSpectatorSpawnVector() {
		return spectatorspawn;
	}

	public Location getSpectatorSpawn() {
		if (spectatorspawn != null) {
			return new Location(getWorld(), spectatorspawn.getX(), spectatorspawn.getY(), spectatorspawn.getZ());
		}
		return null;
	}

	public Vector getSpawnPointVector() {
		return spawnpoint;
	}

	public Location getSpawnPoint() {
		return new Location(getWorld(), spawnpoint.getX(), spawnpoint.getY(), spawnpoint.getZ());
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public double getVotePercent() {
		return votesPercent;
	}

	public int getTimeLimit() {
		return timelimit;
	}

	public int getCountdown() {
		return countdown;
	}

	public Rewards getRewards() {
		return rewards;
	}

	public TeleportDestination getTeleportDestination() {
		return teleportDest;
	}

	public static enum TeleportDestination {
		PREVIOUS, LOBBY;
	}

	public DamageEnabled getDamageEnabled() {
		return damageEnabled;
	}

	public static enum DamageEnabled {
		YES, ZERO, NO
	}

	public boolean isKitsEnabled() {
		return kitsEnabled;
	}

	public boolean isPunchDamage() {
		return punchDamage;
	}

	public boolean isTestMode() {
		return testmode;
	}

	public int getRegenerationDelay() {
		return regenerationdelay;
	}

	public double getFee() {
		return fee;
	}

	public Material getCurrency() {
		return Material.getMaterial(currency);
	}

	public boolean hasFee() {
		return fee > 0;
	}

	public boolean isCurrencyEnabled() {
		return Material.getMaterial(currency) != null && !Utils.isAir(Material.getMaterial(currency));
	}

	public String getArenaCost(Arena arena) {
		if (!isCurrencyEnabled()) {
			return String.valueOf(fee);
		}
		return new StringBuilder().append((int) fee).append(" x ").append(currency).toString();
	}

	public boolean isInArenaBounds(Location loc) {
		if (loc.toVector().isInAABB(getP1(), getP2())) {
			return true;
		}
		return false;
	}

	public boolean isArenaBoundsSet() {
		if (getP1() == null || getP2() == null || world == null) {
			return false;
		}
		return true;
	}

	public boolean isArenaConfigured() {
		return isArenaConfiguredString().equals("yes");
	}

	public String isArenaConfiguredString() {
		if (getP1() == null || getP2() == null || world == null) {
			return "Arena bounds not set";
		}
		if (!loselevel.isConfigured()) {
			return "Arena loselevel not set";
		}
		if (spawnpoint == null) {
			return "Arena spawnpoint not set";
		}
		return "yes";
	}

	public boolean isArenaFinished() {
		return finished;
	}

	public void setArenaFinished(boolean finished) {
		this.finished = finished;
	}

	public void setArenaPoints(Location loc1, Location loc2) {
		world = loc1.getWorld().getName();
		p1 = loc1.toVector();
		p2 = loc2.toVector();
	}

	public void setGameLevelDestroyDelay(int delay) {
		gameleveldestroydelay = delay;
	}

	public boolean setLooseLevel(Location loc1, Location loc2) {
		if (isInArenaBounds(loc1) && isInArenaBounds(loc2)) {
			loselevel.setLooseLocation(loc1, loc2);
			return true;
		}
		return false;
	}

	public boolean setSpawnPoint(Location loc) {
		if (isInArenaBounds(loc)) {
			spawnpoint = loc.toVector();
			return true;
		}
		return false;
	}

	public boolean setSpectatorsSpawn(Location loc) {
		if (isInArenaBounds(loc)) {
			spectatorspawn = loc.toVector();
			return true;
		}
		return false;
	}

	public void removeSpectatorsSpawn() {
		spectatorspawn = null;
	}

	public void setMaxPlayers(int maxplayers) {
		maxPlayers = maxplayers;
	}

	public void setMinPlayers(int minplayers) {
		minPlayers = minplayers;
	}

	public void setVotePercent(double votepercent) {
		votesPercent = votepercent;
	}

	public void setTimeLimit(int timelimit) {
		this.timelimit = timelimit;
	}

	public void setCountdown(int countdown) {
		this.countdown = countdown;
	}

	public void setTeleportDestination(TeleportDestination teleportDest) {
		this.teleportDest = teleportDest;
	}

	public void setDamageEnabled(DamageEnabled damageEnabled) {
		this.damageEnabled = damageEnabled;
	}
	
	public void enableKits(boolean kitsEnabled) {
		this.kitsEnabled = kitsEnabled;
	}

	public void setRegenerationDelay(int regendelay) {
		this.regenerationdelay = regendelay;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public void setCurrency(Material currency) {
		if (Utils.isAir(currency)) {
			this.currency = null;
			return;
		}
		this.currency = currency.toString();
	}

	public void saveToConfig() {
		FileConfiguration config = new YamlConfiguration();
		try {
			config.set("world", world);
			config.set("p1", p1);
			config.set("p2", p2);
		} catch (Exception e) {
		}
		config.set("gameleveldestroydelay", gameleveldestroydelay);
		try {
			loselevel.saveToConfig(config);
		} catch (Exception e) {
		}
		try {
			config.set("spawnpoint", spawnpoint);
		} catch (Exception e) {
		}
		try {
			config.set("spectatorspawn", spectatorspawn);
		} catch (Exception e) {
		}
		config.set("maxPlayers", maxPlayers);
		config.set("minPlayers", minPlayers);
		config.set("votePercent", votesPercent);
		config.set("timelimit", timelimit);
		config.set("countdown", countdown);
		config.set("teleportto", teleportDest.toString());
		config.set("damageenabled", damageEnabled.toString());
		config.set("enableKits", kitsEnabled);
		config.set("punchDamage", punchDamage);
		config.set("testmode", testmode);
		config.set("regenerationdelay", regenerationdelay);
		config.set("joinfee", fee);
		config.set("currency", currency);
		config.set("finished", finished);
		rewards.saveToConfig(config);
		try {
			config.save(arena.getArenaFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFromConfig() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(arena.getArenaFile());
		world = config.getString("world", null);
		p1 = config.getVector("p1", null);
		p2 = config.getVector("p2", null);
		gameleveldestroydelay = config.getInt("gameleveldestroydelay", gameleveldestroydelay);
		loselevel.loadFromConfig(config);
		spawnpoint = config.getVector("spawnpoint", null);
		spectatorspawn = config.getVector("spectatorspawn", null);
		maxPlayers = config.getInt("maxPlayers", maxPlayers);
		minPlayers = config.getInt("minPlayers", minPlayers);
		votesPercent = config.getDouble("votePercent", votesPercent);
		timelimit = config.getInt("timelimit", timelimit);
		countdown = config.getInt("countdown", countdown);
		teleportDest = TeleportDestination.valueOf(config.getString("teleportto", TeleportDestination.PREVIOUS.toString()));
		damageEnabled = DamageEnabled.valueOf(config.getString("damageenabled", DamageEnabled.NO.toString()));
		rewards.loadFromConfig(config);
		kitsEnabled = config.getBoolean("enableKits");
		punchDamage = config.getBoolean("punchDamage", true);
		testmode = config.getBoolean("testmode");
		regenerationdelay = config.getInt("regenerationdelay", regenerationdelay);
		fee = config.getDouble("joinfee", fee);
		currency = config.getString("currency", null);
		finished = config.getBoolean("finished");
		if (!finished && arena.getStructureManager().isArenaConfigured()) {
			finished = true;
		}
	}

}
