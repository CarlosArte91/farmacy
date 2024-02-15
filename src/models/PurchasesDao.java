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

public class PurchasesDao {
    // Instanciar la conexión con SQL
    ConnectionMySQL cn = new ConnectionMySQL();
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public boolean createPurchase(int supplier_id, int employee_id, double total) {
        String query = "INSERT INTO purchases (total, created, supplier_id, employee_id) VALUES (?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setDouble(1, total);
            pst.setTimestamp(2, datetime);
            pst.setInt(3, supplier_id);
            pst.setInt(4, employee_id);
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear la compra " + e);
            return false;
        }
    }
    
    public boolean createPurchaseDetail(
        int purchase_id,
        double purchase_price,
        int purchase_amount,
        double purchase_subtotal,
        int product_id
    ) {
        String query = "INSERT INTO purchase_details (purchase_price, purchase_amount, purchase_subtotal, purchase_id, "
                + "product_id) VALUES (?,?,?,?,?)";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setDouble(1, purchase_price);
            pst.setInt(2, purchase_amount);
            pst.setDouble(3, purchase_subtotal);
            pst.setInt(4, purchase_id);
            pst.setInt(5, product_id);
            
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el detalle de la compra " + e);
            return false;
        }
    }
    
    public int purchaseId() {
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM purchases";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return id;
    }
    
    public List obtaindAllPurchases() {
        List<Purchases> purchases = new ArrayList();
        
        String query = "SELECT pu.*, su.name AS supplier_name FROM purcharses pu, suppliers su "
                + "WHERE pu.supplier_id = su.id ORDER_BY pu.id ASC";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Purchases purchase = new Purchases();
                
                purchase.setId(rs.getInt("id"));
                purchase.setSupplier_name_product(rs.getString("supplier_name"));
                purchase.setTotal(rs.getDouble("total"));
                purchase.setCreated(rs.getString("created"));
                
                purchases.add(purchase);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return purchases;
    }
}
