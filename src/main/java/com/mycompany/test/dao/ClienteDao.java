/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.test.dao;

import com.mycompany.test.models.Client;
import com.mysql.cj.util.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edwardcastle
 */
public class ClienteDao {

    private static Connection con = null;

    public void connect() {
        String database = "java";
        String user = "root";
        String password = "Eduardo*123";
        String host = "localhost";
        String port = "3306";
        String driver = "com.mysql.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(conexionUrl, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addClient(Client cliente) {
        connect();
        try {
            String sql = "INSERT INTO `clientes` (`id`, `nombre`, `apellido`, `telefono`, `email`) "
                    + "VALUES (NULL, '" + cliente.getName() + "', '" + cliente.getLastName() + "', '" + cliente.getPhone() + "', '" + cliente.getEmail() + "')";
            Statement statement = con.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Client> listClient() {
        connect();
        List<Client> clienteList = new ArrayList<>();
        try {
            String sql = "Select * from `clientes`";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Client client = new Client();
                client.setId(result.getString("id"));
                client.setName(result.getString("nombre"));
                client.setLastName(result.getString("apellido"));
                client.setEmail(result.getString("email"));
                client.setPhone(result.getString("telefono"));
                clienteList.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clienteList;
    }

    public void deleteClient(String index) {
        connect();
        try {
            String sql = "DELETE FROM `clientes` WHERE `clientes`.`id` = " + index;
            Statement statement = con.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editClient(Client cliente) {
        connect();
        try {
            String sql = "UPDATE `clientes` SET "
                    + "`nombre` = '" + cliente.getName() + "', "
                    + "`apellido` = '" + cliente.getLastName() + "', "
                    + "`telefono` = '" + cliente.getPhone() + "', "
                    + "`email` = '" + cliente.getEmail() + "' "
                    + "WHERE `clientes`.`id` = " + cliente.getId() + ";";
            Statement statement = con.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(Client client) {
        if (StringUtils.isEmptyOrWhitespaceOnly(client.getId())) {
            System.out.println("1");
            addClient(client);
        } else {
            System.out.println("2");
            System.out.println(client.getId());
            editClient(client);
        }
    }
}
