package edu.stevens.cs548.clinic.service.dto.test;

import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.ProviderSpecType;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;

public class SchemaTest {

	@Before
	public void setUp() throws Exception {
		System.out.println("** Testing with choice element start");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("** Testing with choice element end");
	}

	@Test
	public void test() {
		PatientDtoFactory patientFactory = new PatientDtoFactory();
		ProviderDtoFactory providerFactory = new ProviderDtoFactory();
		TreatmentDtoFactory treatmentFactory = new TreatmentDtoFactory();

	    try {
		    JAXBContext context = JAXBContext.newInstance("edu.stevens.cs548.clinic.service.dto");
		    Marshaller marshaller = context.createMarshaller();
		    marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);

		    PatientDto patient = patientFactory.createPatientDto();
		    patient.setId(20001l);
		    patient.setName("kevvvin");
		    patient.setPatientId(9998877l);
		    
		    List<Long> treatments = patient.getTreatments();
		    treatments.add(10001l);
		    treatments.add(10002l);
		    treatments.add(10003l);
		    
		    System.out.println();
			marshaller.marshal(patient,System.out);

		    ProviderDto provider = providerFactory.createProviderDto();
		    provider.setId(10001l);
		    provider.setName("provider");
		    provider.setNpi("identifier-0001");
		    provider.setProviderSpec(ProviderSpecType.SURGERY);
		    provider.getTreatments().addAll(treatments);
		    
		    
		    System.out.println();
			marshaller.marshal(provider,System.out);
			
			DrugTreatmentType drugTreatment = treatmentFactory.createDrugTreatmentDto();
			drugTreatment.setDosage(0.6f);
			drugTreatment.setDrug("drug-1");
			
			SurgeryType surgery = treatmentFactory.createSurgeryTreatmentDto();
			surgery.setDate(new Date());
			
			RadiologyType radiology = treatmentFactory.createRadiologyTreatmentDto();
			List<Date> dates = radiology.getDate();
			dates.add(new Date());
			dates.add(new Date());
			
			TreatmentDto treatment = treatmentFactory.createTreatmentDto();
			treatment.setDiagnosis("diagnosis-01");
			treatment.setDrugTreatment(drugTreatment);
			treatment.setId(10001l);
			treatment.setPatient(9998877l);
			treatment.setProvider(10001l);
			treatment.setRadiology(radiology);
			treatment.setSurgery(surgery);
			
			System.out.println();
			marshaller.marshal(treatment,System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
