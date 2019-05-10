package edu.stevens.cs548.clinic.service.dto.util;

import javax.xml.bind.JAXBElement;

import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public DrugTreatmentType createDrugTreatmentType() {
		return factory.createDrugTreatmentType();
	}
	
	public JAXBElement<DrugTreatmentType> createDrugTreatmentDto (DrugTreatmentType drugTreatment) {
		return factory.createDrugTreatmentDto(drugTreatment);
	}
	
	public RadiologyType createRadiologyType() {
		return factory.createRadiologyType();
	}
	
	public JAXBElement<RadiologyType> createRadiologyTypeDto (RadiologyType radiologyType) {
		return factory.createRadiologyTreatmentDto(radiologyType);
	}
	
	public SurgeryType createSurgeryType() {
		return factory.createSurgeryType();
	}
	
	public JAXBElement<SurgeryType> createSurgeryTreatmentDto (SurgeryType surgeryType) {
		return factory.createSurgeryTreatmentDto(surgeryType);
	}

}
