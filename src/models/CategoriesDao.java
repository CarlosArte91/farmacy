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

public class CategoriesDao {
    // Instanciar la conexión con SQL
    ConnectionMySQL cn = new ConnectionMySQL();
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean createCategory(Categories category) {
        String query = "INSERT INTO categories (name, created, updated) VALUES (?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(3, datetime);
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear la categoría " + e);
            return false;
        }
    }
    
    public List readCategories(String categoryId) {
        List<Categories> categories = new ArrayList();
        
        String obtaindAllCategories = "SELECT * FROM categories";
        String obtaindCategoriesById = "SELECT * FROM categories WHERE id LIKE '%" + categoryId + "%'";
        
        try {
            conn = cn.getConnection();
            
            if (categoryId.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(obtaindAllCategories);
            } else {
                pst = conn.prepareStatement(obtaindCategoriesById);
            }
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Categories category = new Categories();
                
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                
                categories.add(category);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar las categorias " + e);
        }
        
        return categories;
    }
    
    public boolean updateCategory(Categories category) {
        String query = "UPDATE categories SET name = ?, updated = ? WHERE id = ?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, category.getId());
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la categoría " + e);
            return false;
        }
    }
    
    public boolean deleteCategory(int id) {
        String query = "DELETE FROM categories WHERE id = ?";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la categoría " + e);
            return false;
        }
    }
}
