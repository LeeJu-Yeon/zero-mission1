package bookmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkService {
	
	private final String driver = "org.sqlite.JDBC";
	private final String url = "jdbc:sqlite:C:\\Users\\_________\\Desktop\\zero-mission1-sqlite\\near_wifi_service.db";
	
//	private final String driver = "org.mariadb.jdbc.Driver";
//	private final String url = "jdbc:mariadb://localhost:3306/wifi_db";
//	private final String dbUserId = "zero_user";
//	private final String dbUserPsw = "zero1234";
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public BookmarkService() {
		
		try {
        	Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		try {
			connection = DriverManager.getConnection(url);
//			connection = DriverManager.getConnection(url, dbUserId, dbUserPsw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void closeAll() {
		
		try {
            if ( resultSet != null && !resultSet.isClosed() ) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if ( connection != null && !connection.isClosed() ) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}
	
	public List<Bookmark> getBookmarkList() {
		
		List<Bookmark> bookmarkList = new ArrayList<>();

        try {
            String selectSql = "select b.bookmark_id, b.group_id, bg.group_name, b.wifi_id, w.wifi_name, b.add_date \n"
            				 + "from bookmark b \n"
            				 + "	join bookmark_group bg on b.group_id = bg.group_id \n"
            				 + "	join wifi w on b.wifi_id = w.wifi_id;";

            preparedStatement = connection.prepareStatement(selectSql);

            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
            	
            	Bookmark bookmark = new Bookmark();
            	
            	bookmark.setBookmarkID( resultSet.getString("bookmark_id") );
            	bookmark.setGroupID( resultSet.getString("group_id") );
            	bookmark.setGroupName( resultSet.getString("group_name") );
            	bookmark.setWifiID( resultSet.getString("wifi_id") );
            	bookmark.setWifiName( resultSet.getString("wifi_name") );
            	bookmark.setAddDate( resultSet.getString("add_date") );
            	
            	bookmarkList.add(bookmark);

            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
		
		return bookmarkList;
	}
	
	public boolean addBookmark(int groupID, String wifiID) {
		
		boolean check = false;

        try {
            String insertSql = "insert into bookmark (group_id, wifi_id, add_date) values (?, ?, CURRENT_TIMESTAMP);";

            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1, groupID);
            preparedStatement.setString(2, wifiID);
            
            int affectedRows = preparedStatement.executeUpdate();

            check = ( affectedRows > 0 ) ? true : false;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
		
		return check;
	}
	
	public boolean deleteBookmark(int bookmarkID) {
		
		boolean check = false;

        try {
            String deleteSql = "delete \n"
            				 + "from bookmark \n"
            				 + "where bookmark_id = ?;";

            preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setInt(1, bookmarkID);
            
            int affectedRows = preparedStatement.executeUpdate();

            check = ( affectedRows > 0 ) ? true : false;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
        
        return check;
	}

}
