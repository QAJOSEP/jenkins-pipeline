package a.delete.service.impl;

import a.delete.model.Huesped;
import a.delete.repository.IGenericRepo;
import a.delete.repository.IHuespedRepo;
import a.delete.service.IHuespedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl extends CRUDimpl<Huesped,Integer> implements IHuespedService {

    private final IHuespedRepo repo;
    @Override
    protected IGenericRepo<Huesped, Integer> repo() {
        return repo;
    }

    @Override
    public Page<Huesped> listPage(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
