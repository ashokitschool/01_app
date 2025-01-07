package in.ashokit.dto;

import lombok.Data;

@Data
public class DashboardResponseDTO {
	
	private Integer totalEnqCnt;
	private Integer openEnqCnt;
	private Integer enrolledEnqCnt;
	private Integer lostEnqCnt;

}
