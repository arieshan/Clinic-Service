package edu.stevens.cs548.clinic.service.dto.util;

import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;

public class PatientDtoFactory {
	
	ObjectFactory factory;
	
	public PatientDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public PatientDto createPatientDto () {
		return factory.createPatientDto();
	}
	
	public PatientDto createPatientDto (Patient p) {
		PatientDto d = factory.createPatientDto();
		/*
		 * TODO: Initialize the fields of the DTO.
		 */
		
		//throw new IllegalStateException("PatientDtoFactory");
		d.setId(p.getId());
		d.setName(p.getName());
		d.setPatientId(p.getPatientId());
		
		List<Long> tids = p.getTreatmentIds();
		
		for (Long treatment : tids) {
			d.getTreatments().add(treatment);
		}
		return d;
	}

}
