package dat.daos;

import dat.exceptions.ApiException;

import java.util.List;

public interface IDAO<T, I> {

    T getById(I i) throws ApiException;
    List<T> getAll();
    T create(T t);
    T update(I i, T t) throws ApiException;
    T delete(I i) throws ApiException;

}
