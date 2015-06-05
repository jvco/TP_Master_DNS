package DNS;

import  java.io.*;

public class DNSheader {

	static final int  length = 12;
	byte[]            b = new byte[length];
	int               id, qr, opCode, aa, tc, rd, ra, rCode;
	int               qdcount, ancount, nscount, arcount;

	public void decode (byte[] in, int pos)throws  java.io.IOException
	{
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream (in, pos, in.length-pos));
		id = dis.readUnsignedShort ();
		int flag = dis.readUnsignedShort ();
		rCode = flag & 0x0f;
		ra = (flag >> 7) & 1;
		rd = (flag >> 8) & 1;
		tc = (flag >> 9) & 1;
		aa = (flag >> 10) & 1;
		opCode = (flag >> 11) & 0x0f;
		qr = (flag >> 15) & 1;
		qdcount = dis.readUnsignedShort ();
		ancount = dis.readUnsignedShort ();
		nscount = dis.readUnsignedShort ();
		arcount = dis.readUnsignedShort ();
		dis.close ();
		b = (new String (in, pos, DNSheader.length)).getBytes();
	}

	public void encode (int id)throws java.io.IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		DataOutputStream dos = new DataOutputStream (baos);

		dos.writeShort (id);
		dos.writeShort (0x0100);
		dos.writeShort (1);
		for (int i=0; i<3; i++)
		{
			dos.writeShort (0);
		}
		b = baos.toByteArray();
		dos.close ();
		baos.close ();
	}

	public void affichage () 
	{
		System.out.println ("id = " + id +" opcode = " + opCode +" qr = " + qr +" aa = " + aa +" tc = " + tc +" RECURSIONDESIRED = " + rd +" RECURSIONAVIABLE = " + ra +" rcode = " + rCode +" QDCOUNT = " + qdcount +" ANCOUNT = " + ancount +" NSCOUNT = " + nscount +" ARCOUNT = " + arcount);
	}

} // end of class