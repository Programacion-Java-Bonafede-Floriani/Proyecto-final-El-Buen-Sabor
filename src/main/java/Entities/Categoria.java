package Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Categoria extends Base{
    private String denominacion;
    @Builder.Default
    private ArrayList<Articulo> articulos = new ArrayList<>();
    private Categoria categoria;
    @Builder.Default
    private Set<Categoria> subcategorias = new HashSet<>();
}