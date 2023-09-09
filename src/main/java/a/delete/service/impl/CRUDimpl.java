package a.delete.service.impl;

import a.delete.repository.IGenericRepo;
import a.delete.service.ICRUD;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class CRUDimpl<T,ID> implements ICRUD<T,ID> {

    protected abstract IGenericRepo<T,ID> repo();

    @Override
    public List<T> findAll() {
        return repo().findAll();
    }

    @Override
    public T findById(ID id) {
        return repo().findById(id).orElseThrow(null);
    }

    @Override
    public T save(T t) {
        return repo().save(t);
    }

    @Override
    public T update(ID id, T t) {
        repo().findById(id).orElseThrow(null);
        return repo().save(t);
    }

    @Override
    public void delete(ID id) {
        repo().deleteById(id);
    }
}
