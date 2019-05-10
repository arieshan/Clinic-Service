package edu.stevens.cs548.clinic.domain;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

public class ProviderDAO implements IProviderDAO {

	private EntityManager em;
	@Transient
	private TreatmentDAO treatmentDAO;
	
	public ProviderDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderDAO.class.getCanonicalName());

	@Override
	public long addProvider(Provider provider) throws ProviderExn {
		/*
		 * TODO add to database (and sync with database to generate primary key).
		 * Don't forget to initialize the Provider aggregate with a treatment DAO.
		 */
		long npi = provider.getNpi();
		Query query = em.createNamedQuery("CountProviderByNPI").setParameter("npi", npi);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			try {
				
				em.persist(provider);
				em.flush();
				provider.setTreatmentDAO(this.treatmentDAO);
				
			} catch (Exception e) {
				throw new IllegalStateException("Unimplemented");				
			}
			// TODO add to database (and sync with database to generate primary key)
			// Don't forget to initialize the patient aggregate with a treatment DAO
			
			return provider.getId();
			
		} else {
			throw new ProviderExn("Insertion: Provider with provider id (" + npi + ") already exists.");
		}
	}

	@Override
	public Provider getProvider(long id) throws ProviderExn {
		
		/*
		 * TODO retrieve Provider using primary key
		 */
		Provider provider = em.find(Provider.class, id);
		if (provider == null) {
			throw new ProviderExn("No provider Found with the provided Id");
		} else {
			provider.setTreatmentDAO(this.treatmentDAO);
			return provider;
		}
	}

	@Override
	public Provider getProviderByNPI(long pid) throws ProviderExn {
		/*
		 * TODO retrieve Provider using query on Provider id (secondary key)
		 */
		TypedQuery<Provider> query =  em.createNamedQuery("SearchProviderByNPI",Provider.class).setParameter("npi", pid);
		List<Provider> providerL = query.getResultList();
	
		if (providerL.isEmpty() || providerL.size() > 1) {
			throw new ProviderExn("Ambiguious result : "+providerL.size());
		} else {
			Provider provider = providerL.get(0);
			provider.setTreatmentDAO(this.treatmentDAO);
			return provider;
		}
	}
	
	@Override
	public void deleteProviders() {
		Query update = em.createNamedQuery("RemoveAllProviders");
		update.executeUpdate();
	}

}
