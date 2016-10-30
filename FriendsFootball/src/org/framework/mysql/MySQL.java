/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.framework.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.football.manager.LoginController;

/**
 *
 * @author Luis Rocha
 */
public class MySQL {

    private final String database = "Development";
    private final String username = "dev";
    private final String password = "development";

    private Connection conection;
    private Statement statment;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private boolean connected = false;
    private long id = -1;

    public class ReturnData {

        public boolean result;
        public long id;

        public ReturnData(boolean result, long id) {
            this.result = result;
            this.id = id;
        }
    }

    public MySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conection = DriverManager.getConnection("jdbc:mysql://lrocha3.no-ip.org:3306/" + this.database, this.username, this.password);
            statment = conection.createStatement();
            connected = true;

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex);
        }

    }

    public long GetID() {
        return id;
    }

    /*
    For methods that return data.
    Ex: INSERT INTO
     */
 /*
    For queries already created fully.
     */
    private void ExecuteUpdate(String Query) {
        if (!isConnected()) {
            return;
        }
        try {
            statment.executeUpdate(Query);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    /*
     For queries with sintax: INSERT INTO table VALUES(?,?,?)")
     */
    private void PrepareStatement(String Query, boolean generateKeys) throws SQLException {
        if (!isConnected()) {
            return;
        }

        if (generateKeys) {
            preparedStatement = conection.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
        } else {
            preparedStatement = conection.prepareStatement(Query);
        }
    }

    private long ExecutePreparedUpdate() {
        if (!isConnected()) {
            return 0;
        }

        try {
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys(); // vai buscar todos os autoincrement. Usar apenas para tabelas com apenas um autoincrement.
            long id = 0;
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return 0;
        }

    }

    private boolean isConnected() {
        return connected;
    }

    /*
        Defined here the methods to be called
        by the org.football.manager.
        They must be public to be acessed.
     */
    public long InsertPreparedQuery() {
        try {
            String query;

            // INSERT
            query = "INSERT INTO Countries (Country) VALUES (?)";

            this.PrepareStatement(query, true);
            this.SetPreparedStatementArg(1, "Mozambique");
            return this.ExecutePreparedUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public void InsertQuery() {
        String query;

        // INSERT
        query = "INSERT INTO Countries (Country) VALUES ('Austria')";

        this.ExecuteUpdate(query);
    }

    /*
        Queries that return data.
     */
    public ResultSet ExecuteQuery(String Query) {
        if (!isConnected()) {
            return null;
        }

        try {

            resultSet = statment.executeQuery(Query);
            return resultSet;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }

    /*
        Prints the all the contents of resultset.
     */
    public void printResultSet(ResultSet rs) {

        if (!isConnected()) {
            return;
        }

        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String country = rs.getString("Country");

                System.out.println(id + " " + country);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    ///////////////////////////////////////////////////////
    /*
        New methods here. Need to be evaluated after.
     */
    ///////////////////////////////////////////////////////

    /*
        Prepared Statment Creation.
     */
    public void new_PrepareStatement(String Query) throws SQLException {
        if (!isConnected()) {
            return;
        }
        preparedStatement = conection.prepareStatement(Query);
    }

    /*
        Prepared Statment Creation. Specify that there will be returned in the end of the execution the autoincrement value.
     */
    public void new_PrepareStatement_ID(String Query) throws SQLException {
        if (!isConnected()) {
            return;
        }
        preparedStatement = conection.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
    }

    /*
        Set the arguments for the prepared Statement.
     */
    public void SetPreparedStatementArg(int index, int value) throws SQLException {
        if (!isConnected()) {
            return;
        }
        preparedStatement.setInt(index, value);
    }

    
    /*
        Set the arguments for the prepared Statement.
     */
    public void SetPreparedStatementArg(int index, long value) throws SQLException {
        if (!isConnected()) {
            return;
        }
        preparedStatement.setLong(index, value);
    }
    
    
    public void SetPreparedStatementArg(int index, String value) throws SQLException {
        if (!isConnected()) {
            return;
        }
        preparedStatement.setString(index, value);
    }

    /*
        For Queries that do not return data.
     */
    public void new_ExecutePreparedUpdate() {
        if (!isConnected()) {
            return;
        }

        try {
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    /*
        For Queries that do not return data but needs to return a Auto_Increment value generated.
        (In this case I am focring to be only a autoincrement value, if there is more it is used only one.
         This is because it will be only used when creating a user and I need the ID generated, nothing more)
     */
    public long new_ExecutePreparedUpdateID() {
        if (!isConnected()) {
            return 0;
        }

        try {
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys(); // vai buscar todos os autoincrement. Usar apenas para tabelas com apenas um autoincrement.
            long id = 0;
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return 0;
        }

    }

    public ResultSet new_ExecutePreparedQuery() {
        if (!isConnected()) {
            return null;
        }

        try {

            resultSet = preparedStatement.executeQuery();
            return resultSet;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }

    
    
    public ResultSet userInformationQuery(String username){
         
        String query = "SELECT * FROM users INNER JOIN players ON players.users_id_player = users.id_player WHERE username = ?";

        try {
            this.new_PrepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

        try {
            this.SetPreparedStatementArg(1, username);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
        
        return this.new_ExecutePreparedQuery();
    }
    
    
    /*
        Prints the all the contents of resultset.
     */
    public long new_getCount(ResultSet rs) { // if 1 means the user exists

        if (!isConnected()) {
            return 0;
        }

        try {
            if (rs.next()) {
                System.out.println(rs.getLong(1));
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public boolean createNewUser(String username, String password) {

        String query = "INSERT INTO users (username, password) VALUES(?,?)";

        try {
            this.new_PrepareStatement_ID(query);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

        try {
            this.SetPreparedStatementArg(1, username);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

        try {
            this.SetPreparedStatementArg(2, password);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        this.id = this.new_ExecutePreparedUpdateID();

        // falta codigo para os outros parametros
        return true;

    }

    public boolean createNewUserInformation(String firstName, String surname, String birthdate, String email, String fieldPosition, String telephone, long id) {

        String query = "INSERT INTO players (first_name, surname, birthdate, email, fieldposition, telephone, users_id_player) VALUES(?,?,?,?,?,?,?);";

        try {
            this.new_PrepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

        try {
            this.SetPreparedStatementArg(1, firstName);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

        try {
            this.SetPreparedStatementArg(2, surname);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            this.SetPreparedStatementArg(3, birthdate);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            this.SetPreparedStatementArg(4, email);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            this.SetPreparedStatementArg(5, fieldPosition);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            this.SetPreparedStatementArg(6, telephone);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            this.SetPreparedStatementArg(7, id);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        this.new_ExecutePreparedUpdate();

        // falta codigo para os outros parametros
        return true;

    }

    public boolean verifyUniqueUsername(String username) {

        String query = "SELECT COUNT(*) FROM users WHERE username = ?";

        try {
            this.new_PrepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

        try {
            this.SetPreparedStatementArg(1, username);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

        ResultSet rs = this.new_ExecutePreparedQuery();

        try {
            if (rs.next()) {
                if (rs.getLong(1) == 1) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

}
