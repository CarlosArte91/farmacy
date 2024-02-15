package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SuppliersDao {
    // Instanciar la conexión con SQL
    ConnectionMySQL cn = new ConnectionMySQL();
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Método para registrar proveedores
    public boolean createSupplier(Suppliers supplier) {
        String query = "INSERT INTO suppliers (name, description, telephone, address, email, city, created, updated) VALUES (?,?,?,?,?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getTelephone());
            pst.setString(4, supplier.getAddress());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear al nuevo proveedor " + e);
            return false;
        }
    }
    
    //Método para listar proveedores
    public List obtaindSuppliers(String searched) {
        String obtainedAll = "SELECT * FROM suppliers";
        String obtainedBySearched = "SELECT * FROM suppliers WHERE id LIKE '%" + searched + "%'";
        
        List<Suppliers> suppliers = new ArrayList();
        
        try {
            conn = cn.getConnection();
            
            if (searched.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(obtainedAll);
            } else {
                pst = conn.prepareStatement(obtainedBySearched);
            }
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Suppliers supplier = new Suppliers();
                
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setDescription(rs.getString("description"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setAddress(rs.getString("address"));
                supplier.setEmail(rs.getString("email"));
                supplier.setCity(rs.getString("city"));
                
                suppliers.add(supplier);
            }            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar los proveedores " + e);
        }
        
        return suppliers;
    }
    
    //Método para actualizar un proveedor
    public boolean updateSupplier(Suppliers supplier) {
        String query = "UPDATE suppliers SET name = ?, description = ?, telephone = ?, address = ?, email = ?, city = ?, updated = ? WHERE id = ?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getTelephone());
            pst.setString(4, supplier.getAddress());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar al proveedor " + e);
            return false;
        }
    }
    
    //Método para eliminar un proveedor
    public boolean deleteSupplier(int id) {
        String query = "DELETE FROM suppliers WHERE id = ?";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al proveedor " + e);
            return false;
        }
    }
}
