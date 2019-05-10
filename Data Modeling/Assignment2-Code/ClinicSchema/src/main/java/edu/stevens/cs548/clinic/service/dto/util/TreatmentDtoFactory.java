package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createTreatmentDto () {
		return factory.createTreatmentDto();
	}
	
	public DrugTreatmentType createDrugTreatmentDto () {
		return factory.createDrugTreatmentType();
	}
	
	public SurgeryType createSurgeryTreatmentDto () {
		return factory.createSurgeryType();
	}
	
	public RadiologyType createRadiologyTreatmentDto () {
		return factory.createRadiologyType();
	}

}
