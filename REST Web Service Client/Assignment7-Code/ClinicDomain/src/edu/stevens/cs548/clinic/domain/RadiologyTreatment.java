package edu.stevens.cs548.clinic.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "RADIOLOGY")
public class RadiologyTreatment extends Treatment {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3656673416179492428L;

	// TODO JPA annotation
	@ElementCollection
	@Temporal(TemporalType.DATE)
	@CollectionTable(name="Radiology_dates")
	protected List<Date> treatmentDates;

	public List<Date> getTreatmentDates() {
		return treatmentDates;
	}

	public void setTreatmentDates(List<Date> treatmentDates) {
		this.treatmentDates = treatmentDates;
	}

	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		// TODO
		List<Date> rdList = new ArrayList<Date>();
		for(Date rd : this.treatmentDates){
			rdList.add(rd);
		}
		return visitor.exportRadiology(this.getId(), 0, 0, getDiagnosis(), rdList); 
	}
	
}
