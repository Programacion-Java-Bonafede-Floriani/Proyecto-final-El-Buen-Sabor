package DAO;

import Entities.Usuario;
import Enums.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Connection conexion;

    public UsuarioDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void save(Usuario usuario) throws SQLException {
        String sql;
        // Si el usuario ya tiene un ID, intentamos actualizarlo
        if (usuario.getId() != null && usuario.getId() > 0) {
            sql = "UPDATE usuarios SET auth_id = ?, username = ?, rol = ? WHERE id = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setString(1, usuario.getAuthId());
                pstmt.setString(2, usuario.getUsername());
                pstmt.setString(3, usuario.getRol().name());
                pstmt.setLong(4, usuario.getId());
                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("✅ Usuario '" + usuario.getUsername() + "' actualizado con ID: " + usuario.getId());
                } else {
                    System.out.println("❌ Usuario '" + usuario.getUsername() + "' no encontrado para actualizar (ID: " + usuario.getId() + ")");
                }
            }
        } else { // Si no tiene ID, es un nuevo usuario, lo insertamos
            sql = "INSERT INTO usuarios (auth_id, username, rol) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, usuario.getAuthId());
                pstmt.setString(2, usuario.getUsername());
                pstmt.setString(3, usuario.getRol().name());

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            usuario.setId(rs.getLong(1));
                            System.out.println("✅ Usuario '" + usuario.getUsername() + "' insertado con ID: " + usuario.getId());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Usuario findById(Long id) throws SQLException {
        String sql = "SELECT id, auth_id, username, rol FROM usuarios WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Usuario.builder()
                            .id(rs.getLong("id"))
                            .authId(rs.getString("auth_id"))
                            .username(rs.getString("username"))
                            .rol(Rol.valueOf(rs.getString("rol")))
                            .build();
                }
            }
        }
        return null;
    }


    @Override
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, auth_id, username, rol FROM usuarios";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = Usuario.builder()
                        .id(rs.getLong("id"))
                        .authId(rs.getString("auth_id"))
                        .username(rs.getString("username"))
                        .rol(Rol.valueOf(rs.getString("rol"))) // Convertimos el String a Enum
                        .build();
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public void update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET auth_id = ?, username = ?, rol = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getAuthId());
            pstmt.setString(2, usuario.getUsername());
            pstmt.setString(3, usuario.getRol().name());
            pstmt.setLong(4, usuario.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}