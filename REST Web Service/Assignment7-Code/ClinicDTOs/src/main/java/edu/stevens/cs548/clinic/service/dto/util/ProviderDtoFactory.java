package edu.stevens.cs548.clinic.service.dto.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.Provider.ProviderType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.ProviderSpecType;

public class ProviderDtoFactory {
	
	ObjectFactory factory;
	
	public ProviderDtoFactory() {
		factory = new ObjectFactory();
	}

	public ProviderDto createProviderDto() {
		return factory.createProviderDto();
	}

	public ProviderDto createProviderDto(Provider p) {
		ProviderDto d = factory.createProviderDto();
		/*
		 * TODO: Initialize the fields of the DTO.
		 */
		
		Map<ProviderType, ProviderSpecType> map = new HashMap<>();
		map.put(ProviderType.RADIOLOGIST, ProviderSpecType.RADIOLOGY);
		map.put(ProviderType.SURGEON, ProviderSpecType.SURGERY);
		map.put(ProviderType.INTERNIST, ProviderSpecType.INTERNAL);
		
		d.setId(p.getId());
		d.setNpi(p.getNpi());
		d.setName(p.getName());
		d.setProviderSpec(map.get(p.getProviderType()));

		List<Long> tids = new ArrayList<>();
		p.getTreatmentIds(tids);
		List<Long> treatments = d.getTreatments();
		for (Long treatment : tids) {
			treatments.add(treatment);
		}

		return d;
	}

	public static ProviderSpecType getSpecType(ProviderType providerType) {
		switch (providerType) {
		case INTERNIST:
			return ProviderSpecType.INTERNAL;
		case RADIOLOGIST:
			return ProviderSpecType.RADIOLOGY;
		case SURGEON:
			return ProviderSpecType.SURGERY;
		default:
			throw new IllegalStateException("Unrecognized provider type: " + providerType);
		}
	}

}
