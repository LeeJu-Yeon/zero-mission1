package api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import wifi.WiFi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WifiListParser {
	
	private List<WiFi> wifiList;
	
	@SuppressWarnings("unchecked")
    @JsonProperty("TbPublicWifiInfo")
    public void setWifiList( Map<String,Object> TbPublicWifiInfo ) {
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)TbPublicWifiInfo.get("row");
        
    	this.wifiList = new ArrayList<>();
        
        if ( list != null ) {
        	for ( Map<String, Object> item : list ) {
        		
        		WiFi wifi = new WiFi();
        		
        		wifi.setWifiID( (String) item.get("X_SWIFI_MGR_NO") );
                wifi.setGu( (String) item.get("X_SWIFI_WRDOFC") );
                wifi.setWifiName( (String) item.get("X_SWIFI_MAIN_NM") );
                wifi.setRoadAddress( (String) item.get("X_SWIFI_ADRES1") );
                wifi.setDetailAddress( (String) item.get("X_SWIFI_ADRES2") );
                wifi.setInstallFloor( (String) item.get("X_SWIFI_INSTL_FLOOR") );
                wifi.setInstallType( (String) item.get("X_SWIFI_INSTL_TY") );
                wifi.setInstallAgency( (String) item.get("X_SWIFI_INSTL_MBY") );
                wifi.setServiceType( (String) item.get("X_SWIFI_SVC_SE") );
                wifi.setNetworkType( (String) item.get("X_SWIFI_CMCWR") );
                wifi.setInstallYear( (String) item.get("X_SWIFI_CNSTC_YEAR") );
                wifi.setIndoorOutdoor( (String) item.get("X_SWIFI_INOUT_DOOR") );
                wifi.setConnectionDetail( (String) item.get("X_SWIFI_REMARS3") );
                wifi.setLongitude( (String) item.get("LAT") );							// 서울시 API 데이터가 위도 경도가 반대라
                wifi.setLatitude( (String) item.get("LNT") );							// 서로 바꿔서 파싱하였습니다
                wifi.setWorkDate( (String) item.get("WORK_DTTM") );
                
                wifiList.add(wifi);
                
        	}
        }
        
    }

	public List<WiFi> getWifiList() {
		return wifiList;
	}

}
