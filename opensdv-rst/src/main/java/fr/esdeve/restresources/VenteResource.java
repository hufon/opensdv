package fr.esdeve.restresources;


import fr.esdeve.dao.IVenteDAO;
import fr.esdeve.utils.SdvParams;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import fr.esdeve.dao.impl.VenteDAO;
import fr.esdeve.model.Vente;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Scope("prototype")
public class VenteResource extends ServerResource {


    @Autowired
    private IVenteDAO venteDAO;

    private String venteId;

    @Autowired
    private SdvParams sdvParams;

    @Override
    protected void doInit() throws ResourceException {
        this.venteId = getAttribute("venteId");
    }

    @Get
    public Representation get() {
        Vente vente;
        if (venteId.equals("new")) {
            vente = new Vente();
            vente.setLocation(sdvParams.getDefaultVenteLocation());
        } else {
            vente = venteDAO.get(venteId);
            if (vente == null) {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
        }
        return new JacksonRepresentation<Vente>(vente);
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
