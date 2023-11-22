package centauri.academy.cerepro.rest.request.candidate;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * @author m.franco
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RequestUpdateCandidateCustom extends RequestUpdateCustom {	

	public RequestUpdateCandidateCustom() {

	}

	public RequestUpdateCandidateCustom(Long id, String domicileCity, String domicileStreetName,
			String domicileHouseNumber, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String note, String imgpath,  String oldImg, String oldCV, MultipartFile[] files, 
			Long candidateStatusCode, long insertedBy, String positionCode) {
		super(id, domicileCity, domicileStreetName, domicileHouseNumber, studyQualification, graduate, highGraduate,
				stillHighStudy, mobile, cvExternalPath, email, firstname, lastname, dateOfBirth, note,imgpath, oldImg, oldCV, files, candidateStatusCode, insertedBy, positionCode);
		
	}
	
	
	
}
