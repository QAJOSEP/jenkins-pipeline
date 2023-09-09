package a.delete.service.impl;

import a.delete.model.Huesped;
import a.delete.model.Reserva;
import a.delete.repository.IGenericRepo;
import a.delete.repository.IReservaRepo;
import a.delete.service.IReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl extends CRUDimpl<Reserva,Integer> implements IReservaService {
    
    private final IReservaRepo repo; 
    @Override
    protected IGenericRepo<Reserva, Integer> repo() {
        return repo;
    }

    @Override
    public Page<Reserva> listPage(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
