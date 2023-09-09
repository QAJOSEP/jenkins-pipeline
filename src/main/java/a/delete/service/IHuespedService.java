package a.delete.service;

import a.delete.model.Huesped;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHuespedService extends ICRUD<Huesped,Integer>{

    // paginación
    Page<Huesped> listPage(Pageable pageable);
}
