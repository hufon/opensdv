package fr.esdeve.restresources;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.esdeve.dao.VenteDAO;
import fr.esdeve.model.Vente;


@Component
@Scope("prototype")
public class VenteListResource extends ServerResource {
	

	@Autowired
	private VenteDAO venteDAO;
	
	@Get
    public Representation get() {
        List<Vente> ventes = venteDAO.list();
        if (ventes == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new JacksonRepresentation<List<Vente>>(ventes);
    }

}
