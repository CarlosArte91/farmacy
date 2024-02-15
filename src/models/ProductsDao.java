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

public class ProductsDao {
    // Instanciar la conexión con SQL
    ConnectionMySQL cn = new ConnectionMySQL();
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean createProduct(Products product) {
        String query = "INSERT INTO products (code, name, description, unit_price, product_quantity, created, updated, "
                + "category_id) VALUES (?,?,?,?,?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnitPrice());
            pst.setInt(5, product.getQuantity());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.setInt(8, product.getCategoryId());
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el producto " + e);
            return false;
        }
    }
    
    public List readProducts(String productName) {
        List<Products> products = new ArrayList();
        
        String obtainAllProducts = "SELECT p.*, c.name AS category_name SELECT p.*, c.name AS category_name "
                + "WHERE p.category_id = c.id";
        
        String obtainProductsByName = "SELECT p.*, c.name AS category_name FROM productos p INNER JOIN categories c "
                + "ON p.category_id = c.id WHERE p.name LIKE '%" + productName + "%'";
        
        try {
            conn = cn.getConnection();
            
            if (productName.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(obtainAllProducts);
            } else {
                pst = conn.prepareStatement(obtainProductsByName);
            }
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Products product = new Products();
                
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnitPrice(rs.getDouble("unit_price"));
                product.setQuantity(rs.getInt("product_quantity"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setCategoryName(rs.getString("category_name"));
                
                products.add(product);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer los pruductos " + e);
        }
        return products;
    }
    
    public boolean updateProduct(Products product) {
        String query = "UPDATE products SET name = ?, description = ?, unit_price = ?, product_quantity = ?, "
                + "updated = ?, category_id = ? WHERE code = ?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, product.getName());
            pst.setString(2, product.getDescription());
            pst.setDouble(3, product.getUnitPrice());
            pst.setInt(4, product.getQuantity());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product.getCategoryId());
            pst.setInt(7, product.getCode());
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto " + e);
            return false;
        }
    }
    
    public boolean deleteProduct(int code) {
        String query = "DELETE FROM products WHERE code = ?";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);            
            pst.setInt(1, code);
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto " + e);
            return false;
        }
    }
    
    public Products searchByCode(int code) {
        String query = "SELECT p.*, c.name AS category_name FROM products p INNER JOIN categories c ON "
                + "p.category_id = c.id WHERE p.code = ?";
        
        Products product = new Products();
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, code);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnitPrice(rs.getDouble("unit_price"));
                product.setQuantity(rs.getInt("product_quantity"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setCategoryName(rs.getString("category_name"));
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el producto " + e);
        }
        
        return product;
    }
    
    public int quantityByProductId(int code) {
        String query = "SELECT p.product_quantity AS quantity FROM products p WHERE p.code = ?";
        
        int quantity = 0;
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, code);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return quantity;
    }
    
    public boolean updateQuantity(int newQuantity, int code) {
        String query = "UPDATE products SET product_quantity = ? WHERE code = ?";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, newQuantity);
            pst.setInt(2, code);
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
}
