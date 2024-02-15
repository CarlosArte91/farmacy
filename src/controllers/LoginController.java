package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import views.LoginView;
import views.SystemView;

public class LoginController implements ActionListener {
    private Employees employee;
    private EmployeesDao employeesDao;
    private LoginView login_view;

    public LoginController(Employees employee, EmployeesDao employeesDao, LoginView login_view) {
        this.employee = employee;
        this.employeesDao = employeesDao;
        this.login_view = login_view;
        this.login_view.btn_login.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = login_view.txt_user.getText().trim();
        String password = String.valueOf(login_view.txt_password.getPassword());
        
        if (e.getSource() == login_view.btn_login) {
            // Validar que los campos no estén vacios
            if (!user.equals("") && !password.equals("")) {
                employee = employeesDao.loginQuery(user, password);
                
                if (employee.getUserName() != null) {
                    if (employee.getRol().equals("Administrador")) {
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                    } else {
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                    }
                    this.login_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    login_view.txt_user.setText("");
                    login_view.txt_password.setText("");
                    login_view.txt_user.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos están vacios");  
            }
        }
    }
    
    
}
