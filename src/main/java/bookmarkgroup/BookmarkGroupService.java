package bookmarkgroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupService {
	
	private final String driver = "org.sqlite.JDBC";
	private final String url = "jdbc:sqlite:C:\\Users\\_________\\Desktop\\zero-mission1-sqlite\\near_wifi_service.db";
	
//	private final String driver = "org.mariadb.jdbc.Driver";
//	private final String url = "jdbc:mariadb://localhost:3306/wifi_db";
//	private final String dbUserId = "zero_user";
//	private final String dbUserPsw = "zero1234";
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public BookmarkGroupService() {
		
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
	
	public List<BookmarkGroup> getBookmarkGroupList() {
		
		List<BookmarkGroup> groupList = new ArrayList<>();

        try {
            String selectSql = "select group_id, group_name, sort_order, create_date, update_date \n"
            				 + "from bookmark_group \n"
            				 + "order by sort_order asc;";

            preparedStatement = connection.prepareStatement(selectSql);

            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
            	
            	BookmarkGroup bookmarkGroup = new BookmarkGroup();
            	
            	bookmarkGroup.setGroupID( resultSet.getString("group_id") );
            	bookmarkGroup.setGroupName( resultSet.getString("group_name") );
            	bookmarkGroup.setSortOrder( resultSet.getString("sort_order") );
            	bookmarkGroup.setCreateDate( resultSet.getString("create_date") );
            	bookmarkGroup.setUpdateDate( resultSet.getString("update_date") );
            	
            	groupList.add(bookmarkGroup);

            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
        
        return groupList;
	}

	public boolean addBookmarkGroup(String groupName, int sortOrder) {
		
		boolean check = false;

        try {
            String insertSql = "insert into bookmark_group (group_name, sort_order, create_date) values (?, ?, CURRENT_TIMESTAMP);";

            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, groupName);
            preparedStatement.setInt(2, sortOrder);
            
            int affectedRows = preparedStatement.executeUpdate();

            check = ( affectedRows > 0 ) ? true : false;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
        
        return check;
	}

	public boolean updateBookmarkGroup(int groupID, String groupName, int sortOrder) {
		
		boolean check = false;

        try {
            String updateSql = "update bookmark_group \n"
            				 + "set group_name = ?, \n"
            				 + "	sort_order = ?, \n"
            				 + "	update_date = CURRENT_TIMESTAMP \n"
            				 + "where group_id = ?;";

            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, groupName);
            preparedStatement.setInt(2, sortOrder);
            preparedStatement.setInt(3, groupID);
            
            int affectedRows = preparedStatement.executeUpdate();

            check = ( affectedRows > 0 ) ? true : false;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
        
        return check;
	}

	public boolean deleteBookmarkGroup(int groupID) {
		
		boolean check = false;

        try {
            String deleteSql = "delete \n"
            				 + "from bookmark_group \n"
            				 + "where group_id = ?;";

            preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setInt(1, groupID);
            
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
