import java.util.Date;

import models.Vente;
import play.Application;
import play.GlobalSettings;
import play.Logger.ALogger;


public class Global extends GlobalSettings {
	public void onStart(Application app) {
		ALogger log = play.Logger.of("application");
		log.info("onStart...");
        insert(app);
    }
	
	private static void insert(Application app){
		if (Vente.listAll().size()==0) {
			Vente vae1 = new Vente();
			vae1.name="test";
			vae1.date = new Date();
			vae1.save();
		}
		
	}
}
