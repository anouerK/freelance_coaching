/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package first_sprint_crud.services;

import first_sprint_crud.entities.Reponse;
import first_sprint_crud.entities.Reclamation;
import first_sprint_crud.util.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author USER
 */
public class ReponseService {
       Connection cnx;

    public ReponseService() {
        cnx = MyDB.getInstance().getConnection();
    }
    
     public void ajouter(Reponse r) {
        try {
   String req = "insert into reponse(reclamation_id,reponse)"
   + "values( '" + r.getRec().getId()+ "',   '" + r.getReponse()+  "')";

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Reponse Added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
        
    }
     
         public void supprimer(int id) {
 try {
            String req = "delete from reponse where id=" + " '"+ id + "' ";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.executeUpdate();


            System.out.println("Reponse deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
         
         
         public Reponse recupererReclamationAvecReponse(int idF) {
    Reponse reponse = null;
    try {
        String sql = "SELECT r.id, r.date_rec, r.sujet, r.description, rp.id as idrep, rp.reponse " +
                     "FROM reclamation r " +
                     "INNER JOIN reponse rp ON r.id = rp.reclamation_id " +
                     "WHERE r.id = " + " '"+ idF + "'";

        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            int recId = rs.getInt("id");
            String dateRec = rs.getString("date_rec");
            String sujet = rs.getString("sujet");
            String description = rs.getString("description");
            
             int repId = rs.getInt("idrep");
             String reponseS = rs.getString("reponse");

            Reclamation reclamation = new Reclamation(recId, dateRec, sujet, description);
            
            reponse =  new Reponse(repId,reclamation,reponseS);
        }

    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération de la réclamation : " + ex.getMessage());
    }

    return reponse;
}

}
