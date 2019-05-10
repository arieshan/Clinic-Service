package edu.stevens.cs548.clinic.service.dto.util;

import java.util.Date;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.RadiologyTreatment;
import edu.stevens.cs548.clinic.domain.SurgeryTreatment;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createDrugTreatmentDto () {
		TreatmentDto t = factory.createTreatmentDto();
		DrugTreatmentType dt = factory.createDrugTreatmentType();
		t.setDrugTreatment(dt);
		return t;
	}
	
	public PatientDto createTreatmentDto (DrugTreatment t) {
		/*
		 * TODO: Initialize the DTO from the drug treatment.
		 */
		return factory.createPatientDto();
	}
	

	public SurgeryType createsurgeryTreatmentDto () {
		return factory.createSurgeryType();
	}
	public RadiologyType createRadiologyTreatmentDto () {
		return factory.createRadiologyType();
	}
	
	public TreatmentDto createTreatmentDto(){
		return factory.createTreatmentDto();
	}
	
	/*
	 * TODO: Repeat for other treatments.
	 */
	
	public TreatmentDto createDrugTreatmentDto (DrugTreatment t) {
		TreatmentDto treatment = factory.createTreatmentDto();
		treatment.setDiagnosis(t.getDiagnosis());
		DrugTreatmentType drugTreatment = factory.createDrugTreatmentType();
		drugTreatment.setDosage(t.getDosage());
		drugTreatment.setDrug(t.getDrug());
		treatment.setDrugTreatment(drugTreatment);
		return treatment;
		
	}
	
	public TreatmentDto createSurgeryDto(SurgeryTreatment t){
		TreatmentDto treatment = factory.createTreatmentDto();
		treatment.setDiagnosis(t.getDiagnosis());
		SurgeryType surgeryTreatment = this.createsurgeryTreatmentDto();
		surgeryTreatment.setDate(t.getSurgeryDate());
		treatment.setSurgery(surgeryTreatment);
		return treatment;
		
	}
	
	public TreatmentDto createRadiologyDto(RadiologyTreatment t){
		TreatmentDto treatment = factory.createTreatmentDto();
		treatment.setDiagnosis(t.getDiagnosis());
		RadiologyType radiologyTreatment = this.createRadiologyTreatmentDto();
		for(Date d : t.getTreatmentDates()){
			radiologyTreatment.getDate().add(d);	
		}
		treatment.setRadiology(radiologyTreatment);
		return treatment;
		
	}

}
