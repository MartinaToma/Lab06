package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
	    final String sql="SELECT localita, data, umidita FROM situazione WHERE MONTH(DATA)=? and localita=? ";
	    
	    List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	    
	    

		return rilevamenti;
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {

		return 0.0;
	}

	public List<Citta> getAllCitta() {
		
		
		final String sql = "SELECT DISTINCT localita FROM SITUAZIONE";	
		List<Citta> citta=new LinkedList<Citta>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
			 		citta.add(new Citta(rs.getString("localita")))	;
				
			}
			

			conn.close();
			
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}	
		
		for(Citta c:citta) {
			System.out.println(c);
		}
		return citta;
	}

	public double getUmidita(Month mese, String citta) {
		
		final String sql="SELECT AVG(umidita) AS U FROM SITUAZIONE WHERE LOCALITA=? AND MONTH(DATA)=? ";
		double media=0.0;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, citta);
			st.setInt(2, mese.getValue());

			
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
			  media=rs.getDouble("U");
				
			}
			

			conn.close();
			
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}	
		
		
		
		return media;
	}

}
