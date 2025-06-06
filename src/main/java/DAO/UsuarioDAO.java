package DAO;

import Entities.Usuario; // Asegúrate de importar tu clase Usuario
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {

    // Metodo para guardar un nuevo usuario o actualizar uno existente
    void save(Usuario usuario) throws SQLException;

    // Metodo para encontrar un usuario por su ID
    Usuario findById(Long id) throws SQLException;

    // Metodo para obtener todos los usuarios
    List<Usuario> findAll() throws SQLException;

}