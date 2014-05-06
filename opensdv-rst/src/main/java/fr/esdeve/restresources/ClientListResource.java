package fr.esdeve.restresources;

import fr.esdeve.dao.IClientDAO;
import fr.esdeve.dao.IVendorDAO;
import fr.esdeve.dao.IVenteDAO;
import fr.esdeve.model.Client;
import fr.esdeve.model.Vendor;
import fr.esdeve.model.Vente;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
@Scope("prototype")
public class ClientListResource extends ServerResource {
	

	@Autowired
	private IClientDAO clientDAO;

    @Autowired
    private IVenteDAO venteDAO;

    private String venteId;


    @Override
    protected void doInit() throws ResourceException {
        this.venteId = getQueryValue("venteId");
    }
	
	@Get
    public Representation get() {
        List<Client> clients;

        if (venteId != null)
        {
            Vente vente = venteDAO.get(this.venteId);
            if (vente == null)
            {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            clients = clientDAO.listClientByVente(vente);
        } else {
            clients = clientDAO.list();
        }
        if (clients == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new JacksonRepresentation<List<Client>>(clients);
    }
	
    @Post("json")
    public void create(Representation representation) throws IOException {
        JacksonRepresentation<Client> jsonRepresentation = new JacksonRepresentation<Client>(representation, Client.class);
        Client newClient = jsonRepresentation.getObject();
        clientDAO.addBean(newClient);
    }

}
