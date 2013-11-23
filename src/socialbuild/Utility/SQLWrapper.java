package socialbuild.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * SQLWrapper Class
 * 
 * @author unhappychoice
 * 
 */
public class SQLWrapper extends JavaPlugin {

	// ////////////////////////////////
	// ///////////singleton//////////////
	// ////////////////////////////////
	private SQLWrapper() {
	}

	public static SQLWrapper getInstance(Logger log) {

		instance.log = log;
		return instance;

	}

	public static SQLWrapper getInstance() {
		return instance;
	}

	// ////////////////////////////////
	// //////////Connection/////////////
	// ////////////////////////////////
	public void Connect() {
		try {

			Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:sqlite:./plugins/SocialBuild/database.db");
			state = connection.createStatement();

		} catch (SQLException e) {
			log.info("SQL CONNECTION ERROR");
			log.info(e.getMessage());
		} catch (ClassNotFoundException e) {
			log.info("CLASS NOT FOUND ERROR");
			log.info(e.getMessage());
		}

	}

	// /////////////////////////////////
	// ////////Create Table///////////////
	// /////////////////////////////////
	public void isTable() {
		try {

			state.executeUpdate("CREATE TABLE  ranking(id INTEGER PRIMARY KEY AUTOINCREMENT, playername text, count int);");
			state.executeUpdate("CREATE TABLE  player (id INTEGER PRIMARY KEY AUTOINCREMENT, playername text, signid int);");
			state.executeUpdate("CREATE TABLE  sign(id INTEGER PRIMARY KEY AUTOINCREMENT, owner text, x int, y int, z int);");

		} catch (SQLException e) {
			log.info("table are recognized");
		}
	}

	// /////////////////////////////////
	// ///////insertPlayerColumn///////////
	// /////////////////////////////////
	public void insertPlayer(String playername, int signid) {

		try {

			state.execute("INSERT INTO player(id,playername,signid) VALUES (null,\""
					+ playername + "\", " + signid + ");");

		} catch (SQLException e) {
			log.info("INSERT PLAYER ERROR");
			log.info(e.getMessage());
		}
	}

	// /////////////////////////////////
	// ///////insertSignColumn////////////
	// /////////////////////////////////
	public void insertSign(String owner, int x, int y, int z) {

		try {

			state.execute("INSERT INTO sign(id, owner, x, y, z) VALUES (null, \""
					+ owner + "\"," + x + "," + y + "," + z + ");");

		} catch (SQLException e) {
			log.info("INSERT SIGN ERROR");
			log.info(e.getMessage());
		}
	}

	// /////////////////////////////////
	// ///////insertRankingColumn/////////
	// /////////////////////////////////
	public void insertRanking(String playername, int count) {

		try {

			state.execute("INSERT INTO ranking(id, playername, count) VALUES (null, \""
					+ playername + "\"," + count + ");");

		} catch (SQLException e) {
			log.info("INSERT RANK ERROR");
			log.info(e.getMessage());
		}
	}

	// /////////////////////////////////
	// ///////updateRankingColumn/////////
	// /////////////////////////////////
	public void updateRanking(String playername, int count) {

		try {

			state.execute("UPDATE ranking SET count = " + count
					+ " WHERE playername = \"" + playername + "\" ;");

		} catch (SQLException e) {
			log.info("UPDATE RANK ERROR");
			log.info(e.getMessage());
		}
	}

	// /////////////////////////////////
	// ///////updateRankingColumn/////////
	// /////////////////////////////////
	public void updateOwner(String from_owner, String to_owner) {

		try {

			state.execute("UPDATE sign SET owner = \"" + from_owner
					+ "\" WHERE owner = \"" + to_owner + "\" ;");

		} catch (SQLException e) {
			log.info("UPDATE OWNER ERROR");
			log.info(e.getMessage());
		}
	}

	// /////////////////////////////////
	// //////DeletePlayerColumn///////////
	// /////////////////////////////////
	public void deletePlayer(int signid) {

		try {

			state.execute("DELETE FROM player WHERE signid =" + signid + ";");

		} catch (SQLException e) {
			log.info("DELETE PLAYER ERROR");
			log.info(e.getMessage());
		}

	}

	public void deletePlayer(String playername, int signid) {

		try {

			state.execute("DELETE FROM player WHERE signid =" + signid
					+ " AND playername = \"" + playername + "\";");

		} catch (SQLException e) {
			log.info("DELETE PLAYER ERROR");
			log.info(e.getMessage());
		}
	}

	// /////////////////////////////////
	// //////DeleteSignColumn///////////
	// /////////////////////////////////
	public void deleteSign(int x, int y, int z) {

		try {

			state.execute("DELETE FROM sign WHERE x=" + x + " AND y=" + y
					+ " AND z=" + z + ";");

		} catch (SQLException e) {
			log.info("DELETE SIGN ERROR");
			log.info(e.getMessage());
		}
	}

