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

public class EmployeesDao {
    // Instanciar la conexión con SQL
    ConnectionMySQL cn = new ConnectionMySQL();
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    // Variables para enviar datos entre interfaces
    public static int id_user = 0;
    public static String full_name_user = "";
    public static String user_name_user = "";
    public static String address_user = "";
    public static String rol_user = "";
    public static String email_user = "";
    public static String telephone_user = "";
    
    // Método de Login
    public Employees loginQuery(String user, String password) {
        String query = "SELECT * FROM employees WHERE user_name = ? AND password = ?";
        
        Employees employee = new Employees();
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            // Enviar parametros
            pst.setString(1, user);
            pst.setString(2, password);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();
                
                employee.setFullName(rs.getString("full_name"));
                full_name_user = employee.getFullName();
                
                employee.setUserName(rs.getString("user_name"));
                user_name_user = employee.getUserName();
                
                employee.setAddress(rs.getString("address"));
                address_user = employee.getAddress();
                
                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();
                
                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail();
                
                employee.setTelephone(rs.getString("telephone"));
                telephone_user = employee.getTelephone();
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener al empleado" + e);
        }
        
        return employee;
    }
    
    // Método para registrar empleado
    public boolean registerEmployeeQuery(Employees employee) {
        String query = "INSERT INTO employees (id, full_name, user_name, address, telephone, email, password, rol, created, updated) VALUES (?,?,?,?,?,?,?,?,?,?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getFullName());
            pst.setString(3, employee.getUserName());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword());
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9, datetime);
            pst.setTimestamp(10, datetime);
            
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario" + e);
            return false;
        }
    }
    
    // Método para listar los empleados
    public List listEmployeesQuery(String value) {
        List<Employees> list_employees = new ArrayList();
        
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
        
        try {
            conn = cn.getConnection();
            
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
            } else {
                pst = conn.prepareStatement(query_search_employee);
            }
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Employees employee = new Employees();
                
                employee.setId(rs.getInt("id"));
                employee.setFullName(rs.getString("full_name"));
                employee.setUserName(rs.getString("user_name"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));
                
                list_employees.add(employee);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_employees;
    }
    
    // Método para actualizar empleado
    public boolean updateEmployeeQuery(Employees employee) {
        String query = "UPDATE employees SET full_name = ?, user_name = ?, address = ?, telephone = ?, email = ?, rol = ?, updated = ? WHERE id = ?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, employee.getFullName());
            pst.setString(2, employee.getUserName());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, employee.getId());
            
            pst.execute();
            
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar usuario" + e);
            return false;
        }
    }
    
    // Método para eliminar empleado
    public boolean deleteEmployeeQuery(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al usuario" + e);
            return false;
        }
    }
    
    // Método para actualizar contraseña del empleado
    public boolean updateEmployeePassword(Employees employee) {
        String query = "UPDATE employees SET password = ? WHERE user_name = '" + user_name_user + "'";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getPassword());
            pst.executeUpdate();
            return true;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la contraseña del usuario" + e);
            return false;
        }
    }
}
