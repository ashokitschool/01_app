package in.ashokit.entitiy;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "counsellor_tbl")
@Setter
@Getter
public class CounsellorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer counsellorId;
	private String name;
	private String email;
	private String pwd;
	private String phno;

	@OneToMany(mappedBy = "counsellor", cascade = CascadeType.ALL)
	private List<EnquiryEntity> enquiries;
}