	// /////////////////////////////////
	// ////////CheckSignExists////////////
	// /////////////////////////////////
	public int isSign(int x, int y, int z) {

		ResultSet rs;
		int signid = -1;

		try {
			rs = state.executeQuery("SELECT id FROM sign WHERE " + "x=" + x
					+ " AND y=" + y + " AND z=" + z + ";");

			if (rs.next()) {
				signid = rs.getInt(1);
			}

		} catch (SQLException e) {
			log.info("CHECK SIGN ERROR");
			log.info(e.getMessage());
		}

		return signid;

	}

	// /////////////////////////////////
	// //////CheckPlayerExists////////////
	// /////////////////////////////////
	public boolean isPlayer(String playername, int signid) {

		ResultSet rs;

		try {

			rs = state
					.executeQuery("SELECT playername FROM player WHERE signid ="
							+ signid
							+ " AND playername =\""
							+ playername
							+ "\";");
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			log.info("CHECK PLAYER ERROR");
			log.info(e.getMessage());
		}

		return false;

	}

	// /////////////////////////////////
	// //////CheckPlayerExists////////////
	// /////////////////////////////////
	public boolean isRank(String playername) {

		ResultSet rs;

		try {

			rs = state
					.executeQuery("SELECT playername FROM ranking WHERE playername =\""
							+ playername + "\";");
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			log.info("CHECK PLAYER ERROR");
			log.info(e.getMessage());
		}

		return false;

	}

	// /////////////////////////////////
	// //////CaliculateCount//////////////
	// /////////////////////////////////
	public int getSignCount(int signid) {

		ResultSet rs;
		int count = 0;
		try {

			rs = state
					.executeQuery("SELECT COUNT(id) FROM player WHERE signid="
							+ signid + ";");
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			log.info("COUNT ERROR");
			log.info(e.getMessage());
		}

		return count;
	}

	// /////////////////////////////////
	// //////CaliculateCount//////////////
	// /////////////////////////////////
	public void CaliculatePlayerCount(String playername) {

		ResultSet rs;
		int count = 0;
		try {

			rs = state
					.executeQuery("SELECT COUNT(*) FROM player,sign WHERE player.signid = sign.id"
							+ " AND sign.owner = \"" + playername + "\" ;");
			if (rs.next()) {
				count = rs.getInt(1);

			}

		} catch (SQLException e) {
			log.info("COUNT ERROR");
			log.info(e.getMessage());
		}

		if (!isRank(playername)) {
			insertRanking(playername, count);
		} else {
			updateRanking(playername, count);
		}
	}

	// /////////////////////////////////
	// ////////////getCount//////////////
	// /////////////////////////////////
	public int getPlayerCount(String playername) {

		ResultSet rs;
		int count = 0;
		try {

			rs = state
					.executeQuery("SELECT count FROM ranking WHERE playername = \""
							+ playername + "\" ;");
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			log.info("COUNT ERROR");
			log.info(e.getMessage());
			return 0;
		}

		return count;
	}

	// /////////////////////////////////
	// ////////////getOwner//////////////
	// /////////////////////////////////
	public String getOwner(int signid) {

		ResultSet rs;
		try {

			rs = state.executeQuery("SELECT owner FROM sign WHERE id = "
					+ signid + " ;");
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException e) {
			log.info("COUNT ERROR");
			log.info(e.getMessage());
			return null;
		}

		return null;
	}

	// /////////////////////////////////
	// //////CloseDatabase///////////////
	// /////////////////////////////////
	public String[] getRanking() {

		String ranking[] = new String[12];
		ResultSet rs;

		ranking[0] = ChatColor.BLUE + "=======GOOD RANKING=======";
		try {
			rs = state
					.executeQuery("SELECT playername,count FROM ranking ORDER BY count DESC LIMIT 10");

			for (int i = 0; i < 10; i++) {
				if (rs.next()) {
					ranking[i + 1] = ChatColor.GRAY + "-" + (i + 1) + "- "
							+ rs.getString(1) + ":" + rs.getInt(2);
				} else {
					ranking[i + 1] = ChatColor.BLUE
							+ "==========================";
					break;
				}
			}

		} catch (SQLException e) {
			log.info("GET RANKING ERROR");
			log.info(e.getMessage());
		}
		ranking[11] = ChatColor.BLUE + "==========================";

		return ranking;
	}

	// /////////////////////////////////
	// //////CloseDatabase///////////////
	// /////////////////////////////////
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			log.info("ERROR");
			log.info(e.getMessage());
		}
	}

	private static SQLWrapper instance = new SQLWrapper();
	private Logger log;
	private Connection connection;
	private Statement state;
}
