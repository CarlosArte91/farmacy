package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.rol_user;
import views.SystemView;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {
    private Employees employee;
    private EmployeesDao employeesDao;
    private SystemView views;
    // Rol
    String rol = rol_user;
    
    DefaultTableModel model = new DefaultTableModel();

    public EmployeesController(Employees employee, EmployeesDao employeesDao, SystemView views) {
        this.employee = employee;
        this.employeesDao = employeesDao;
        this.views = views;
        
        // Botón de registrar empleado
        this.views.btn_employee_register.addActionListener(this);
        // Botón de actualizar empleado
        this.views.btn_employee_edit.addActionListener(this);
        // Botón de eliminar
        this.views.btn_employee_delete.addActionListener(this);
        // Botón cancelar
        this.views.btn_employee_cancel.addActionListener(this);
        // Botón de cambiar contraseña
        this.views.btn_profile_edit.addActionListener(this);
        
        // Label employees
        this.views.jLabelEmployees.addMouseListener(this);
        
        // Eventos
        this.views.employees_table.addMouseListener(this);
        this.views.txt_employee_search.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_employee_register) {
            // Verificar si los campos están vacios
            if (
                views.txt_employee_id.getText().equals("") ||
                views.txt_employee_name.getText().equals("") ||
                views.txt_employee_user.getText().equals("") ||
                views.txt_employee_address.getText().equals("") ||
                views.txt_employee_phone.getText().equals("") ||
                views.txt_employee_email.getText().equals("") ||
                views.cmb_employee_rol.getSelectedItem().toString().equals("") ||
                String.valueOf(views.txt_employee_password.getPassword()).equals("")
            ) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setFullName(views.txt_employee_name.getText().trim());
                employee.setUserName(views.txt_employee_user.getText().trim());
                employee.setAddress(views.txt_employee_address.getText().trim());
                employee.setTelephone(views.txt_employee_phone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setRol(views.cmb_employee_rol.getSelectedItem().toString().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                
                if (employeesDao.registerEmployeeQuery(employee)) {
                    cleanTable();
                    cleanForm();
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Usuario registrado con exito");   
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                }
            }
        } else if (e.getSource() == views.btn_employee_edit) {
            if (views.txt_employee_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado para continuar");
            } else {
                if (
                    views.txt_employee_name.getText().equals("") ||
                    views.cmb_employee_rol.getSelectedItem().toString().equals("")
                ) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                    employee.setFullName(views.txt_employee_name.getText().trim());
                    employee.setUserName(views.txt_employee_user.getText().trim());
                    employee.setAddress(views.txt_employee_address.getText().trim());
                    employee.setTelephone(views.txt_employee_phone.getText().trim());
                    employee.setEmail(views.txt_employee_email.getText().trim());
                    employee.setRol(views.cmb_employee_rol.getSelectedItem().toString().trim());
                    employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                    
                    if (employeesDao.updateEmployeeQuery(employee)) {
                        cleanTable();
                        cleanForm();
                        listAllEmployees();
                        JOptionPane.showMessageDialog(null, "Usuario actualizado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido actualizar el usuario");
                    }
                }
            }
        } else if (e.getSource() == views.btn_employee_delete) {
            int row = views.employees_table.getSelectedRow();
            
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado para continuar");
            } else {
                int id = Integer.parseInt(views.employees_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar al empleado?");
                
                if (question == 0) {
                    if (employeesDao.deleteEmployeeQuery(id)) {
                        cleanTable();
                        cleanForm();
                        listAllEmployees();
                        JOptionPane.showMessageDialog(null, "Empleado eliminado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar empleado");
                    }
                }                                
            }
        } else if (e.getSource() == views.btn_employee_cancel) {
            cleanForm();
            views.employees_table.clearSelection();
        } else if (e.getSource() == views.btn_profile_edit) {
            // Obtener los campos de la nueva contraseña
            String newPassword = String.valueOf(views.txt_profile_new_password.getPassword());
            String confirmNewPassword = String.valueOf(views.txt_profile_confirm_password.getPassword());
            
            // Validar que las cajas de texto no sean vacías
            if (!newPassword.equals("") && !confirmNewPassword.equals("")) {
                // Validar que sean iguales
                if (newPassword.equals(confirmNewPassword)) {
                    employee.setPassword(newPassword);
                    
                    if (employeesDao.updateEmployeePassword(employee) != false) {
                        JOptionPane.showMessageDialog(null, "Contraseña actualizada con exito");
                        views.txt_profile_new_password.setText("");
                        views.txt_profile_confirm_password.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ocurrio un error al actualizar la contraseña");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos no deben estar vacíos");
            }
        }
    }
    
    public void listAllEmployees() {
        if (rol.equals("Administrador")) {
            List<Employees> employeesList = employeesDao.listEmployeesQuery(views.txt_employee_search.getText());
            
            model = (DefaultTableModel) views.employees_table.getModel();
            Object[] row = new Object[7];
            
            for (int i = 0; i < employeesList.size(); i++) {
                row[0] = employeesList.get(i).getId();
                row[1] = employeesList.get(i).getFullName();
                row[2] = employeesList.get(i).getUserName();
                row[3] = employeesList.get(i).getAddress();
                row[4] = employeesList.get(i).getTelephone();
                row[5] = employeesList.get(i).getEmail();
                row[6] = employeesList.get(i).getRol();
                
                model.addRow(row);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.employees_table) {
            int row = views.employees_table.rowAtPoint(e.getPoint());
            
            views.txt_employee_id.setText(views.employees_table.getValueAt(row, 0).toString());
            views.txt_employee_name.setText(views.employees_table.getValueAt(row, 1).toString());
            views.txt_employee_user.setText(views.employees_table.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.employees_table.getValueAt(row, 3).toString());
            views.txt_employee_phone.setText(views.employees_table.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.employees_table.getValueAt(row, 5).toString());
            views.cmb_employee_rol.setSelectedItem(views.employees_table.getValueAt(row, 6).toString());
            
            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false);
            views.btn_employee_register.setEnabled(false);
        } else if (e.getSource() == views.jLabelEmployees) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(3);
                
            } else {
                views.jTabbedPane1.setEnabledAt(3, false);
                views.jLabelEmployees.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador");
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_employee_search) {
            cleanTable();
            listAllEmployees();
        }
    }
    
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i--;
        }
    }   
    
    public void cleanForm() {
        views.btn_employee_register.setEnabled(true);
        views.txt_employee_id.setEditable(true);
        views.txt_employee_password.setEnabled(true);
        
        views.txt_employee_id.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_email.setText("");
        views.txt_employee_name.setText("");
        views.txt_employee_password.setText("");
        views.txt_employee_phone.setText("");
        views.txt_employee_user.setText("");
        views.cmb_employee_rol.setSelectedItem("");
    }
}
