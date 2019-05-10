package edu.stevens.cs548.clinic.service.dto.test;

import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
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
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;

public class SchemaTest {

	@Before
	public void setUp() throws Exception {
		System.out.println("** Testing with element substitution start");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("** Testing with element substitution end");
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
			
			DrugTreatmentType drugTreatmentType = treatmentFactory.createDrugTreatmentType();
			JAXBElement<DrugTreatmentType> drugTreatment = treatmentFactory.createDrugTreatmentDto(drugTreatmentType);
			drugTreatment.getValue().setDiagnosis("diagnosis-01");
			drugTreatment.getValue().setId(10001l);
			drugTreatment.getValue().setPatient(9998877l);
			drugTreatment.getValue().setProvider(10001l);
			drugTreatment.getValue().setDrug("drug-01");
			drugTreatment.getValue().setDosage(0.6f);
			
		    System.out.println();
			marshaller.marshal(drugTreatment,System.out);
			
			SurgeryType surgeryType = treatmentFactory.createSurgeryType();
			JAXBElement<SurgeryType> surgeryTreatment = treatmentFactory.createSurgeryTreatmentDto(surgeryType);
			surgeryTreatment.getValue().setDiagnosis("diagnosis-01");
			surgeryTreatment.getValue().setId(10001l);
			surgeryTreatment.getValue().setPatient(9998877l);
			surgeryTreatment.getValue().setProvider(10001l);
			surgeryTreatment.getValue().setDate(new Date());
			
		    System.out.println();
			marshaller.marshal(surgeryTreatment,System.out);
			
			RadiologyType radiologyType = treatmentFactory.createRadiologyType();
			JAXBElement<RadiologyType> radiologyTreatment = treatmentFactory.createRadiologyTypeDto(radiologyType);
			radiologyTreatment.getValue().setDiagnosis("diagnosis-01");
			radiologyTreatment.getValue().setId(10001l);
			radiologyTreatment.getValue().setPatient(9998877l);
			radiologyTreatment.getValue().setProvider(10001l);
			List<Date> dates = radiologyTreatment.getValue().getDate();
			dates.add(new Date());
			dates.add(new Date(System.currentTimeMillis() + 10000000l));
			
		    System.out.println();
			marshaller.marshal(radiologyTreatment,System.out);
	    } catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
