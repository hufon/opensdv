package fr.esdeve.restresources;

import fr.esdeve.dao.VendorDAO;
import fr.esdeve.dao.VenteDAO;
import fr.esdeve.model.Vendor;
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
public class VendorListResource extends ServerResource {
	

	@Autowired
	private VendorDAO vendorDAO;
	
	@Get
    public Representation get() {
        List<Vendor> vendors = vendorDAO.list();
        if (vendors == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return new JacksonRepresentation<List<Vendor>>(vendors);
    }
	
    @Post("json")
    public void create(Representation representation) throws IOException {
        JacksonRepresentation<Vendor> jsonRepresentation = new JacksonRepresentation<Vendor>(representation, Vendor.class);
        Vendor newVendor = jsonRepresentation.getObject();
        vendorDAO.addBean(newVendor);
    }

}
