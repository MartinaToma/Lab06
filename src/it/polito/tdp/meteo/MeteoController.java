package it.polito.tdp.meteo;

import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.bean.Citta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;

public class MeteoController {

	
	private Model model;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Month> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) { 
		Month m=boxMese.getValue();
		List<Citta> citta=model.trovaSequenza(m.getValue());
		for(Citta c:citta) {
			txtResult.appendText(c.getNome()+"\n");
			
		}

	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		Month m=boxMese.getValue();
		
		for(Citta c:model.getCitta()) {
			double d=model.getUmiditaMedia(m, c.getNome());
			txtResult.appendText(c+ " "+d+"\n");
		}
	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
	}
	
	
	public void setModel(Model model) {
		this.model=model;
		
			for(int mese = 1; mese <= 12 ; mese ++) {
				boxMese.getItems().add(Month.of(mese)) ;
				boxMese.setConverter(new StringConverter<Month>() {
					@Override
					public String toString(Month m) {
						return m.getDisplayName(TextStyle.FULL, Locale.ITALIAN) ;
					}

					@Override
					public Month fromString(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}
			});	
			
			
			
		}
		
	
}
}

