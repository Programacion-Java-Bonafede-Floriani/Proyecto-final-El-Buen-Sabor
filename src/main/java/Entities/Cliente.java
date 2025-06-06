package Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.sql.RowSet;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList; // Importar ArrayList

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Cliente extends Base{
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDate fechaNacimiento;
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();
    @Builder.Default
    private List<Domicilio> domicilios = new ArrayList<>();
    private Imagen imagen;
    private Usuario usuario;


    public void agregarDomicilio(Domicilio domicilio) {
        domicilios.add(domicilio);
    }


}