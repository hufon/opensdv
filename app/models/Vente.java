package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.*;

import play.data.validation.*;
import play.data.format.*;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity 
@SequenceGenerator(name = "vente_seq", sequenceName = "vente_seq")
public class Vente extends Model {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vente_seq")
    public Long id;
    
    @Constraints.Required
    public String name;
    
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date date;
    
    /**
     * Generic query helper for entity Computer with id Long
     */
    public static Finder<Long,Vente> find = new Finder<Long,Vente>(Long.class, Vente.class); 
    
    public static List<Vente> listAll()
    {
    	return find.all();
    }
}
