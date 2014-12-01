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

   /**
    * Singleton
    */
   private SQLWrapper() {
   }

   public static SQLWrapper getInstance(Logger log) {
      _instance._log = log;
      return _instance;
   }

   public static SQLWrapper getInstance() {
      return _instance;
   }

   /**
    * Connect to Database
    */
   public void Connect() {
      try {

         Class.forName("org.sqlite.JDBC");
         _connection = DriverManager.getConnection("jdbc:sqlite:./plugins/SocialBuild/database.db");
         _state = _connection.createStatement();

      } catch (SQLException e) {
         _log.info("SQL CONNECTION ERROR");
         _log.info(e.getMessage());
      } catch (ClassNotFoundException e) {
         _log.info("CLASS NOT FOUND ERROR");
         _log.info(e.getMessage());
      }

   }

   /**
    * Create Table
    */
   public void isTable() {
      try {
         _state.executeUpdate("CREATE TABLE  ranking ( id INTEGER PRIMARY KEY AUTOINCREMENT, playername text, count int);");
         _state.executeUpdate("CREATE TABLE  player ( id INTEGER PRIMARY KEY AUTOINCREMENT, playername text, signid int);");
         _state.executeUpdate("CREATE TABLE  sign ( id INTEGER PRIMARY KEY AUTOINCREMENT, owner text, x int, y int, z int);");
      } catch (SQLException e) {
         _log.info("table are recognized");
      }
   }

   /**
    * Insert Player
    * @param playername
    * @param signid
    */
   public void insertPlayer(String playername, int signid) {
      try {
         _state.execute("INSERT INTO player(id,playername,signid) VALUES (null,\"" + playername + "\", " + signid + ");");

      } catch (SQLException e) {
         _log.info("INSERT PLAYER ERROR");
         _log.info(e.getMessage());
      }
   }

   /**
    * Insert Sign
    * @param owner
    * @param x
    * @param y
    * @param z
    */
   public void insertSign(String owner, int x, int y, int z) {
      try {
         _state.execute("INSERT INTO sign(id, owner, x, y, z) VALUES (null, \"" + owner + "\"," + x + "," + y + "," + z + ");");
      } catch (SQLException e) {
         _log.info("INSERT SIGN ERROR");
         _log.info(e.getMessage());
      }
   }

   /**
    * Insert Ranking
    * @param playername
    * @param count
    */
   public void insertRanking(String playername, int count) {
      try {
         _state.execute("INSERT INTO ranking(id, playername, count) VALUES (null, \"" + playername + "\"," + count + ");");
      } catch (SQLException e) {
         _log.info("INSERT RANK ERROR");
         _log.info(e.getMessage());
      }
   }

   /**
    * Update Ranking
    * @param playername
    * @param count
    */
   public void updateRanking(String playername, int count) {
      try {
         _state.execute("UPDATE ranking SET count = " + count + " WHERE playername = \"" + playername + "\" ;");
      } catch (SQLException e) {
         _log.info("UPDATE RANK ERROR");
         _log.info(e.getMessage());
      }
   }

   /**
    * Update Sign Owner
    * @param from_owner
    * @param to_owner
    */
   public void updateOwner(String from_owner, String to_owner) {
      try {
         _state.execute("UPDATE sign SET owner = \"" + to_owner + "\" WHERE owner = \"" + from_owner + "\" ;");
      } catch (SQLException e) {
         _log.info("UPDATE OWNER ERROR");
         _log.info(e.getMessage());
      }
   }

   /**
    * Delete Player
    * @param signid
    */
   public void deletePlayer(int signid) {
      try {
         _state.execute("DELETE FROM player WHERE signid =" + signid + ";");
      } catch (SQLException e) {
         _log.info("DELETE PLAYER ERROR");
         _log.info(e.getMessage());
      }
   }

   public void deletePlayer(String playername, int signid) {
      try {
         _state.execute("DELETE FROM player WHERE signid =" + signid + " AND playername = \"" + playername + "\";");
      } catch (SQLException e) {
         _log.info("DELETE PLAYER ERROR");
         _log.info(e.getMessage());
      }
   }

   /**
    * Delete Sign
    * @param x
    * @param y
    * @param z
    */
   public void deleteSign(int x, int y, int z) {
      try {
         _state.execute("DELETE FROM sign WHERE x=" + x + " AND y=" + y + " AND z=" + z + ";");
      } catch (SQLException e) {
         _log.info("DELETE SIGN ERROR");
         _log.info(e.getMessage());
      }
   }

   /**
    * Check If there is a Sign
    * @param x
    * @param y
    * @param z
    * @return
    */
   public int isSign(int x, int y, int z) {
      ResultSet rs;
      int signid = -1;
      try {
         rs = _state.executeQuery("SELECT id FROM sign WHERE " + "x=" + x + " AND y=" + y + " AND z=" + z + ";");
         if (rs.next()) {
            signid = rs.getInt(1);
         }
      } catch (SQLException e) {
         _log.info("CHECK SIGN ERROR");
         _log.info(e.getMessage());
      }
      return signid;
   }

   /**
    * Check Player Exists
    * @param playername
    * @param signid
    * @return
    */
   public boolean isPlayer(String playername, int signid) {
      ResultSet rs;
      try {
         rs = _state.executeQuery("SELECT playername FROM player WHERE signid =" + signid + " AND playername =\"" + playername + "\";");
         if (rs.next()) {
            return true;
         }
      } catch (SQLException e) {
         _log.info("CHECK PLAYER ERROR");
         _log.info(e.getMessage());
      }
      return false;
   }

   /**
    * Check Player Exists In Ranking
    * @param playername
    * @return
    */
   public boolean isRank(String playername) {
      ResultSet rs;
      try {
         rs = _state.executeQuery("SELECT playername FROM ranking WHERE playername =\"" + playername + "\";");
         if (rs.next()) {
            return true;
         }
      } catch (SQLException e) {
         _log.info("CHECK PLAYER ERROR");
         _log.info(e.getMessage());
      }
      return false;
   }

   /**
    * Sign Count
    * @param signid
    * @return
    */
   public int getSignCount(int signid) {
      ResultSet rs;
      int count = 0;
      try {
         rs = _state.executeQuery("SELECT COUNT(id) FROM player WHERE signid=" + signid + ";");
         if (rs.next()) {
            count = rs.getInt(1);
         }
      } catch (SQLException e) {
         _log.info("COUNT ERROR");
         _log.info(e.getMessage());
      }
      return count;
   }

   /**
    * Update Player Count
    * @param playername
    */
   public void CaliculatePlayerCount(String playername) {
      ResultSet rs;
      int count = 0;
      try {
         rs = _state.executeQuery("SELECT COUNT(*) FROM player,sign WHERE player.signid = sign.id" + " AND sign.owner = \"" + playername + "\" ;");
         if (rs.next()) {
            count = rs.getInt(1);
         }
      } catch (SQLException e) {
         _log.info("COUNT ERROR");
         _log.info(e.getMessage());
      }

      if (!isRank(playername)) {
         insertRanking(playername, count);
      } else {
         updateRanking(playername, count);
      }
   }

   /**
    * Player Count
    * @param playername
    * @return
    */
   public int getPlayerCount(String playername) {
      ResultSet rs;
      int count = 0;
      try {
         rs = _state.executeQuery("SELECT count FROM ranking WHERE playername = \"" + playername + "\" ;");
         if (rs.next()) {
            count = rs.getInt(1);
         }
      } catch (SQLException e) {
         _log.info("COUNT ERROR");
         _log.info(e.getMessage());
         return 0;
      }
      return count;
   }

   /**
    * Owner
    * @param signid
    * @return
    */
   public String getOwner(int signid) {

      ResultSet rs;
      try {

         rs = _state.executeQuery("SELECT owner FROM sign WHERE id = "
               + signid + " ;");
         if (rs.next()) {
            return rs.getString(1);
         }

      } catch (SQLException e) {
         _log.info("COUNT ERROR");
         _log.info(e.getMessage());
         return null;
      }

      return null;
   }

   /**
    * Ranking List
    * @return
    */
   public String[] getRanking() {
      String ranking[] = new String[12];
      ResultSet rs;
      ranking[0] = ChatColor.BLUE + "=======GOOD RANKING=======";
      try {
         rs = _state.executeQuery("SELECT playername,count FROM ranking ORDER BY count DESC LIMIT 10");

         for (int i = 0; i < 10; i++) {
            if (rs.next()) {
               ranking[i + 1] = ChatColor.GRAY + "-" + (i + 1) + "- " + rs.getString(1) + ":" + rs.getInt(2);
            } else {
               ranking[i + 1] = ChatColor.BLUE + "==========================";
               break;
            }
         }
      } catch (SQLException e) {
         _log.info("GET RANKING ERROR");
         _log.info(e.getMessage());
      }
      ranking[11] = ChatColor.BLUE + "==========================";
      return ranking;
   }

   /**
    * Close Database
    */
   public void close() {
      try {
         _connection.close();
      } catch (SQLException e) {
         _log.info("ERROR");
         _log.info(e.getMessage());
      }
   }

   private static SQLWrapper _instance = new SQLWrapper();
   private Logger _log;
   private Connection _connection;
   private Statement _state;
}
