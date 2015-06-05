package DNS;

import  java.net.*;

public class DNSclient {

	public static final int   port = 5300;
	public static final int   udpLength = 128;

	public static void main (String[] args)throws Exception
	{
		int         id = 10000;	// ID de la question par défaut
		int         type = 1;       // Type de requete par défaut A (le seul que nous implémentons)
		String      serveur = null;  // Nom du serveur
		String      query = null; // Requete
		DNSheader   header = new DNSheader (); // Header
		DNSquestion req_enc = new DNSquestion (); // Question (requete encodée)
		DNSRR       rr = new DNSRR (); // Reponse 

		// Paramètres (récupération des arguments) :
		for (int i=0; i<args.length; i++) 
		{
			if (args[i].startsWith("-s")) serveur = args[++i];
			else if (args[i].startsWith("-t"))type = Integer.parseInt (args[++i]);
			else if (args[i].startsWith("-id"))id = Integer.parseInt (args[++i]);
			else { query = args[i];	}
		}
		// Creation socket
		InetAddress		adresse_serv = InetAddress.getByName (serveur);
		DatagramSocket	socket = new DatagramSocket ();
		// Encodage et envoi de la requete
		header.encode (id);// encodage du header
		req_enc.encode (query, type);// encodage de la question à partir du nom (argument) et du type
		byte[] s = ( (new String(header.b)) + (new String(req_enc.b)) ).getBytes();// buffer
		DatagramPacket envoi = new DatagramPacket(s, s.length, adresse_serv, port);// création du paqeut
		socket.send (envoi);// envoi du paquet
		System.out.println ("Requete envoyée : "+envoi.getLength() + " octets.");		
		// Reception de la reponse et decodage
		byte[] r = new byte[udpLength];// buffer
		DatagramPacket recu = new DatagramPacket (r, udpLength);// création du paquet 
		socket.receive (recu);// reception du paquet
		System.out.println ("Reponse reçu : " + recu.getLength() + " octets.");
		int pos = 0; // indice de lecture
		byte[] data = recu.getData (); // on recupère les données de la socket
		System.out.println ("*** Header ***");		// Le header 
		header.decode (data, pos);// on decode
		header.affichage (); // on affiche
		pos += DNSheader.length;
		System.out.println ("*** Requete ***");// La requete 
		req_enc.decode (data, pos);// on decode
		req_enc.affichage (); // on affiche
		pos += req_enc.length;
		System.out.println ("*** Reponse ***");// La reponse 
		rr.decode (data, pos);// on decode
		rr.affichage (); // on affiche
		pos += rr.length;
		System.exit (0);
	}// fin du main
}// fin de la classe