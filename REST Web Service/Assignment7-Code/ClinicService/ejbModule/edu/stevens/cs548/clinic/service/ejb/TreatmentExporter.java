package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
	
	private ObjectFactory factory = new ObjectFactory();
	
	@Override
	public TreatmentDto exportDrugTreatment(long tid, long patientId, long providerId, String diagnosis,
			String drug, float dosage) {
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(diagnosis);
		DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
		drugInfo.setDosage(dosage);
		drugInfo.setDrug(drug);
		dto.setDrugTreatment(drugInfo);
		return dto;
	}

	@Override
	public TreatmentDto exportRadiology(long tid, long patientId, long providerId, String diagnosis, List<Date> dates) {
		// TODO Auto-generated method stub	
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(diagnosis);
		RadiologyType radiologyInfo = factory.createRadiologyType();
		List<Date> rdates = radiologyInfo.getDate();
		rdates.addAll(dates);
		dto.setRadiology(radiologyInfo);
		return dto;
	}

	@Override
	public TreatmentDto exportSurgery(long tid, long patientId, long providerId, String diagnosis, Date date) {
		// TODO Auto-generated method stub	
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(diagnosis);
		SurgeryType sinfo = factory.createSurgeryType();
		sinfo.setDate(date);
		return dto;
	}
	
}