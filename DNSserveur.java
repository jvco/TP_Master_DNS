package DNS;

import java.net.*;

public class DNSserveur 
{
	static String IP=null;
	public static int   port = 5300;
	public static int   udpLength = 128;

	public static void main (String[] args)throws Exception
	{
		try 
		{
			DNSheader header = new DNSheader (); // Header
			DNSquestion req_enc = new DNSquestion (); // Question (requete encod�e)
			DNSRR rr = new DNSRR (); // Reponse

			DatagramSocket socket = new DatagramSocket (port);
			while (true) 
			{// reception de requete
				// buffer
				byte[] r = new byte[udpLength];
				// paquet
				DatagramPacket recu = new DatagramPacket (r, udpLength);
				// reception
				socket.receive (recu);
				// adresse du client
				InetAddress clientAddr = recu.getAddress ();
				// port du client (pour la r�ponse)
				int clientPort = recu.getPort ();
				// taille du paquet re�u
				int len = recu.getLength ();
				System.out.println("Reception : addresse client = " + clientAddr.getHostName() + " port = " + clientPort + " longueur requete = " + len);
				// indice de lecture
				int position = 0; 
				// on recup�re les donn�es de la socket
				byte[] data = recu.getData(); 
				// Le header 
				System.out.println ("*** Header ***");
				// on decode
				header.decode(data, position);
				// on affiche
				header.affichage (); 
				position += DNSheader.length;
				// La requete 
				System.out.println ("*** Requete ***");
				// on decode
				req_enc.decode(data, position);
				// on affiche
				req_enc.affichage (); 
				position += req_enc.length;
				// constitution et envoi de la reponse			
				header.qr=1; // on passe le bit r�ponse � 1
				H_map h = new H_map(); // creer la une H_map avec les differents enregistrement
				// on encode le RR
				rr.encode(req_enc.qName,H_map.address(req_enc.qName),"0");
				// taille du RR encod�
				int test=DNSheader.length+req_enc.length+rr.rr.length;
				// nouveau buffer
				byte[] s = new byte[test];
				// remplissage du buffer avec le header
				for(int i=0; i<DNSheader.length; i++)
				{
					s[i] = header.b[i];
				}
				// remplissage du buffer avec la question
				test=DNSheader.length;
				req_enc.encode(req_enc.qName,1);
				for(int i=0; i<req_enc.length; i++)
				{
					s[(test+i)] = req_enc.b[i];
				}
				test+=req_enc.length;
				// remplissage du buffer avec la reponse
				for(int i=0; i<rr.rr.length; i++)
				{
					s[test+ i] = rr.rr[i];
				}
				// cr�ation du paquet
				DatagramPacket envoi = new DatagramPacket(s, s.length, clientAddr, clientPort);
				// envoi du paquet
				socket.send(envoi);
				System.out.println ("Requete envoy�e : "+envoi.getLength() + " octets.");
			}//fin du while
		}// fin du try
		catch (Exception e) 
		{ 
			
		}
	}// fin du main
}// fin de classe