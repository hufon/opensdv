package fr.esdeve.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hubert on 06/05/14.
 */
public interface IGenericDAO<T> {

    @Transactional
    T addBean(T newItem);

    T get(String itemId);

    void remove(T item);

    @Transactional
    void remove(String itemId);

    @Transactional
    void save(T item);

    @Transactional
    List<T> list();
}
