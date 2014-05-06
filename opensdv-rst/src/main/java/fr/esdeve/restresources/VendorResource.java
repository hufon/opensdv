package fr.esdeve.restresources;


import fr.esdeve.dao.IVendorDAO;
import fr.esdeve.dao.impl.VendorDAO;
import fr.esdeve.model.Vendor;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;
import org.restlet.resource.ResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VendorResource extends ServerResource {
	

	@Autowired
	private IVendorDAO vendorDAO;
	
	private String vendorId;

	@Override
	protected void doInit() throws ResourceException {
		this.vendorId = getAttribute("vendorId");
	}
	
	@Get("json")
    public Representation get() {
        Vendor vendor = vendorDAO.get(vendorId);
        if (vendor == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new JacksonRepresentation<Vendor>(vendor);
    }

    @Put("json")
    public void update(Representation representation) throws IOException {
        JacksonRepresentation<Vendor> jsonRepresentation = new JacksonRepresentation<Vendor>(representation, Vendor.class);
        Vendor vendor = jsonRepresentation.getObject();
        vendorDAO.save(vendor);
    }
	
	@Delete
    public void remove() {
		vendorDAO.remove(vendorId);
    }

}
