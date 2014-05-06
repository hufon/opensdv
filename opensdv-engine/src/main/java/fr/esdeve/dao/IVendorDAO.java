package fr.esdeve.dao;

import fr.esdeve.model.Vendor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hubert on 06/05/14.
 */
public interface IVendorDAO extends IGenericDAO<Vendor> {
    Integer getNextVendorNumber();

    @Transactional
    List<Vendor> searchVendor(String name);
}
