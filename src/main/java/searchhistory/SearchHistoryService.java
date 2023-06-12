package searchhistory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchHistoryService {
	
	private final String driver = "org.sqlite.JDBC";
	private final String url = "jdbc:sqlite:C:\\Users\\_________\\Desktop\\zero-mission1-sqlite\\near_wifi_service.db";
	
//	private final String driver = "org.mariadb.jdbc.Driver";
//	private final String url = "jdbc:mariadb://localhost:3306/wifi_db";
//	private final String dbUserId = "zero_user";
//	private final String dbUserPsw = "zero1234";
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public SearchHistoryService() {
		
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
	
	public void saveSearchHistory(double inputLatitude, double inputLongitude) {
		
        try {
            String insertSql = "insert into search_history (search_latitude, search_longitude, search_date) values (?, ?, CURRENT_TIMESTAMP);";

            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setDouble(1, inputLatitude);
            preparedStatement.setDouble(2, inputLongitude);
            
            preparedStatement.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }

	}

	public List<SearchHistory> getSearchHistoryList() {
		
		List<SearchHistory> historyList = new ArrayList<>();
		
        try {
            String selectSql = "select search_id, search_latitude, search_longitude, search_date \n"
            				 + "from search_history \n"
            				 + "order by search_id desc;";

            preparedStatement = connection.prepareStatement(selectSql);

            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
            	
            	SearchHistory searchHistory = new SearchHistory();
            	
            	searchHistory.setSearchID( resultSet.getString("search_id") );
            	searchHistory.setSearchLatitude( resultSet.getString("search_latitude") );
            	searchHistory.setSearchLongitude( resultSet.getString("search_longitude") );
            	searchHistory.setSearchDate( resultSet.getString("search_date") );
            	
            	historyList.add(searchHistory);

            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
        
        return historyList;
	}
	
	public boolean deleteSearchHistory(int searchID) {
		
		boolean check = false;
		
        try {
            String deleteSql = "delete \n"
            				 + "from search_history \n"
            				 + "where search_id = ?;";

            preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setInt(1, searchID);
            
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
