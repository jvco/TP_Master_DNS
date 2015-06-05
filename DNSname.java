package DNS;

import  java.io.*;
import java.util.*;

public class DNSname 
{
	byte[] b; // buffer
	String name; // nom
	int length; // longueur

	public void decode (byte[] in, int position)throws java.io.IOException
	{
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream (in)); // buffer
		dis.skip (position);
		System.out.println ("*** debug 1 ***");
		name="";
		while (true) 
		{
			int c = dis.readUnsignedByte(); // octets restants
			System.out.println ("*** debug 2 ***");
			if (c == 0) {length += 1;break;}      
			else if (c > 63) 
			{
				int p = (c &0x3F)*256 + dis.readUnsignedByte();
				this.length += 2; 
				System.out.println ("*** debug 3 ***");
				DNSname dName = new DNSname (); // nouveau nom dns
				dName.decode (in, p); // decodage du nom
				this.name += dName.name + "."; // rajout des points au nom
				break; // on sort de la boucle
			}
			else 
			{                   
				System.out.println ("*** debug 4 ***");
				byte[] s = new byte[c]; // buffer
				dis.readFully (s); // lecture complete du buffer
				this.name += new String(s) + ".";// rajout des points
				this.length += c + 1; // longueur + 1 
			}
		}
		System.out.println ("*** debug 5 ***");
		dis.close ();// fermeture du buffer
		this.name = this.name.substring (0, this.name.length()-1); // on va de 0 jusqu'a longueur du nom -1 (le nom est censé finir par 0 pour indiquer sa fin)
		this.b = (new String (in, position, this.length)).getBytes(); // encodage en byte 
	}// fin decodage

	public void encode (String nom_dns)throws java.io.IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream (); // buffer
		DataOutputStream dos = new DataOutputStream (baos); 
		StringTokenizer st = new StringTokenizer (nom_dns, "."); // recherche des points
		while (st.hasMoreTokens()) // tant qu'il y a des points
		{
			String label = st.nextToken(); // on récupère le label entre 2 Points
			dos.writeByte (label.length()); // on ecrit la longueur entre les 2 Points
			dos.writeBytes (label); // on écrit le label
		}
		dos.writeByte (0); // fermeture du buffer
		this.b = baos.toByteArray (); // encodage en byte
		this.length = baos.size (); // on récupère la taille
		this.name = nom_dns; // on écrit le nom 
		dos.close (); // fermeture du buffer
		baos.close ();
	}// fin encodage
}// fin de classe