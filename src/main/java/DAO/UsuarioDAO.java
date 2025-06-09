package DAO;

import Entities.Usuario; // Asegúrate de importar tu clase Usuario
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {
    void save(Usuario usuario) throws SQLException;
    Usuario findById(Long id) throws SQLException;
    List<Usuario> findAll() throws SQLException;
    void update(Usuario usuario) throws SQLException;
    void delete(Long id) throws SQLException;
}