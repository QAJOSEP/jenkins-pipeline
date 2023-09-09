package a.delete.controller;

import a.delete.dto.ReservaDTO;
import a.delete.model.Reserva;
import a.delete.service.impl.ReservaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaServiceImpl service;
    private final ModelMapper mapper;

    @GetMapping("/pages")
    public ResponseEntity<Page<ReservaDTO>> listPage(Pageable pageable){
        Page<ReservaDTO> page = service.listPage(pageable).map(p-> mapper.map(p, ReservaDTO.class));
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listAll(){
        List<ReservaDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> listId(@PathVariable Integer id){
        ReservaDTO list = this.convertToDto(service.findById(id));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ReservaDTO dto){
        Reserva obj = service.save(this.convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> update(@PathVariable Integer id,@RequestBody ReservaDTO dto){
        dto.setId(id);
        Reserva obj = service.update(id, this.convertToEntity(dto));
        return new ResponseEntity<>(this.convertToDto(obj),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // HATEOAS
    @GetMapping("/heateoas")
    public EntityModel<ReservaDTO> findByHateoas(@PathVariable Integer id){
        EntityModel<ReservaDTO> resource = EntityModel.of(this.convertToDto(service.findById(id)));

        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findByHateoas(id));
        WebMvcLinkBuilder link2 = WebMvcLinkBuilder.linkTo(methodOn(ReservaController.class).listAll());
        resource.add(link1.withRel("Reserva-info"));
        resource.add(link2.withRel("reserva-info"));
        return  resource;
    }

    // DTO
    public ReservaDTO convertToDto(Reserva obj){
        return mapper.map(obj, ReservaDTO.class);
    }

    public Reserva convertToEntity(ReservaDTO obj){
        return mapper.map(obj, Reserva.class);
    }

}
