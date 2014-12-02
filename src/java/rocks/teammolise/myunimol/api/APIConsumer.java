package rocks.teammolise.myunimol.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import rocks.teammolise.myunimol.webapp.configuration.ConfigurationManager;
import rocks.teammolise.myunimol.webapp.configuration.ConfigurationManagerHandler;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;

/**
 * Permette di consumare le API
 * @author simone
 */
public class APIConsumer {
	/**
	 * Chiama un servizio API che richiede solo username e password
	 * @param pAPI Nome dell'API da chiamare
	 * @param pParameters Mappa dei parametri
	 * 
	 * @return Oggetto JSON restituito dal server
	 * @throws UnirestException Errore di Unirest
	 * @throws IOException Errore nella lettura del file di configurazione
	 */
	public JSONObject consume(String pAPI, Map<String, Object> pParameters) throws UnirestException, IOException {
    	ConfigurationManager config = ConfigurationManagerHandler.getInstance();
    	
    	String apiUrl = config.getWebServicesRoot() + pAPI;
		String token = config.getToken();
        
        HttpRequestWithBody temp = Unirest.post(apiUrl).header("accept", "application/json");
        
        MultipartBody body = temp.field("token", token);
        for (String key : pParameters.keySet()) {
        	body = body.field(key, pParameters.get(key));
        }
        
        return new JSONObject(body.asString().getBody());
	}
	
	/**
	 * Chiama un servizio API che richiede solo username e password
	 * @param pAPI Nome dell'API da chiamare
	 * @param pUsername Username dell'utente
	 * @param pPassword Password dell'utente
	 * 
	 * @return Oggetto JSON restituito dal server 
	 * @throws UnirestException Errore di Unirest
	 * @throws IOException Errore nella lettura del file di configurazione
	 */
	public JSONObject consume(String pAPI, String pUsername, String pPassword) throws UnirestException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", pUsername);
		map.put("password", pPassword);
		
		return this.consume(pAPI, map);
	}
}
