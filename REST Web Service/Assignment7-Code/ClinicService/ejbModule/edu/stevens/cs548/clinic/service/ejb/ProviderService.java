package edu.stevens.cs548.clinic.service.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.IProviderFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.Provider.ProviderType;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.ProviderSpecType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;

/**
 * Session Bean implementation class ProviderService
 */
@Stateless(name="ProviderServiceBean")
public class ProviderService implements IProviderServiceLocal, IProviderServiceRemote {	
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderService.class.getCanonicalName());

	private IProviderFactory providerFactory;
	
	private ProviderDtoFactory providerDtoFactory;

	private IProviderDAO providerDAO;
	
	private IPatientDAO patientDAO;
	
	private TreatmentFactory treatmentFactory;

	/**
	 * Default constructor.
	 */
	public ProviderService() {
		// TODO initialize factories
		providerFactory = new ProviderFactory();
		providerDtoFactory = new ProviderDtoFactory();
		treatmentFactory = new TreatmentFactory();
	}
	
	// TODO use dependency injection and EJB lifecycle methods to initialize DAOs

	// TODO
	//@PersistenceContext(unitName="ClinicDomain")
	@Inject @ClinicDomain
	private EntityManager em;

	@PostConstruct
	private void initialize() {
		// TODO
		providerDAO = new ProviderDAO(em);
		patientDAO = new PatientDAO(em);
	}

	/**
	 * @see IProviderService#addProvider(ProviderDto dto)
	 */
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		// TODO Use factory to create Provider entity, and persist with DAO
		Map<ProviderSpecType, ProviderType> map = new HashMap<>();
		map.put(ProviderSpecType.RADIOLOGY, ProviderType.RADIOLOGIST);
		map.put(ProviderSpecType.SURGERY, ProviderType.SURGEON);
		map.put(ProviderSpecType.INTERNAL, ProviderType.INTERNIST);
		
		try {			
			ProviderType ptype = map.getOrDefault(dto.getProviderSpec(), ProviderType.RADIOLOGIST);
			Provider provider = providerFactory.createProvider(dto.getNpi(), dto.getName(), ptype);
			providerDAO.addProvider(provider);
			return provider.getId();
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	/**
	 * @see IProviderService#getProvider(long)
	 */
	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		// TODO use DAO to get Provider by database key
		try {
			Provider provider = providerDAO.getProvider(id);
			ProviderDto dto = providerDtoFactory.createProviderDto(provider);
			return dto;
		} catch(ProviderExn ex) {
			throw new ProviderServiceExn(ex.toString());
		}
	}

	/**
	 * @see IProviderService#getProviderByNPI(long)
	 */
	@Override
	public ProviderDto getProviderByNPI(long npi) throws ProviderServiceExn {
		// TODO use DAO to get Provider by NPI
		try{
			Provider provider = providerDAO.getProviderByNPI(npi);
			//PatientDto patientDTO = patientToDTO(patient);
			
			return providerDtoFactory.createProviderDto(provider); 
		} catch(ProviderExn e){
			throw new ProviderServiceExn(e.getMessage());
		}
	}
		
	@Override
	public long addTreatment(TreatmentDto dto) throws PatientServiceExn, ProviderServiceExn {
		try {
			Provider provider = providerDAO.getProvider(dto.getProvider());
			Patient patient = patientDAO.getPatient(dto.getPatient());
			Treatment treatment;
			if (dto.getDrugTreatment() != null) {
				treatment = treatmentFactory.createDrugTreatment(dto.getDiagnosis(), dto.getDrugTreatment().getDrug(),
						dto.getDrugTreatment().getDosage());
			} else if (dto.getRadiology() != null) {
				treatment = treatmentFactory.createRadiologyTreatment(dto.getDiagnosis(), dto.getRadiology().getDate());			

			} else if(dto.getSurgery() != null) {
				treatment = treatmentFactory.createSurgeryTreatment(dto.getDiagnosis(), dto.getSurgery().getDate());

			} else {
				// TODO Handle the other cases
				throw new IllegalArgumentException("No treatment-specific info provided.");
			}
			treatment.setPatient(patient);
			return provider.addTreatment(patient, treatment);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		}
	}

	@Override
	public List<Long> getTreatments(long id) throws ProviderServiceExn {
		// TODO Use DAO to get list of treatments for the provider
		try {
			Provider provider =  providerDAO.getProvider(id);
			List<Long> treatmentIds = new ArrayList<>();
			provider.getTreatmentIds(treatmentIds);
			return treatmentIds;
		} catch (ProviderExn e) {
			// TODO Auto-generated catch block
			throw new ProviderNotFoundExn(e.toString());
		}
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		// TODO Export treatment DTO from Provider aggregate
		try {
			Provider provider =  providerDAO.getProvider(id);
			TreatmentExporter visitor = new TreatmentExporter();
			
			return provider.exportTreatment(tid, visitor);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}
	
	// TODO inject resource value
	@Resource(name="SiteInfo")
	private String siteInformation;
	

	@Override
	public String siteInfo() {
		return siteInformation;
	}

	public static ProviderType getType(ProviderSpecType providerType) {
		switch (providerType) {
		case INTERNAL:
			return ProviderType.INTERNIST;
		case RADIOLOGY:
			return ProviderType.RADIOLOGIST;
		case SURGERY:
			return ProviderType.SURGEON;
		default:
			throw new IllegalStateException("Unrecognized provider type: "+providerType);
		}
	}

}
