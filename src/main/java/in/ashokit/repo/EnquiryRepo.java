package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entitiy.EnquiryEntity;

public interface EnquiryRepo 
    extends JpaRepository<EnquiryEntity, Integer>{

}
