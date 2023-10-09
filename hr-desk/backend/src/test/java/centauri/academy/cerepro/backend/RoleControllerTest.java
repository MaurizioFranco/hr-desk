package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import franco.maurizio.hr.desk.com.persistence.entity.CeReProAbstractEntity;
import franco.maurizio.hr.desk.com.persistence.entity.Role;
import franco.maurizio.hr.desk.com.persistence.repository.RoleRepository;
import centauri.academy.cerepro.service.RoleService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoleControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(RoleControllerTest.class);

	@Spy
	private RoleController roleController;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private RoleService roleService;

	@Before
	public void setup() {
		roleController = new RoleController();
		ReflectionTestUtils.setField(roleController, "roleRepository", roleRepository);
		ReflectionTestUtils.setField(roleController, "roleService", roleService);
	}

	@Test
	public void testListAllRoles() {
		logger.info("testListAllRoles - START");
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(new Role());
		when(this.roleService.getAll()).thenReturn(roleList);
		ResponseEntity<List<Role>> responseEntity = this.roleController.getRoles();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
		logger.info("testListAllRoles - END");
	}

	@Test
	public void testGetRoleById() {
		Role testRole = new Role (100L, "test", "tester", 100) ;
		Optional<Role> currOpt = Optional.of(testRole) ;
		when(this.roleRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.roleController.getRoleById(100L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(100, ((Role)responseEntity.getBody()).getLevel());
		Assert.assertEquals("tester", ((Role)responseEntity.getBody()).getDescription());
		Assert.assertEquals("test", ((Role)responseEntity.getBody()).getLabel());
	}
	
	@Test
	public void testInsertRoleSuccessfully() {
		Role testRole = new Role (100L, "test", "tester", 100) ;
		when(this.roleService.insert(testRole)).thenReturn(testRole);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.roleController.createRole(testRole);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals(100, ((Role)responseEntity.getBody()).getLevel());
		Assert.assertEquals("tester", ((Role)responseEntity.getBody()).getDescription());
		Assert.assertEquals("test", ((Role)responseEntity.getBody()).getLabel());
	}
	
	@Test
	public void testDeleteRoleSuccessfully() {
		Role testRole = new Role (100L, "test", "tester", 100) ;
		Optional<Role> currOpt = Optional.of(testRole) ;
		when(this.roleRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.roleController.deleteRole(100L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdateRoleSuccessfully() {
		Role testRole = new Role (100L, "test", "tester", 100) ;
		Optional<Role> currOpt = Optional.of(testRole) ;
		when(this.roleRepository.findById(100L)).thenReturn(currOpt);
		testRole.setDescription("testerUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.roleController.updateRole(100L, testRole);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("testerUPDATED", ((Role)responseEntity.getBody()).getDescription());
	}
	
	@After
	public void teardown() {
		roleController = null;
	}

}
