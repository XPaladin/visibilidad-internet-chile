package src;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Vector;


public class Bot {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws UnknownHostException{
		DataMatrix lgs= new DataMatrix("data/telnetLG"," ");
		DataMatrix freeLGs=lgs.filter(4, "F");
		DataMatrix noUser=freeLGs.filter(5, "N");
		//System.out.println(freeLGs);
		Vector<String> v= noUser.selectColumn(1);
		Iterator<String> itr = v.iterator();
		Telnet con;
		long count=0;
		int lastASN;
		while(itr.hasNext()){
			count++;
			String lg=itr.next();
			System.out.println(lg);
			con=new Telnet(lg);
			System.out.println("Conectado");
			try{
				System.out.println("best= "+Telnet.getBestPath(con.ask("192.80.24.6")));
				con.quit();
				con.close();
			}catch(IOException e){
				e.printStackTrace();
			}

		}
		

	}

}
