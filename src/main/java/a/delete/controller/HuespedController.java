package a.delete.controller;

import a.delete.dto.HuespedDTO;
import a.delete.model.Huesped;
import a.delete.service.impl.HuespedServiceImpl;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/huespedes")
@RequiredArgsConstructor
public class HuespedController {

    private final HuespedServiceImpl service;
    private final ModelMapper mapper;

    @GetMapping("/pages")
    public ResponseEntity<Page<HuespedDTO>> listPage(Pageable pageable){
        Page<HuespedDTO> page = service.listPage(pageable).map(p-> mapper.map(p, HuespedDTO.class));
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HuespedDTO>> listAll(){
        List<HuespedDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HuespedDTO> listId(@PathVariable Integer id){
        HuespedDTO list = this.convertToDto(service.findById(id));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody HuespedDTO dto){
        Huesped obj = service.save(this.convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HuespedDTO> update(@PathVariable Integer id,@RequestBody HuespedDTO dto){
        dto.setId(id);
        Huesped obj = service.update(id, this.convertToEntity(dto));
        return new ResponseEntity<>(this.convertToDto(obj),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // HATEOAS
    @GetMapping("/heateoas")
    public EntityModel<HuespedDTO> findByHateoas(@PathVariable Integer id){
        EntityModel<HuespedDTO> resource = EntityModel.of(this.convertToDto(service.findById(id)));

        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).findByHateoas(id));
        WebMvcLinkBuilder link2 = WebMvcLinkBuilder.linkTo(methodOn(ReservaController.class).listAll());
        resource.add(link1.withRel("huesped-info"));
        resource.add(link2.withRel("reserva-info"));
        return  resource;
    }

    // DTO
    public HuespedDTO convertToDto(Huesped obj){
        return mapper.map(obj, HuespedDTO.class);
    }

    public Huesped convertToEntity(HuespedDTO obj){
        return mapper.map(obj, Huesped.class);
    }

}
