--<ScriptOptions statementTerminator=";"/>

CREATE TABLE treatment (
		id INT8 NOT NULL,
		dtype VARCHAR(31),
		diagnosis VARCHAR(255),
		treatment_type VARCHAR(2),
		patient_id INT8,
		provider_id INT8
	);

CREATE TABLE provider (
		id INT8 NOT NULL,
		name VARCHAR(255),
		npi INT8,
		providertype INT4
	);

CREATE TABLE drug_treatment (
		id INT8 NOT NULL,
		dosage FLOAT8,
		drug VARCHAR(255)
	);

CREATE TABLE sequence (
		seq_name VARCHAR(50) NOT NULL,
		seq_count NUMERIC(38 , 0)
	);

CREATE TABLE patient (
		id INT8 NOT NULL,
		name VARCHAR(255),
		patientid INT8
	);

CREATE TABLE radiology_dates (
		radiologytreatment_id INT8,
		treatmentdates DATE
	);

CREATE TABLE radiology (
		id INT8 NOT NULL
	);

CREATE TABLE surgery (
		id INT8 NOT NULL,
		surgerydate DATE
	);

CREATE UNIQUE INDEX provider_pkey ON provider (id ASC);

CREATE UNIQUE INDEX patient_pkey ON patient (id ASC);

CREATE UNIQUE INDEX treatment_pkey ON treatment (id ASC);

CREATE UNIQUE INDEX drug_treatment_pkey ON drug_treatment (id ASC);

CREATE UNIQUE INDEX surgery_pkey ON surgery (id ASC);

CREATE UNIQUE INDEX radiology_pkey ON radiology (id ASC);

CREATE UNIQUE INDEX sequence_pkey ON sequence (seq_name ASC);

ALTER TABLE radiology ADD CONSTRAINT radiology_pkey PRIMARY KEY (id);

ALTER TABLE patient ADD CONSTRAINT patient_pkey PRIMARY KEY (id);

ALTER TABLE surgery ADD CONSTRAINT surgery_pkey PRIMARY KEY (id);

ALTER TABLE treatment ADD CONSTRAINT treatment_pkey PRIMARY KEY (id);

ALTER TABLE sequence ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);

ALTER TABLE provider ADD CONSTRAINT provider_pkey PRIMARY KEY (id);

ALTER TABLE drug_treatment ADD CONSTRAINT drug_treatment_pkey PRIMARY KEY (id);

ALTER TABLE surgery ADD CONSTRAINT fk_surgery_id FOREIGN KEY (id)
	REFERENCES treatment (id);

ALTER TABLE drug_treatment ADD CONSTRAINT fk_drug_treatment_id FOREIGN KEY (id)
	REFERENCES treatment (id);

ALTER TABLE radiology_dates ADD CONSTRAINT fk_radiology_dates_radiologytreatment_id FOREIGN KEY (radiologytreatment_id)
	REFERENCES treatment (id);

ALTER TABLE radiology ADD CONSTRAINT fk_radiology_id FOREIGN KEY (id)
	REFERENCES treatment (id);

ALTER TABLE treatment ADD CONSTRAINT fk_treatment_provider_id FOREIGN KEY (provider_id)
	REFERENCES provider (id);

ALTER TABLE treatment ADD CONSTRAINT fk_treatment_patient_id FOREIGN KEY (patient_id)
	REFERENCES patient (id);

