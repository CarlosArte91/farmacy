package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import models.Products;
import models.ProductsDao;
import views.SystemView;

public class ProductsController implements ActionListener, MouseListener, KeyListener {
    
    private Products product;
    private ProductsDao productDao;
    private SystemView view;
    
    public ProductsController(Products product, ProductsDao productDao, SystemView view) {
        this.product = product;
        this.productDao = productDao;
        this.view = view;
        
        // Escuchador de eventos
        // Botones
        this.view.btn_register_product.addActionListener(this);
        this.view.btn_update_product.addActionListener(this);
        this.view.btn_delete_product.addActionListener(this);
        this.view.btn_cancel_product.addActionListener(this);
        
        // Buscador de la tabla
        this.view.txt_search_product.addKeyListener(this);
        
        // Tabla
        this.view.products_table.addMouseListener(this);
    }

    // Eventos de los botones
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == view.btn_register_product) {
            System.out.println("botón registrar producto");
        } else if (event.getSource() == view.btn_update_product) {
            System.out.println("botón editar producto");
        } else if (event.getSource() == view.btn_delete_product) {
            System.out.println("botón eliminar producto");
        } else if (event.getSource() == view.btn_cancel_product) {
            System.out.println("botón cancelar producto");
        }
    }

    // Eventos del mouse
    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getSource() == view.products_table) {
            System.out.println("Click en la tabla de productos");
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

    // Eventos de teclas
    @Override
    public void keyTyped(KeyEvent event) {}

    @Override
    public void keyPressed(KeyEvent event) {}

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getSource() == view.txt_search_product) {
            System.out.println("buscando un producto");
        }
    }
}
