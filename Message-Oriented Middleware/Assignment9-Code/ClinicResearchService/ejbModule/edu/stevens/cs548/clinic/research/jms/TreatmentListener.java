package edu.stevens.cs548.clinic.research.jms;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import edu.stevens.cs548.clinic.research.service.DrugTreatmentDtoFactory;
import edu.stevens.cs548.clinic.research.service.IResearchService.DrugTreatmentDTO;
import edu.stevens.cs548.clinic.research.service.IResearchServiceLocal;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: TreatmentListener
 *
 */
// TODO add annotation to deploy as a listener on jms/Treatment topic, filtering drug treatments only
@MessageDriven(activationConfig = {
//		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jmsTreatment"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "treatmentType='Drug'") }, mappedName = "jms/Treatment")
public class TreatmentListener implements MessageListener {
	
	private static Logger logger = Logger.getLogger(TreatmentListener.class.getCanonicalName());

    /**
     * Default constructor. 
     */
    public TreatmentListener() {
        drugTreatmentDtoFactory = new DrugTreatmentDtoFactory();
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    
    @Inject IResearchServiceLocal researchService;
    
    private DrugTreatmentDtoFactory drugTreatmentDtoFactory;
    
    public void onMessage(Message message) {
        ObjectMessage objMessage = (ObjectMessage)message;
        try {
        	TreatmentDto treatment = (TreatmentDto)(objMessage.getObject());
        	
        	//objMessage.setStringProperty("treatmentType", getTreatmentType(treatment));
        	logger.info("Research obtained a treatment record: "+treatment.getDiagnosis());

        	DrugTreatmentDTO dto = drugTreatmentDtoFactory.createDrugTreatmentDTO();
        	dto.setDate(new Date(System.currentTimeMillis()));
        	dto.setDosage(treatment.getDrugTreatment().getDosage());
        	dto.setDrugName(treatment.getDrugTreatment().getDrug());
        	dto.setPatientId(treatment.getPatient());
        	dto.setTreatmentId(treatment.getId());
        	
        	researchService.addDrugTreatmentRecord(dto);
        	
        } catch (JMSException e) {
        	logger.log(Level.SEVERE, "Failure while consuming JMS message", e);
        }
    }
    
    private String getTreatmentType(TreatmentDto treatment) {
    	if (treatment.getDrugTreatment() != null) {
    		return "Drug";
    	} else if (treatment.getSurgery() != null) {
    		return "Surgery";
    	} else if (treatment.getRadiology() != null) {
    		List<Date> dates = treatment.getRadiology().getDate();
    		return "Radiology";
    	} else {
    		throw new RuntimeException("treatment is not initialize. TreatmentListener - 66");
    	}
    }
    
}
