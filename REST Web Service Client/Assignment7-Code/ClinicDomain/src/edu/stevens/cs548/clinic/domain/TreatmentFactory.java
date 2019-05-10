package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentFactory implements ITreatmentFactory {
	
	/*
	 * Patient and provider fields are set when the treatment is added (see Provider).
	 * Id field is set when the treatment entity is synced with the database (see TreatmentDAO).
	 */

	@Override
	public Treatment createDrugTreatment(String diagnosis, String drug, float dosage) {
		DrugTreatment treatment = new DrugTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		return treatment;
	}

	@Override
	public Treatment createRadiologyTreatment(String diagnosis, List<Date> date) {
		// TODO Auto-generated method stub
		RadiologyTreatment radiology = new RadiologyTreatment();
		radiology.setDiagnosis(diagnosis);

		radiology.setTreatmentDates(date);
		return radiology;
	}

	@Override
	public Treatment createSurgeryTreatment(String diagnosis, Date date) {
		// TODO Auto-generated method stub
		SurgeryTreatment surgery = new SurgeryTreatment();
		surgery.setDiagnosis(diagnosis);
		surgery.setSurgeryDate(date);
		return surgery;
	}
	
	// TODO define other factory methods

}
