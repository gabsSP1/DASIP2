package metier.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import metier.modele.Adherent;
import metier.modele.Lieu;
import util.GeoTest;


public class ServiceTechnique {
	
    private final static String EMAIL_COLLECTIF = "collectif@collectif.org"; 																// l'email chargé des envois de message du service
    private final static GeoApiContext MON_CONTEXTE_GOOGLE_MAPS = new GeoApiContext().setApiKey("AIzaSyAhf3JleYpal9S-xouJYH8lf7Dvz5Y2Nko");	// clé API (services Google Maps)
    
    /**
     * Renvoie une String contenant le message formé à partir des différents paramètres (simulation)
     * @param destinataire : l'email du destinataire
	 * @param sujet : le sujet du message
	 * @param message : le corps du message
     */
    public static String envoyerEMail ( String destinataire, String sujet, String message ) {
        String msg  = "Expediteur : " + EMAIL_COLLECTIF + "\n"
                    + "Pour : " + destinataire + "\n"
                    + "Sujet : " + sujet + "\n"
                    + "Corps :\n" + message;
        return msg;
    }
    
	
    /**
     * Renvoie les coordonnées GPS associées à une adresse
     * @param adrese : l'adresse dont on souhaite récupérer les coordonnées
     * @return les coordonnées GPS
     */
    public static LatLng obtenirLatLongViaAdresse ( String adresse ) {
        return GeoTest.getLatLng(adresse);
    }

    /**
     * Renvoie la distance entre un lieu et un adhérent
     * @param a : l'adhérent
     * @param l : le lieu
     * @return la distance (en km)
     */
    public static double obtenirDistanceEntreAdherentEtLieu ( Adherent a, Lieu l )
    {
        LatLng coordsAdherent = new LatLng ( a.getLatitude ( ), a.getLongitude ( ) );
        LatLng coordsLieu = new LatLng ( l.getLatitude ( ), l.getLongitude ( ) );
           
        return GeoTest.getFlightDistanceInKm ( coordsAdherent, coordsLieu );
    }
}
