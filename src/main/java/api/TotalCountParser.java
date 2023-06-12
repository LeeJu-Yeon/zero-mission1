package api;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalCountParser {
	
	private int totalCount;
	
	@JsonProperty("TbPublicWifiInfo")
    public void setTotalCount( Map<String,Object> TbPublicWifiInfo ) {
		
		this.totalCount = (int)TbPublicWifiInfo.get("list_total_count");
		
	}

	public int getTotalCount() {
		return totalCount;
	}
	
}
