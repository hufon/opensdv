package fr.esdeve.restresources;


import fr.esdeve.dao.IClientDAO;
import fr.esdeve.model.Client;
import fr.esdeve.utils.SdvParams;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ClientResource extends ServerResource {
	

	@Autowired
	private IClientDAO clientDAO;

    @Autowired
    private SdvParams sdvParams;
	
	private String clientId;

	@Override
	protected void doInit() throws ResourceException {
		this.clientId = getAttribute("clientId");
	}
	
	@Get("json")
    public Representation get() {
        Client client;
        if (clientId.equals("new")) {
            client = new Client();
            client.setRate(sdvParams.getDefaultClientRate());
        } else
        {
            client = clientDAO.get(clientId);
        }
        if (client == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new JacksonRepresentation<Client>(client);
    }

    @Put("json")
    public void update(Representation representation) throws IOException {
        JacksonRepresentation<Client> jsonRepresentation = new JacksonRepresentation<Client>(representation, Client.class);
        Client client = jsonRepresentation.getObject();
        clientDAO.save(client);
    }
	
	@Delete
    public void remove() {

        clientDAO.remove(clientId);
    }

}
