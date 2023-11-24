package franco.maurizio.hr.desk.com.unit.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import franco.maurizio.hr.desk.com.CeReProBackendApplication;
import franco.maurizio.hr.desk.com.backend.CandidateStatesController;
import franco.maurizio.hr.desk.com.backend.RoleController;
import franco.maurizio.hr.desk.com.persistence.entity.CandidateStates;
import franco.maurizio.hr.desk.com.persistence.entity.CeReProAbstractEntity;
import franco.maurizio.hr.desk.com.persistence.entity.Role;
import franco.maurizio.hr.desk.com.persistence.repository.CandidateStatesRepository;
import franco.maurizio.hr.desk.com.persistence.repository.RoleRepository;


/**
 * 
 * @author Sebastiano Varchetta
 * @author maurizio.franco@ymail.com
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CandidateStatesControllerTest {
	
	public static final Logger logger = LoggerFactory.getLogger(CoursePageControllerTest.class);

	@Spy
	private CandidateStatesController candidateStatesController;
	@Mock
	private CandidateStatesRepository candidateStatesRepository;
	
	@Spy
	private RoleController roleController;
	@Mock
	private RoleRepository roleRepository;
	
	@BeforeEach
	public void setup() {
		candidateStatesController = new CandidateStatesController();
		ReflectionTestUtils.setField(candidateStatesController, "candidateStatesRepository", candidateStatesRepository);
	}
	
	@Test
	public void testListAllCandidateStates() {
		logger.info("testListAllCandidateStates()  ---------------------- START");
		List<CandidateStates> candidateStatesList = new ArrayList<CandidateStates>();
		candidateStatesList.add(new CandidateStates());
		when(this.candidateStatesRepository.findAll()).thenReturn(candidateStatesList);
		ResponseEntity<List<CandidateStates>> responseEntity = this.candidateStatesController.listAllCandidatesStates();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, responseEntity.getBody().size());
		logger.info("testListAllCandidateStates()  ---------------------- END");
	}
	
	
	@Test
    public void testInsertCandidateStates() {
		logger.info("testInsertCandidateStates()  ---------------------- START");
		
		Role testRole =new Role();
		CandidateStates testCandStat=new CandidateStates();
		
		testRole.setId(1L);
		testRole.setDescription("a description");
		testRole.setLabel("a label");
		testRole.setLevel(1);
		
		testCandStat.setId(10L);
		testCandStat.setRoleId(1L);
		testCandStat.setStatusCode(1);
		testCandStat.setStatusLabel("a status label");
		testCandStat.setStatusDescription("a status description");
		testCandStat.setStatusColor("#FF0000");
		
		Optional<Role> role = Optional.of(testRole);
		Optional<CandidateStates> candStat = Optional.of(testCandStat);
		
//		when(sqRepository.findBySurveyIdAndQuestionId(4L, 40L)).thenReturn(null);
//		when(sRepository.findById(4L)).thenReturn(survey);
		when(roleRepository.findById(10L)).thenReturn(role);
		when(candidateStatesRepository.findById(10L)).thenReturn(null);
		when(this.candidateStatesRepository.save(testCandStat)).thenReturn(testCandStat);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateStatesController.createCandidateStates(testCandStat);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(10, ((CandidateStates)responseEntity.getBody()).getId().longValue());
		assertEquals(1, ((CandidateStates)responseEntity.getBody()).getRoleId().longValue());
		assertEquals(1, ((CandidateStates)responseEntity.getBody()).getStatusCode());
		assertEquals("a status label", ((CandidateStates)responseEntity.getBody()).getStatusLabel());
		assertEquals("a status description", ((CandidateStates)responseEntity.getBody()).getStatusDescription());
		assertEquals("#FF0000", ((CandidateStates)responseEntity.getBody()).getStatusColor());
		
		logger.info("testInsertCandidateStates()  ---------------------- END");
		
	}
	
	@Test
	public void testUpdateCandidateStates() {
		logger.info("testUpdateCandidateStates()  ---------------------- START");
		CandidateStates testCandStat = new CandidateStates ();
		
		Optional<CandidateStates> currOpt = Optional.of(testCandStat) ;
		when(this.candidateStatesRepository.findById(12L)).thenReturn(currOpt);
		testCandStat.setRoleId(4L);
		testCandStat.setStatusCode(2);
		testCandStat.setStatusLabel("a new status label");
		testCandStat.setStatusDescription("a new status description");
		testCandStat.setStatusColor("#00FF00");

		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateStatesController.updateCandidateStates(12L, testCandStat);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(4, ((CandidateStates)responseEntity.getBody()).getRoleId().longValue());
		assertEquals(2, ((CandidateStates)responseEntity.getBody()).getStatusCode());
		assertEquals("a new status label", ((CandidateStates)responseEntity.getBody()).getStatusLabel());
		assertEquals("a new status description", ((CandidateStates)responseEntity.getBody()).getStatusDescription());
		assertEquals("#00FF00", ((CandidateStates)responseEntity.getBody()).getStatusColor());
		

		logger.info("testUpdateCandidateStates()  ---------------------- END");
	}
	
	@Test
	public void testGetCandidateStatesById() {
		
		logger.info("testGetCandidateStatesById()  ---------------------- START");
		CandidateStates candStat = new CandidateStates();
		candStat.setId(10L);
		candStat.setRoleId(3L);
		candStat.setStatusCode(2);
		candStat.setStatusLabel("a status label");
		candStat.setStatusDescription("a status description");
		candStat.setStatusColor("#00FF00");

		Optional<CandidateStates> currOpt = Optional.of(candStat);
		when(this.candidateStatesRepository.findById(10L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateStatesController.getCandidateStatesById(10L);
		
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3, ((CandidateStates)responseEntity.getBody()).getRoleId().longValue());
		assertEquals(2, ((CandidateStates)responseEntity.getBody()).getStatusCode());
		assertEquals("a status label", ((CandidateStates)responseEntity.getBody()).getStatusLabel());
		assertEquals("a status description", ((CandidateStates)responseEntity.getBody()).getStatusDescription());
		assertEquals("#00FF00", ((CandidateStates)responseEntity.getBody()).getStatusColor());
		
		logger.info("testGetCandidateStatesById()  ---------------------- END");
	}
	
	@Test
	public void testDeleteCandidateStates() {
		logger.info("testDeleteCandidateStates()  ---------------------- START");
		CandidateStates testCandStat = new CandidateStates();
		
		Optional<CandidateStates> currOpt = Optional.of(testCandStat) ;
		when(this.candidateStatesRepository.findById(13L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateStatesController.deleteCandidateStates(13L);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		
		logger.info("testDeleteCandidateStates()  ---------------------- END");
	}
	
	
	
	@AfterEach
	public void teardown() {
		candidateStatesController = null;
	}
	

}
