package edu.stevens.cs548.clinic.service.representations;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.ProviderSpecType;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.ProviderSpec;
import edu.stevens.cs548.clinic.service.web.rest.data.ProviderType;
import edu.stevens.cs548.clinic.service.web.rest.data.dap.LinkType;

@XmlRootElement
public class ProviderRepresentation extends ProviderType {

	public List<LinkType> getLinksTreatments() {
		return this.getTreatments();
	}
	
	public static LinkType getProviderLink(long id, UriInfo uriInfo) {
		/*
		 * TODO return a link for this provider: http://.../provider/<id>  see patient
		 * Remember a link has three parts: URI, relation, media type.
		 */
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("provider").path("{id}");
		String providerURI = ub.build(Long.toString(id)).toString();

		LinkType link = new LinkType();
		link.setUrl(providerURI);
		link.setRelation(Representation.RELATION_PROVIDER);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	private ProviderDtoFactory providerDtoFactory;
	
	public ProviderRepresentation () {
		super();
		this.providerDtoFactory = new ProviderDtoFactory();
	}
	
	public ProviderRepresentation (ProviderDto dto, UriInfo uriInfo) {
		this();
		this.id = getProviderLink(dto.getId(), uriInfo);
		this.npi =  dto.getNpi();
		this.name = dto.getName();
		this.spec = fromDtoSpecType(dto.getProviderSpec());
		/*
		 * Call getTreatments to initialize empty list.
		 */
		List<LinkType> links = this.getTreatments();
		for (long t : dto.getTreatments()) {
			links.add(TreatmentRepresentation.getTreatmentLink(t, uriInfo));
		}
	}
	
	public ProviderDto getProviderDto() {
		ProviderDto p = providerDtoFactory.createProviderDto();
		p.setId(Representation.getId(this.id));
		p.setNpi(this.npi);
		p.setName(this.name);
		p.setProviderSpec(toDtoSpecType(this.spec));
		return p;
	}
	
	public static ProviderSpec fromDtoSpecType(ProviderSpecType specType) {
		switch(specType) {
		case INTERNAL:
			return ProviderSpec.INTERNAL;
		case RADIOLOGY:
			return ProviderSpec.RADIOLOGY;
		case SURGERY:
			return ProviderSpec.SURGERY;
		default:
			throw new IllegalArgumentException("Unrecognized provider spec type: "+specType);
		}
	}

	public static ProviderSpecType toDtoSpecType(ProviderSpec specType) {
		switch(specType) {
		case INTERNAL:
			return ProviderSpecType.INTERNAL;
		case RADIOLOGY:
			return ProviderSpecType.RADIOLOGY;
		case SURGERY:
			return ProviderSpecType.SURGERY;
		default:
			throw new IllegalArgumentException("Unrecognized provider spec: "+specType);
		}
	}

}
