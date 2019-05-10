package edu.stevens.cs548.clinic.service.web.soap;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

// TODO Use JSR-181 annotations to specify Web service.
//Specify: endpoint interface (FQN), target namespace, service name, port name
@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap/provider", 
		serviceName = "ProviderWebService", portName = "ProviderWebPort")

public class ProviderWebService implements IProviderWebService {

	// TODO use CDI to inject the service
	@Inject
	IProviderServiceLocal service;

	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		// TODO
		return service.addProvider(dto);
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		// TODO
		return service.getProvider(id);
	}

	@Override
	public ProviderDto getProviderByNPI(long pid) throws ProviderServiceExn {
		// TODO
		return service.getProviderByNPI(pid);
	}

	@Override
	public void addTreatment(TreatmentDto dto) throws PatientServiceExn, ProviderServiceExn {
		// TODO
		service.addTreatment(dto);
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn,
			ProviderServiceExn {
		// TODO
		return service.getTreatment(id, tid);
	}

	@Override
	public String siteInfo() {
		// TODO
		return service.siteInfo();
	}

}
