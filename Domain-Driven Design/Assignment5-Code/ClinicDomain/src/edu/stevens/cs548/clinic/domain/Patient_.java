package edu.stevens.cs548.clinic.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-10-06T11:34:05.657-0700")
@StaticMetamodel(Patient.class)
public class Patient_ {
	public static volatile SingularAttribute<Patient, Long> id;
	public static volatile SingularAttribute<Patient, Long> patientId;
	public static volatile SingularAttribute<Patient, String> name;
	public static volatile ListAttribute<Patient, Treatment> treatments;
}
