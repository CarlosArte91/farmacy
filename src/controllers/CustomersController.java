package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import models.Customers;
import models.CustomersDao;
import views.SystemView;

public class CustomersController implements ActionListener, MouseListener, KeyListener {

    private Customers customer;
    private CustomersDao customerDao;
    private SystemView view;

    public CustomersController(Customers customer, CustomersDao customerDao, SystemView view) {
        this.customer = customer;
        this.customerDao = customerDao;
        this.view = view;
        
        // Escuchadores de eventos.
        // Botones
        this.view.btn_customer_register.addActionListener(this);
        this.view.btn_customer_edit.addActionListener(this);
        this.view.btn_customer_delete.addActionListener(this);
        this.view.btn_customer_cancel.addActionListener(this);
        
        // Buscador de la tabla
        this.view.txt_customer_search.addKeyListener(this);
        
        // Tabla
        this.view.customer_table.addMouseListener(this);
    }

    // Eventos de botones
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == view.btn_customer_register) {
            System.out.println("botón registrar cliente");
        } else if (event.getSource() == view.btn_customer_edit) {
            System.out.println("botón editar cliente");
        } else if (event.getSource() == view.btn_customer_delete) {
            System.out.println("botón eliminar cliente");
        } else if (event.getSource() == view.btn_customer_cancel) {
            System.out.println("botón cancelar cliente");
        }
    }

    // Eventos de mouse
    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getSource() == view.customer_table) {
            System.out.println("Click en la tabla");
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}

    // Eventos de teclado
    @Override
    public void keyTyped(KeyEvent event) {}

    @Override
    public void keyPressed(KeyEvent event) {}

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getSource() == view.txt_customer_search) {
            System.out.println("En el buscador de cliente");
        }
    }
}
