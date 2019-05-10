package edu.stevens.cs548.clinic.test;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.ProviderSpecType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());

	/**
	 * Default constructor.
	 */
	public InitBean() {
	}
	
	// Do NOT use this
	//@PersistenceContext(unitName="ClinicDomain")
	//EntityManager em;
	
	@Inject
	private IPatientServiceLocal patientService;
	
	@Inject
	private IProviderServiceLocal providerService;

	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Xiaohan Chang");

//		try {

			//IPatientDAO patientDAO = new PatientDAO(em);
//			IProviderDAO providerDAO = new ProviderDAO(em);
//			ITreatmentDAO treatmentDAO = new TreatmentDAO(em);
//
//			PatientDtoFactory patientFactory = new PatientFactory();
//			ProviderFactory providerFactory = new ProviderFactory();
//			TreatmentFactory treatmentFactory = new TreatmentFactory();
			
//			PatientDtoFactory patientFactory = new PatientDtoFactory();
//			ProviderDtoFactory providerFactory = new ProviderDtoFactory();
//			TreatmentDtoFactory treatmentFactory = new TreatmentDtoFactory();
			
			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * If we ensure that deletion of patients cascades deletes of treatments,
			 * then we only need to delete patients.
			 */
			
//			patientDAO.deletePatients();
			
//			Patient john = patientFactory.createPatient(12345678L, "John Doe");
//			patientDAO.addPatient(john);
			
//			PatientDto john = patientFactory.createPatientDto();
//			john.setPatientId(11160L);
//			john.setName("Lyla Murfin");
//			
//			long patientId = patientService.addPatient(john);
//			
//			logger.info("==================== Added patient "+john.getName()+" with id "+john.getId());
//			
//			// TODO add more testing, including treatments and providers
//			
//			ProviderDto kevin = providerFactory.createProviderDto();
//			kevin.setName("Jeff B");
//			kevin.setNpi(11178L);
//			kevin.setProviderSpec(ProviderSpecType.INTERNAL);
//			
//			long providerId = providerService.addProvider(kevin);
//			
//			logger.info("======================= Added provider "+kevin.getName()+" with id "+john.getId());
//			
//			TreatmentDto drugTreatment = treatmentFactory.createDrugTreatmentDto();
//			drugTreatment.setDiagnosis("pain");
//			
//			DrugTreatmentType dt = new DrugTreatmentType();
//			dt.setDosage(1.0f);
//			dt.setDrug("murfin");
//			drugTreatment.setDrugTreatment(dt);
//			drugTreatment.setPatient(patientId);
//			drugTreatment.setProvider(providerId);
//			
//			
//			
//			
//			long treatmentId = providerService.addTreatment(drugTreatment);
//			logger.info("======================= Added treatment Id " + treatmentId);
			
			
			
//		} catch (PatientServiceExn e) {
//
//			// logger.log(Level.SEVERE, "Failed to add patient record.", e);
//			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
//			ex.initCause(e);
//			throw ex;
//			
//		} catch (ProviderServiceExn e) {
//			// TODO Auto-generated catch block
//			IllegalStateException ex = new IllegalStateException("Failed to add provider record.");
//			ex.initCause(e);
//			throw ex;
//		} 
			
	}

}
