CREATE TABLE Patient (ID BIGINT NOT NULL, NAME VARCHAR(255), PATIENTID BIGINT, PRIMARY KEY (ID))
CREATE TABLE Treatment (ID BIGINT NOT NULL, DTYPE VARCHAR(31), DIAGNOSIS VARCHAR(255), TREATMENT_TYPE VARCHAR(2), PATIENT_ID BIGINT, PROVIDER_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE DRUG_TREATMENT (ID BIGINT NOT NULL, DOSAGE FLOAT, DRUG VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE Provider (ID BIGINT NOT NULL, NAME VARCHAR(255), NPI BIGINT, PROVIDERTYPE INTEGER, PRIMARY KEY (ID))
CREATE TABLE SURGERY (ID BIGINT NOT NULL, SURGERYDATE DATE, PRIMARY KEY (ID))
CREATE TABLE RADIOLOGY (ID BIGINT NOT NULL, PRIMARY KEY (ID))
CREATE TABLE Radiology_dates (RadiologyTreatment_ID BIGINT, TREATMENTDATES DATE)
ALTER TABLE Treatment ADD CONSTRAINT FK_Treatment_PROVIDER_ID FOREIGN KEY (PROVIDER_ID) REFERENCES Provider (ID)
ALTER TABLE Treatment ADD CONSTRAINT FK_Treatment_PATIENT_ID FOREIGN KEY (PATIENT_ID) REFERENCES Patient (ID)
ALTER TABLE DRUG_TREATMENT ADD CONSTRAINT FK_DRUG_TREATMENT_ID FOREIGN KEY (ID) REFERENCES Treatment (ID)
ALTER TABLE SURGERY ADD CONSTRAINT FK_SURGERY_ID FOREIGN KEY (ID) REFERENCES Treatment (ID)
ALTER TABLE RADIOLOGY ADD CONSTRAINT FK_RADIOLOGY_ID FOREIGN KEY (ID) REFERENCES Treatment (ID)
ALTER TABLE Radiology_dates ADD CONSTRAINT FK_Radiology_dates_RadiologyTreatment_ID FOREIGN KEY (RadiologyTreatment_ID) REFERENCES Treatment (ID)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
