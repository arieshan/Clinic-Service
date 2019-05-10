package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.RepresentationFactory;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(ProviderResource.class.getCanonicalName());
	
    @Context
    private UriInfo uriInfo;
    
    private RepresentationFactory factory = new RepresentationFactory();
    
    private ProviderDtoFactory providerDtoFactory;
    
    /**
     * Default constructor. 
     */
    public ProviderResource() {
    	providerDtoFactory = new ProviderDtoFactory();
    }
    
    // TODO
    @Inject
    private IProviderServiceLocal providerService;
    
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return providerService.siteInfo();
    }

	// TODO complete including annotations
    @POST
    @Consumes("application/xml")
     public Response addProvider(ProviderRepresentation providerRep) {
    	try {
    		ProviderDto dto = providerDtoFactory.createProviderDto();
    		dto.setNpi(providerRep.getNpi());
    		dto.setName(providerRep.getName());
    		dto.setProviderSpec(ProviderRepresentation.toDtoSpecType(providerRep.getSpec()));
    		long id = providerService.addProvider(dto);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new RuntimeException(e);
	}
    }
    
	/**
	 * Query methods for provider resources.
	 */
	// TODO complete including annotations
    @GET
    @Path("{id}")
    @Produces("application/xml")
	public ProviderRepresentation getProvider(@PathParam("id") String id) {
		try {
			long key = Long.parseLong(id);
			ProviderDto providerDTO = providerService.getProvider(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
    
	// TODO complete including annotations
    @GET
	@Path("byNPI")
	@Produces("application/xml")
	public ProviderRepresentation getProviderByNpi(@QueryParam("npi") String npi) {
		try {
			long pid = Long.parseLong(npi);
			ProviderDto providerDTO = providerService.getProviderByNPI(pid);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	// TODO complete including annotations
    @POST
    @Path("{id}/treatment")
    @Produces("application/xml")
	public Response addTreatment(@PathParam("id") String id, TreatmentRepresentation treatmentRep) {
		long tid = 0; 
    	try {
    		TreatmentDto treatmentDto = treatmentRep.getTreatment();
    		tid = providerService.addTreatment(treatmentDto);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{tid}");
    		URI url = ub.build(Long.toString(tid));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn | PatientServiceExn e) {
			throw new RuntimeException(e);
	}
	}
    
	// TODO complete including annotations
    @GET
    @Path("{id}/treatments/{tid}")
	@Produces("application/xml")
    public TreatmentRepresentation getProviderTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid));
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
    		return treatmentRep;
    	} catch (ProviderServiceExn e) {
		throw new RuntimeException(e);
	}
    }

}