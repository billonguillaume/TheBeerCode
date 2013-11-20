package com.fjbg.thebeercode.model;
import java.sql.*;



public class PersonneDB extends Personne implements CRUD{
    
    public static Connection dbConnect = null;
    
    public PersonneDB() {
    }
    
    public PersonneDB(int idPersonne, String login, String mdp, String mail, String pays) {
        super(idPersonne, login, mdp, mail, pays);
    }
    
    public static void setDbConnect(Connection dbConnect) {
        PersonneDB.dbConnect = dbConnect;
    }

    @Override
    public void create() throws Exception {
        CallableStatement cstmt = null;
        try {
            String req = "call createPersonne(?,?,?,?,?)";
            cstmt = dbConnect.prepareCall(req);
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER); 
            cstmt.setString(2, login);
            cstmt.setString(3, mdp);
            cstmt.setString(4, mail);
            cstmt.setString(5, pays);
            cstmt.executeUpdate();
            this.idPersonne = cstmt.getInt(1);
        } catch (Exception e) {
            throw new Exception("Erreur de cr�ation " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void read() throws Exception {
        String req = "{?=call readPersonne(?)}";

        CallableStatement cstmt = null;
        try {
            cstmt = dbConnect.prepareCall(req);
            cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cstmt.setInt(2, idPersonne);
            cstmt.executeQuery();
            ResultSet rs = (ResultSet) cstmt.getObject(1);
            if (rs.next()) {
                this.idPersonne = rs.getInt("IDPERSONNE");
                this.login = rs.getString("LOGIN");
                this.mdp = rs.getString("MDP");
                this.mail = rs.getString("MAIL");
                this.pays = rs.getString("PAYS");
            } else {
                throw new Exception("identifiant de la personne inconnu");
            }
        } catch (Exception e) {
            throw new Exception("Erreur de lecture " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void update() throws Exception {
        CallableStatement cstmt = null;

        try {
            String req = "call updatePersonne(?,?,?,?,?)";
            cstmt = dbConnect.prepareCall(req);
            cstmt.setInt(1, idPersonne);
            cstmt.setString(2, login);
            cstmt.setString(3, mdp);
            cstmt.setString(4, mail);
            cstmt.setString(5, pays);
            cstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erreur de mise � jour " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void delete() throws Exception {
        CallableStatement cstmt = null;
        try {
            String req = "call delPersonne(?)";
            cstmt = dbConnect.prepareCall(req);
            cstmt.setInt(1, idPersonne);
            cstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erreur d'effacement " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }
    
}