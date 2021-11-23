package service.factory;

import model.exception.*;

import java.util.List;

public interface ItemService <K, T> {

    T getItemById(K id) throws ServiceException;

    void add(T entity) throws ServiceException, EntityAlreadyExist;

    T update(T entity);

    boolean delete(K id);

    List<T> getAllItemsAsList();


}