package centauri.academy.cerepro.rest.request;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import centauri.academy.cerepro.rest.request.candidate.RequestUpdateCustom;

/**
 * @author m.franco
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RequestUpdateItConsultantCustom extends RequestUpdateCustom {


	public RequestUpdateItConsultantCustom() {

	}

//	public RequestUpdateItConsultantCustom(Long id, Long userId, String domicileCity, String domicileStreetName,
//			String domicileHouseNumber, String studyQualification, Boolean graduate, Boolean highGraduate,
//			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
//			String lastname, Date dateOfBirth, String note, String imgpath,  String oldImg, String oldCV, MultipartFile[] files, Long candidateStatesId) {
//		super(id, userId, domicileCity, domicileStreetName, domicileHouseNumber, studyQualification, graduate, highGraduate,
//				stillHighStudy, mobile, cvExternalPath, email, firstname, lastname, dateOfBirth, note,  imgpath, oldImg, oldCV, files, candidateStatesId);
//	}

}
