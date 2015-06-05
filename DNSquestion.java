package DNS;

import  java.io.*;

public class DNSquestion 
{
	byte[] b;
	String qName;
	int qType, qClass, length;

	public void decode (byte[] in, int pos) throws java.io.IOException
	{
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream (in, pos, in.length-pos));// buffer pour le flot de données de la socket
		DNSname dName = new DNSname ();// création du nom
		dName.decode (in, pos);// decodage du nom
		this.qName = dName.name; // ecriture du nom décodé
		dis.skip (dName.length); // longueur du nom
		this.qType = dis.readUnsignedShort (); // type dns
		this.qClass = dis.readUnsignedShort (); // classe dns 
		dis.close (); // fermeture du buffer
		this.length = dName.length + 4;
	} // fin du dencodage

	public void encode (String nom, int type)throws java.io.IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();// buffer de decodage
		DataOutputStream dos = new DataOutputStream (baos);
		DNSname dName = new DNSname ();// création du nom
		dName.encode (nom);// encodage du nom
		dos.write (dName.b);  // écriture du tableau de byte du nom
		dos.writeShort (type); // écriture du type
		dos.writeShort (1);  
		this.qName = nom; // on récupère le nom
		this.length = dName.length + 4; // on met a jour la longueur
		this.b = baos.toByteArray(); // on passe en byte
		dos.close (); // fermeture des buffer
		baos.close ();
	} // fin de l'encodage
	
	public void affichage() 
	{
		System.out.println(qName + " classe = " + qClass + " type = " + qType + " longueur = " + length);
	}

} // fin de classe
