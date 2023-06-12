package wifi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WifiService {
	
	private final String driver = "org.sqlite.JDBC";
	private final String url = "jdbc:sqlite:C:\\Users\\_________\\Desktop\\zero-mission1-sqlite\\near_wifi_service.db";
	
//	private final String driver = "org.mariadb.jdbc.Driver";
//	private final String url = "jdbc:mariadb://localhost:3306/wifi_db";
//	private final String dbUserId = "zero_user";
//	private final String dbUserPsw = "zero1234";
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public WifiService() {
		
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
	
	public int getWifiCount() {
		
		int wifiCount = 0;

        try {
            String selectSql = "select count(*) as count from wifi;";

            preparedStatement = connection.prepareStatement(selectSql);

            resultSet = preparedStatement.executeQuery();

            if ( resultSet.next() ) {
            	wifiCount = resultSet.getInt("count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
		
		return wifiCount;
	}
	
	public List<WiFi> getCloseWifiList(double inputLatitude, double inputLongitude) {
		
		List<WiFi> closeWifiList = new ArrayList<>();
		
        try {
            String selectSql = "select \n"
            				+ "	( 6371 * acos(	cos(radians(?)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?)) \n"
            				+ "				  + sin(radians(?)) * sin(radians(latitude))	 ) ) as distance, \n"
            				+ "	wifi_id, gu, wifi_name, road_address, detail_address, install_floor, install_type, install_agency, service_type, network_type,"
            				+ " install_year, indoor_outdoor, connection_detail, latitude, longitude, work_date \n"
            				+ "from wifi \n"
            				+ "order by distance asc \n"
            				+ "limit 20;";

            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setDouble(1, inputLatitude);
            preparedStatement.setDouble(2, inputLongitude);
            preparedStatement.setDouble(3, inputLatitude);

            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
            	
            	WiFi wifi = new WiFi();
            	
            	wifi.setDistance( resultSet.getString("distance") );
            	wifi.setWifiID( resultSet.getString("wifi_id") );
                wifi.setGu( resultSet.getString("gu") );
                wifi.setWifiName( resultSet.getString("wifi_name") );
                wifi.setRoadAddress( resultSet.getString("road_address") );
                wifi.setDetailAddress( resultSet.getString("detail_address") );
                wifi.setInstallFloor( resultSet.getString("install_floor") );
                wifi.setInstallType( resultSet.getString("install_type") );
                wifi.setInstallAgency( resultSet.getString("install_agency") );
                wifi.setServiceType( resultSet.getString("service_type") );
                wifi.setNetworkType( resultSet.getString("network_type") );
                wifi.setInstallYear( resultSet.getString("install_year") );
                wifi.setIndoorOutdoor( resultSet.getString("indoor_outdoor") );
                wifi.setConnectionDetail( resultSet.getString("connection_detail") );
                wifi.setLatitude( resultSet.getString("latitude") );
                wifi.setLongitude( resultSet.getString("longitude") );
                wifi.setWorkDate( resultSet.getString("work_date") );
                
                closeWifiList.add(wifi);

            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
		
        return closeWifiList;
	}

	public WiFi getDetailWifi(String wifiID) {
		
		WiFi wifi = new WiFi();

        try {
            String selectSql = "select"
            				+ " wifi_id, gu, wifi_name, road_address, detail_address, install_floor, install_type, install_agency, service_type, network_type, \n"
            				+ "	install_year, indoor_outdoor, connection_detail, latitude, longitude, work_date \n"
            				+ "from wifi \n"
            				+ "where wifi_id = ?;";

            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setString(1, wifiID);

            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
            	
            	wifi.setWifiID( resultSet.getString("wifi_id") );
                wifi.setGu( resultSet.getString("gu") );
                wifi.setWifiName( resultSet.getString("wifi_name") );
                wifi.setRoadAddress( resultSet.getString("road_address") );
                wifi.setDetailAddress( resultSet.getString("detail_address") );
                wifi.setInstallFloor( resultSet.getString("install_floor") );
                wifi.setInstallType( resultSet.getString("install_type") );
                wifi.setInstallAgency( resultSet.getString("install_agency") );
                wifi.setServiceType( resultSet.getString("service_type") );
                wifi.setNetworkType( resultSet.getString("network_type") );
                wifi.setInstallYear( resultSet.getString("install_year") );
                wifi.setIndoorOutdoor( resultSet.getString("indoor_outdoor") );
                wifi.setConnectionDetail( resultSet.getString("connection_detail") );
                wifi.setLatitude( resultSet.getString("latitude") );
                wifi.setLongitude( resultSet.getString("longitude") );
                wifi.setWorkDate( resultSet.getString("work_date") );

            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }
		
		return wifi;
	}

	public void deleteTotalWifi() {
		
        try {
            String deleteSql = "delete \n"
            				 + "from wifi \n";

            preparedStatement = connection.prepareStatement(deleteSql);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	closeAll();
        }

	}
	
}
