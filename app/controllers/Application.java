package controllers;

import models.Vente;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	return redirect(
    			routes.Application.listVente()
    	    );
       // return ok(index.render("coucou"));
    }
    
    public static Result listVente() {
    	return ok(listVente.render(Vente.listAll()));
    }
    

}
