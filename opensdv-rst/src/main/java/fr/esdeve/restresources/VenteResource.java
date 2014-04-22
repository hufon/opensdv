package fr.esdeve.restresources;


import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.esdeve.dao.VenteDAO;
import fr.esdeve.model.Vente;

import java.io.IOException;


@Component
public class VenteResource extends ServerResource {
	

	@Autowired
	private VenteDAO venteDAO;
	
	private String venteId;

	@Override
	protected void doInit() throws ResourceException {
		this.venteId = getAttribute("venteId");
	}
	
	@Get
    public Representation get() {
        Vente todo = venteDAO.get(venteId);
        if (todo == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new JacksonRepresentation<Vente>(todo);
    }

    @Put("json")
    public void update(Representation representation) throws IOException {
        JacksonRepresentation<Vente> jsonRepresentation = new JacksonRepresentation<Vente>(representation, Vente.class);
        Vente vente = jsonRepresentation.getObject();
        venteDAO.save(vente);
    }
	
	@Delete
    public void remove() {
		venteDAO.remove(venteId);
    }

}
