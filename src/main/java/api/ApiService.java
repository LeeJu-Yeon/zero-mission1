package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import wifi.WiFi;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiService {
	
	public int getTotalCount() throws IOException {
		
		URL url = new URL("http://openapi.seoul.go.kr:8088/52556e4d426c6f7634344b6573476f/json/TbPublicWifiInfo/1/1/");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		System.out.println("getTotalCount Response code: " + conn.getResponseCode());
		
		BufferedReader rd;
		
		if ( 200 <= conn.getResponseCode() && conn.getResponseCode() <= 300 ) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuilder sb = new StringBuilder();
		String line;
		
		while ( (line = rd.readLine()) != null ) {
			sb.append(line);
		}
		
		rd.close();
		conn.disconnect();
		
		String jsonStr = sb.toString();
		
		TotalCountParser totalCountParser = new ObjectMapper().readValue(jsonStr, TotalCountParser.class);
		
		return totalCountParser.getTotalCount();
		
	}
	
	public List<WiFi> getWifiList(int start, int end) throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/52556e4d426c6f7634344b6573476f/json/TbPublicWifiInfo/");
		urlBuilder.append(start + "/" + end + "/");
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		System.out.println("wifiList from " + start + " to " + end + " Response code: " + conn.getResponseCode());
		
		BufferedReader rd;
		
		if ( 200 <= conn.getResponseCode() && conn.getResponseCode() <= 300 ) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuilder sb = new StringBuilder();
		String line;
		
		while ( (line = rd.readLine()) != null ) {
			sb.append(line);
		}
		
		rd.close();
		conn.disconnect();
		
		String jsonStr = sb.toString();
		
		WifiListParser wifiListParser = new ObjectMapper().readValue(jsonStr, WifiListParser.class);
		
		return wifiListParser.getWifiList();
		
	}

	public List<List<WiFi>> getTotalWifiList() throws IOException {
		
		int totalCount = getTotalCount();
		
		List<List<WiFi>> totalWifiList = new ArrayList<>();
		
		for ( int i = 0 ; i <= totalCount / 1000 ; i++ ) {
			int start = i * 1000 + 1;
			int end = (i + 1) * 1000;
			
			if ( start == totalCount / 1000 + 1 ) {
				end = totalCount;
			}
			
			List<WiFi> wifiList = getWifiList(start, end);
			totalWifiList.add(wifiList);
			
			System.out.println(i + "번째 리스트 완성");
		}
		
		return totalWifiList;
		
	}
	
	public void saveDataToDB() throws IOException {
		
		List<List<WiFi>> totalWifiList = getTotalWifiList();
		
		String url = "jdbc:sqlite:C:\\Users\\_________\\Desktop\\zero-mission1-sqlite\\near_wifi_service.db";
		
//		String url = "jdbc:mariadb://localhost:3306/wifi_db";
//		String dbUserId = "zero_user";
//		String dbUserPsw = "zero1234";
		
		try {
			Class.forName("org.sqlite.JDBC");
//			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

        try {
        	connection = DriverManager.getConnection(url);
//            connection = DriverManager.getConnection(url, dbUserId, dbUserPsw);
            
            String insertSql = "insert into wifi"
            				+ " (wifi_id, gu, wifi_name, road_address, detail_address, install_floor, install_type, install_agency, service_type, network_type,"
            				+ "  install_year, indoor_outdoor, connection_detail, latitude, longitude, work_date)"
            				+ "  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            preparedStatement = connection.prepareStatement(insertSql);
            
            for ( List<WiFi> wifiList : totalWifiList ) {
            	for ( WiFi wifi : wifiList ) {
            		
            		String wifi_id = wifi.getWifiID();
            		String gu = wifi.getGu();
            		String wifi_name = wifi.getWifiName();
            		String road_address = wifi.getRoadAddress();
            		String detail_address = wifi.getDetailAddress();
                    String install_floor = wifi.getInstallFloor();
                    String install_type = wifi.getInstallType();
                    String install_agency = wifi.getInstallAgency();
                    String service_type = wifi.getServiceType();
                    String network_type = wifi.getNetworkType();
                    int install_year = Integer.parseInt( wifi.getInstallYear() );
                    String indoor_outdoor = wifi.getIndoorOutdoor();
                    String connection_detail = wifi.getConnectionDetail();
                    BigDecimal latitude = new BigDecimal( wifi.getLatitude() );
                    BigDecimal longitude = new BigDecimal( wifi.getLongitude() );
                    String work_date = wifi.getWorkDate();   // SQLite, MariaDB 둘다 년월일시 형태로 잘 출력됨.
//                    Timestamp work_date = Timestamp.valueOf( wifi.getWorkDate() );   // 이렇게 할 경우 SQLite 에서는 년월일시 형태로 출력되지 않기에 String 으로 하였습니다.
                    
                    preparedStatement.setString(1, wifi_id);
                    preparedStatement.setString(2, gu);
                    preparedStatement.setString(3, wifi_name);
                    preparedStatement.setString(4, road_address);
                    preparedStatement.setString(5, detail_address);
                    preparedStatement.setString(6, install_floor);
                    preparedStatement.setString(7, install_type);
                    preparedStatement.setString(8, install_agency);
                    preparedStatement.setString(9, service_type);
                    preparedStatement.setString(10, network_type);
                    preparedStatement.setInt(11, install_year);
                    preparedStatement.setString(12, indoor_outdoor);
                    preparedStatement.setString(13, connection_detail);
                    preparedStatement.setBigDecimal(14, latitude);
                    preparedStatement.setBigDecimal(15, longitude);
                    preparedStatement.setString(16, work_date);
//                    preparedStatement.setTimestamp(16, work_date);
                    
                    preparedStatement.addBatch();
                    preparedStatement.clearParameters();
                    
            	}
            	preparedStatement.executeBatch();
            	preparedStatement.clearBatch();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

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
        
	}
	
}
