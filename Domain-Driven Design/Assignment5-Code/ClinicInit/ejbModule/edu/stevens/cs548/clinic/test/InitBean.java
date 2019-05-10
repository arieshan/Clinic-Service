package edu.stevens.cs548.clinic.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.Provider.ProviderType;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.RadiologyTreatment;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentDAO;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;

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
	
	// TODO inject an EM
	@PersistenceContext(unitName="ClinicDomain")
	EntityManager em;

	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Xiaohan Chang");

		try {
			IPatientDAO patientDAO = new PatientDAO(em);
			IProviderDAO providerDAO = new ProviderDAO(em);
			ITreatmentDAO treatmentDAO = new TreatmentDAO(em);

			PatientFactory patientFactory = new PatientFactory();
			ProviderFactory providerFactory = new ProviderFactory();
			TreatmentFactory treatmentFactory = new TreatmentFactory();
			
			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * If we ensure that deletion of patients cascades deletes of treatments,
			 * then we only need to delete patients.
			 */

			patientDAO.deletePatients();
			Patient john = patientFactory.createPatient(125678, "John Doe");
			//patientDAO.deletePatients();
			patientDAO.addPatient(john);
			logger.info("Added "+john.getName()+" with id "+john.getId());
			Patient retirevedJohn = patientDAO.getPatientByPatientId(john.getPatientId());
			logger.info("Retrieved Patient by ID: "+retirevedJohn.getName()+" with id "+retirevedJohn.getId());
			
			Patient kevin = patientFactory.createPatient(93794, "kevin hale");
			patientDAO.addPatient(kevin);
			
			logger.info("Added "+kevin.getName()+" with id "+kevin.getId());
			Patient retirevedKevin = patientDAO.getPatientByPatientId(kevin.getPatientId());
			logger.info("Retrieved Patient by ID: "+retirevedKevin.getName()+" with id "+retirevedKevin.getId());
			
			// TODO add more testing, including treatments and providers
			Provider p1 = providerFactory.createProvider(67464, "John Smith", ProviderType.RADIOLOGIST);
			providerDAO.addProvider(p1);
			
			logger.info("Added Provider: " + p1.getName() + " with npi "+ p1.getNpi());
			
			Provider retreivedS = providerDAO.getProvider(p1.getId());
			logger.info("Retrieved Provider by Id: "+retreivedS.getName()+" with npi "+retreivedS.getNpi()+ " with id: "+ retreivedS.getId());
			
			Provider p2 = providerFactory.createProvider(92342, "Taylor Smith", ProviderType.SURGEON);
			providerDAO.addProvider(p2);
			
			logger.info("Added Provider: " + p2.getName() + " with npi "+ p2.getNpi());
			Provider retreivedT = providerDAO.getProvider(p2.getId());
			logger.info("Retrieved Provider by Id: "+retreivedT.getName()+" with npi "+retreivedT.getNpi()+ " with id: "+ retreivedT.getId());
			
			Provider p3 = providerFactory.createProvider(13252, "Lyla Smith", ProviderType.INTERNIST);
			providerDAO.addProvider(p3);
			
			logger.info("Added Provider: " + p3.getName() + " with npi "+ p3.getNpi());
			Provider retreivedL = providerDAO.getProvider(p3.getId());
			logger.info("Retrieved Provider by Id: "+retreivedL.getName()+" with npi "+retreivedL.getNpi()+ " with id: "+ retreivedL.getId());

			
			Date date = new Date(10345346);
			
			
			//Adding Radiology Treatment 
			List<Date> radiologyDates = new ArrayList<Date>();
			for(int i =1;i<4;i++){
				radiologyDates.add(new Date(124354534 + 10 * i));
			}
			TreatmentFactory factory = new TreatmentFactory();
			Treatment radio = factory.createRadiologyTreatment("Diagnosis 00001", radiologyDates);
			Treatment surgery = factory.createSurgeryTreatment("Diagnosis 00002", new Date(233453));
			Treatment drug = factory.createDrugTreatment("diagnosis 001", "murffin", 0.3f);
			
			p1.addTreatment(john, radio);
			p1.addTreatment(john, surgery);
			p1.addTreatment(kevin, surgery);
			
			List<Long> treatments = patientDAO.getPatientByPatientId(john.getPatientId()).getTreatmentIds();
			String s = "";
			for(Long l : treatments) {
				s = s + " " + l;
			}
			
			logger.info("treatments list " + s + " of patient: " + john.getName());
			
			Treatment treatment = treatmentDAO.getTreatment(surgery.getId());
			logger.info("Retrieved Treatment: Diag: " + treatment.getDiagnosis() + " id: " + treatment.getId());
					
		} catch (PatientExn e) {

			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
			ex.initCause(e);
			throw ex;
			
		} catch (ProviderExn e) {
			IllegalStateException ex = new IllegalStateException("Failed to add provider record.");
			ex.initCause(e);
			throw ex;
		} catch (TreatmentExn e) {
			// TODO Auto-generated catch block
			IllegalStateException ex = new IllegalStateException("Failed to for treatment operation.");
			ex.initCause(e);
			throw ex;
		}
			
	}

}
