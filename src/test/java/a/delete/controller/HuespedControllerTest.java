package a.delete.controller;

import a.delete.dto.HuespedDTO;
import a.delete.model.Huesped;
import a.delete.service.impl.HuespedServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HuespedController.class)
public class HuespedControllerTest {

    @Autowired
    // clase propia de Spring NO SE PUEDE SIMULAR necesitas la isntancia REAL
    private MockMvc mockMvc;


    // Simulación de la instancia IHuespedService
    @MockBean
    private HuespedServiceImpl service;

    @MockBean
    private ModelMapper modelMapper;

    Huesped HUESPED_1 = new Huesped(1,"José",new ArrayList<>());
    Huesped HUESPED_2 = new Huesped(1,"José",new ArrayList<>());
    Huesped HUESPED_3 = new Huesped(1,"José",new ArrayList<>());
    HuespedDTO HUESPEDDTO_1 = new HuespedDTO(1,"José",new ArrayList<>());
    HuespedDTO HUESPEDDTO_2 = new HuespedDTO(1,"José",new ArrayList<>());
    HuespedDTO HUESPEDDTO_3 = new HuespedDTO(1,"José",new ArrayList<>());


    @Test
    public void readAllTest() throws Exception{
        List<Huesped> huespedes = List.of(HUESPED_1,HUESPED_2,HUESPED_3);

        Mockito.when(service.findAll()).thenReturn(huespedes);

        Mockito.when(modelMapper.map(HUESPED_1, HuespedDTO.class)).thenReturn(HUESPEDDTO_1);
        Mockito.when(modelMapper.map(HUESPED_2, HuespedDTO.class)).thenReturn(HUESPEDDTO_2);
        Mockito.when(modelMapper.map(HUESPED_3, HuespedDTO.class)).thenReturn(HUESPEDDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/huespedes")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)));
    }
}
