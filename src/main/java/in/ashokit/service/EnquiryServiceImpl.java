package in.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.dto.DashboardResponseDTO;
import in.ashokit.dto.EnqFilterDTO;
import in.ashokit.dto.EnquiryDTO;
import in.ashokit.entitiy.CounsellorEntity;
import in.ashokit.entitiy.EnquiryEntity;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.EnquiryRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private EnquiryRepo enqRepo;
	
	@Autowired
	private CounsellorRepo counsellorRepo;
	
	@Override
	public DashboardResponseDTO getDashboardInfo(Integer counsellorId) {
		
		DashboardResponseDTO dto = new DashboardResponseDTO();
		
		List<EnquiryEntity> enqsList =
				enqRepo.findByCounsellorCounsellorId(counsellorId);
		
		/*
		int openCnt = 0;
		int enrolledCnt = 0;
		int lostCnt = 0;
		
		for(EnquiryEntity entity : enqsList) {
			if(entity.getEnqStatus().equals("OPEN")) {
				openCnt ++;
			}else if(entity.getEnqStatus().equals("ENROLLED")) {
				enrolledCnt ++;
			}else if(entity.getEnqStatus().equals("LOST")) {
				lostCnt++;
			}
		}*/

		int openCnt = enqsList.stream()
							  .filter(enq -> enq.getEnqStatus().equals("Open"))
							  .collect(Collectors.toList())
							  .size();	
		
		int enrolledCnt = enqsList.stream()
							      .filter(enq -> enq.getEnqStatus().equals("Enrolled"))
							      .collect(Collectors.toList())
							      .size();
		
		int lostCnt = enqsList.stream()
						      .filter(enq -> enq.getEnqStatus().equals("Lost"))
						      .collect(Collectors.toList())
						      .size();

		dto.setTotalEnqCnt(enqsList.size());
		dto.setOpenEnqCnt(openCnt);
		dto.setEnrolledEnqCnt(enrolledCnt);
		dto.setLostEnqCnt(lostCnt);
		
		return dto;
	}

	@Override
	public boolean addEnquiry(EnquiryDTO enqDTO, Integer counsellorId) {
		
		EnquiryEntity entity = new EnquiryEntity();
		BeanUtils.copyProperties(enqDTO, entity);
		
		// setting FK(counsellor_id) to enquiry obj
		Optional<CounsellorEntity> byId = counsellorRepo.findById(counsellorId);
		if(byId.isPresent()) {
			CounsellorEntity counsellor = byId.get();
			entity.setCounsellor(counsellor);
		}
		
		EnquiryEntity save = enqRepo.save(entity);

		return save.getEnqId()!=null;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(Integer counsellorId) {
		
		List<EnquiryDTO> enqsDtoList = new ArrayList<>();
		
		List<EnquiryEntity> enqList =
				enqRepo.findByCounsellorCounsellorId(counsellorId);
		
		for(EnquiryEntity entity : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(entity, dto);
			enqsDtoList.add(dto);
		}
		
		return enqsDtoList;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(EnqFilterDTO filterDTO, Integer counsellorId) {

		EnquiryEntity entity = new EnquiryEntity();
		
		if(filterDTO.getClassMode()!=null && 
				!filterDTO.getClassMode().equals("")) {
			entity.setClassMode(filterDTO.getClassMode());
		}
		
		if(filterDTO.getCourse()!=null 
				&& !filterDTO.getCourse().equals("")) {
			entity.setCourse(filterDTO.getCourse());
		}
		
		if(filterDTO.getEnqStatus()!=null 
				&& !filterDTO.getEnqStatus().equals("")) {
			entity.setEnqStatus(filterDTO.getEnqStatus());
		}
		
		CounsellorEntity counsellor = new CounsellorEntity();
		counsellor.setCounsellorId(counsellorId);
		entity.setCounsellor(counsellor);
		
		Example<EnquiryEntity> of = Example.of(entity);
		List<EnquiryEntity> enqList = enqRepo.findAll(of);
		
		List<EnquiryDTO> enqsDtoList = new ArrayList<>();
		for(EnquiryEntity enq : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(enq, dto);
			enqsDtoList.add(dto);
		}
		return enqsDtoList;
	}

	@Override
	public EnquiryDTO getEnquiryById(Integer enqId) {
		
		Optional<EnquiryEntity> byId = enqRepo.findById(enqId);
		
		if(byId.isPresent()) {
			EnquiryEntity enquiryEntity = byId.get();
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(enquiryEntity, dto);
			return dto;
		}
		
		return null;
	}
}








