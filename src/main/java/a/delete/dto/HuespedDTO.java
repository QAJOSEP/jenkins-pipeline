package a.delete.dto;

import a.delete.model.Reserva;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class HuespedDTO {
    private int id;
    private String name;
    @OneToMany(mappedBy = "id")
    private List<Reserva> reservas;
}
