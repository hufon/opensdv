package fr.esdeve.dao;

import fr.esdeve.model.Client;
import fr.esdeve.model.Vente;

import java.util.List;

/**
 * Created by hubert on 06/05/14.
 */
public interface IClientDAO extends IGenericDAO<Client> {
    public List<Client> listClientByVente(Vente vente);
}
