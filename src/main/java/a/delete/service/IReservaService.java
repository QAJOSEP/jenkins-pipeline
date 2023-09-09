package a.delete.service;

import a.delete.model.Huesped;
import a.delete.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReservaService extends ICRUD<Reserva,Integer>{
    // paginación
    Page<Reserva> listPage(Pageable pageable);
}
