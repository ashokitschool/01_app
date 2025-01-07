package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entitiy.CounsellorEntity;

public interface CounsellorRepo extends JpaRepository<CounsellorEntity, Integer> {

	public CounsellorEntity findByEmailAndPwd(String email, String pwd);

	public CounsellorEntity findByEmail(String email);

}
