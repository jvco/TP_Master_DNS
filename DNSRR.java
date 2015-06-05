package DNS;

import  java.io.*;
import java.util.StringTokenizer;
public class DNSRR 
{
	byte[] b,rr,b_fqdn;
	String nom=null,ip=null;
	int Type, classe, TTL, length;
	int taille=4;

	public void decode (byte[] in, int pos)throws java.io.IOException
	{
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream (in, pos, in.length-pos));// buffer pour récupérer les données de la socket
		System.out.println ("*** debug 1 ***");
		DNSname dName = new DNSname ();// création d'un objet nom DNS
		System.out.println ("*** debug 2 ***");
		dName.decode (in, pos);// decodage du nom recu dans la requete
		this.nom = dName.name;// on recupere ainsi le nom décodé	
		dis.skip (dName.length);// on dépasse le nom
		System.out.println ("*** debug 3 ***");
		// on récupère ce qu'il y a apres le nom		 
		this.Type = dis.readUnsignedShort ();// on récupère le type		
		this.classe = dis.readUnsignedShort ();// on récupère la classe
		System.out.println ("*** debug 4 ***");
		this.TTL = dis.readUnsignedShort()*65536 +dis.readUnsignedShort();// on récupère le TTL		
		this.taille = dis.readUnsignedShort();// on récupère la taille		
		System.out.println ("*** debug 5 ***");
		this.length = dName.length + 15;// longueur du nom 		 
		if (Type==1) // si le type est 1
		{
			// on rajoute les points dans l'adresse ip décodée
			this.ip = "";
			for (int i=0; i<4; i++) // 4 octets dans une adresse ip 
			{
				this.ip += dis.readUnsignedByte ();
				this.ip+=".";
			}
			this.ip = this.ip.substring (0, this.ip.length()-1);
		}
		else // sinon type inconnu
		{	
			this.ip = "Type de requete inconnu";
		}
		this.length += this.taille;
		// on encode
		this.b = (new String (in, pos, this.length)).getBytes();
		dis.close ();
	}
	
	public void encode (String nom, String message,String ttl)throws java.io.IOException
	{
		b=new byte[(message.length()+3)]; // buffer pour l'adresse ip
		rr=new byte[(b.length)+10]; // buffer general
		this.nom=nom; // on récupère le nom (utile pour l'affichage)
		this.ip=message; // on récupère l'ip (utile pour l'affichage) 
		int i=0,j=0; // indices
		// on enlève les points de l'adresse ip
		StringTokenizer point = new StringTokenizer(message,".");
		while(point.hasMoreTokens()) // tant qu'il y a des points
		{
			String temp = point.nextToken();
			b[j]=(byte)temp.length(); // on rempli ce qu'il y a entre les points
			for(i=0;i!=temp.length();i++) 
			{
				b[++j]=(byte)temp.charAt(i); // on rempli le tableau de byte 
			}
			j++;
		}
		rr=(nom+(new String(b))+classe+Type+ttl+taille).getBytes(); // on encode 
	}
	public void affichage() 
	{
		System.out.println("nom : " +this.nom + " = " + this.ip +" type = " + this.Type + " class = " + this.classe +" TTL = " + this.TTL + " taille = " + this.length);
	} 
}