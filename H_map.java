package DNS;

import java.io.IOException;
import java.util.HashMap;

public class H_map 
{
	public static HashMap <String,String> hm = new HashMap<String,String>();
	public static HashMap <String,String> hm_voisin=new HashMap<String,String>();
	
	H_map() // constructeur
	{
			remplissage_hm(); // remplissage
	} // fin 
	
	public static void remplissage_hm() // remplissage de la map
	{
		// nous gerons le domaine .fr
		hm.put("www.google.fr","10.105.0.1");
		hm.put("www.univ-avignon.fr","10.105.0.2");
		hm.put("www.yahoo.fr","10.105.0.3");
		hm.put("www.bing.fr","10.105.0.4");
		hm.put("www.free.fr","10.105.0.5");
		// notre voisin gere le domaine .org
		hm_voisin.put(".org", "193.57.216.31"); // on met un voisin
	}// fin
	
	public static boolean remove_hm(String adresse)
	{
		if(hm.containsKey(adresse)) // si le nom est dans la map on le supprime
		{
			hm.remove(adresse);
			return true;
		}else return false;
	}// fin 
	
	public static boolean remove_voisin(String adresse)
	{
		if(hm_voisin.containsKey(adresse)) // si le voisin est dans la map on le supprime
		{
			hm_voisin.remove(adresse);
			return true;
		}
		else
		{
			return false;
		}
	}// fin
	
	public static boolean add_hm(String adresse,String ip) throws IOException
	{
		if(!hm.containsKey(adresse)) // si le nom n'est pas dans la map on l'ajoute dans la map
		{
			hm.put(adresse, ip);
			return true;
		}
		return false;

	}// fin
	
	public static boolean add_voisin(String nom,String ip)
	{
		if(!hm_voisin.containsKey(nom)) // si le voisin n'est pas dans la map
		{
			hm_voisin.put(nom, ip);// rajout nom / ip
			return true;
		}
		else
		{
			return false;
		}
	}// fin
	
	public static String address(String adresse)
	{
		// buffer temporaire
		String adresseIP =null; 
		if(hm.containsKey(adresse))// si le nom est dans la map on renvoi l'ip
		{
			// renvoi de l'ip
			adresseIP = hm.get(adresse);
		}
		return adresseIP;
	}// fin
	
}// fin de classe