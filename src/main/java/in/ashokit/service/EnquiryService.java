package in.ashokit.service;

import java.util.List;

import in.ashokit.dto.DashboardResponseDTO;
import in.ashokit.dto.EnqFilterDTO;
import in.ashokit.dto.EnquiryDTO;

public interface EnquiryService {

	public DashboardResponseDTO getDashboardInfo(Integer counsellorId);

	public boolean addEnquiry(EnquiryDTO enqDTO, Integer counsellorId);

	public List<EnquiryDTO> getEnquiries(Integer counsellorId);

	public List<EnquiryDTO> getEnquiries(EnqFilterDTO filterDTO, Integer counsellorId);

	public EnquiryDTO getEnquiryById(Integer enqId);
}
