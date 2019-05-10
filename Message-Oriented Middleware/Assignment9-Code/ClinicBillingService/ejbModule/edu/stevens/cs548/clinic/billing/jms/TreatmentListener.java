package edu.stevens.cs548.clinic.billing.jms;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import edu.stevens.cs548.clinic.billing.service.BillingDtoFactory;
import edu.stevens.cs548.clinic.billing.service.IBillingService.BillingDTO;
import edu.stevens.cs548.clinic.billing.service.IBillingServiceLocal;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: TreatmentListener
 *
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms/Treatment")
public class TreatmentListener implements MessageListener {
	
	private static Logger logger = Logger.getLogger(TreatmentListener.class.getCanonicalName());

    /**
     * Default constructor. 
     */
    public TreatmentListener() {
        billingDtoFactory = new BillingDtoFactory();
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    
    @Inject IBillingServiceLocal billingService;
    
    private BillingDtoFactory billingDtoFactory;
    
    public void onMessage(Message message) {
        ObjectMessage objMessage = (ObjectMessage)message;
        try {
        	TreatmentDto treatment = (TreatmentDto)(objMessage.getObject());
        	
        	//objMessage.setStringProperty("treatmentType", getTreatmentType(treatment));
        	logger.info("Billing obtained a treatment record: "+treatment.getDiagnosis());

        	BillingDTO dto = billingDtoFactory.createBillingDTO();
        	dto.setDate(new Date(System.currentTimeMillis()));
        	dto.setDescription(getTreatmentDescription(treatment));
        	dto.setTreatmentId(treatment.getId());
        	
        	Random generator = new Random();
        	float amount = generator.nextFloat() * 500;
        	dto.setAmount(amount);
        	
        	billingService.addBillingRecord(dto);
        	
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
    
    private static String getTreatmentDescription(TreatmentDto treatment) {
    	if (treatment.getDrugTreatment() != null) {
    		return treatment.getDrugTreatment().getDrug();
    		// TODO finish the other cases
    	} else if (treatment.getSurgery() != null) {
    		return treatment.getSurgery().getDate().toString();
    	} else if (treatment.getRadiology() != null) {
    		List<Date> dates = treatment.getRadiology().getDate();
    		return dates.stream().map(Date::toString).collect(Collectors.joining(","));
    	} else {
    		throw new RuntimeException("treatment is not initialize. TreatmentListener - 87");
    	}
    }

}
