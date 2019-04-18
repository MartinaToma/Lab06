package it.polito.tdp.meteo;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private List<Citta> citta;
	private List<Citta> best;
	MeteoDAO dao;

	public Model() {
		citta=new LinkedList<Citta>();
		dao=new MeteoDAO();
 
	}
	
	public List<Citta> getCitta(){
		 citta=dao.getAllCitta();
		 
		return citta;
	}
	

	public double getUmiditaMedia(Month mese,String citta) {
 
		
		return dao.getUmidita(mese,citta);
	}

	public List<Citta> trovaSequenza(int mese) {
		
		List<Citta> parziale=new LinkedList<Citta>();
		best=null;
		
		this.getCitta();
		for(Citta c:this.citta) {
			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
			
		}
		cerca(parziale,0);

		System.out.println(calcolaCosto(best));
		return best;
	}

	private void cerca(List<Citta> parziale, int livello) {
		if(livello==NUMERO_GIORNI_TOTALI ) {
			double cost=calcolaCosto(parziale);
			if(best==null||cost<calcolaCosto(best)) {
				
				best= new ArrayList<>(parziale);
				
			}
		}
		else {
			for(Citta c:this.citta) {
				if(isValid(c,parziale)) {
					parziale.add(c);
					cerca(parziale,livello+1);
					parziale.remove(parziale.size()-1);
				}
			}
		}
		
	}

	private boolean isValid(Citta c, List<Citta> parziale) {
		int conta=0;
		
		for(Citta precedente:parziale) {
			if(precedente.equals(c))
				conta++;
		}
		if(conta>=NUMERO_GIORNI_CITTA_MAX)
			return false;
		
		
		if(parziale.size()==0) {
			return true;
		}
		
		if(parziale.size()==1||parziale.size()==2) {
			return parziale.get(parziale.size()-1).equals(c);
		}
		
		
		if(parziale.get(parziale.size()-1).equals(c))
			return true;
		
		//cambio
		if(parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2))
				&&parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
			return true;
		
		return false;
	}

	private double calcolaCosto(List<Citta> parziale) {
		double cost=0.0;
		for(int giorno=1;giorno<=NUMERO_GIORNI_TOTALI;giorno++) {
			Citta c=parziale.get(giorno-1);
			double umid=c.getRilevamenti().get(giorno-1).getUmidita();
			cost+=umid;
		}
		
		for(int k=2;k<=NUMERO_GIORNI_TOTALI;k++) {
			if(!parziale.get(k-1).equals(parziale.get(k-2)))
				cost+=COST;
		}
		return cost;
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		return true;
	}

}
