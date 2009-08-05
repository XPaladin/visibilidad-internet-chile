package src;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;


public class Telnet {
	BufferedReader in;
	PrintWriter out;
	Socket s;
	
	Telnet(String ip) throws UnknownHostException{
		try{
			s= new Socket(ip,23);
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new 
					InputStreamReader(s.getInputStream()));
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	Telnet(String ip, String user)throws UnknownHostException{
		this(ip);
		out.println(user);
		
	}
	Telnet(String ip, String user, String password)throws UnknownHostException{
		this(ip, user);
		out.println(password);
		
	}
	public void quit(){
		out.println("quit");
	}
	
	public void close(){
		try{
			s.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/*
	 * Must be ip, not hostname
	 */
	public BufferedReader ask(String ip){
		StringBuffer sb=new StringBuffer();
		// tamaño de la terminal en 0, para que no envie "--More--",
		// otra solucion es leer caracter a caracter.
		out.println("term len 0");
		//Pido la tabla a mi ruta
		out.println("show ip bgp "+ip);
		out.println();
		String line;
		try {
			int idx;
			do{
				line=in.readLine();
			}while(-1==(idx=line.indexOf("show ip bgp")));


			String promt=line.substring(0,idx);
			//System.out.println("promt="+promt);
			while(!promt.equals(line=in.readLine())){
				//System.out.println(line);
				sb.append(line+"\n");
			}
			//System.out.println("promt?"+line);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(sb.toString());
		return new BufferedReader( new StringReader( sb.toString()));


	}
	static public String getBestPath(BufferedReader input)throws IOException{
		String best=null;
		String line;
		
//		 Busca BGP routing table...
		do{
			line=input.readLine().trim();
			if( line.indexOf("Network not in table")>0)
				return line;
		}while(line.indexOf("BGP routing table entry for")==-1);
		//System.out.println(line);
		line = input.readLine().trim();
		//skip lineas opcionales
		if(line.indexOf("Bestpath Modifiers:")==0){
			line = input.readLine().trim();
		}
		if(line.indexOf("Paths")!=0)return null;
		//indice
		int idx = line.indexOf('(')+1;
		//numero de registros
		int n_regs = Integer.parseInt(line.substring(idx,line.indexOf(' ',idx)));
		
		idx = line.indexOf('#')+1;
		//indice del mejor
		int idx_best = Integer.parseInt(line.substring(
				idx,line.indexOf(',',idx)==-1?
						line.indexOf(')',idx) :
							line.indexOf(',',idx)));

		//Envio newlines por el (more)
		//for(int i=0; i<n_regs*4; i++){
			
				
		//leo todos los registros y guardo el mejor
		for(int i=1;i<=n_regs;i++){
			for(int j=0;j<3;j++){
				line=input.readLine().trim();
				//System.out.println(line);
				//skip lineas opcionales
				if(line.indexOf("Community:")==0 ||
						line.indexOf("Originator:")==0 ||
						line.indexOf("Last update:")==0){
					i--;
					break;
				}
				//skip lineas opcionales
				if(line.equals("Not advertised to any peer") || 
						line.indexOf("Flag: 0x")==0 
						){
					j--;
					continue;
				}
				//System.out.println(line);
				
				if(i==idx_best&&j==0){
					best=line;
				}
			}
		}
		return best;
	}
	/*
	 * Must be ip, not hostname
	 */
	static public int getLastASN(String ASPath){
		int idx= ASPath.indexOf(',');
		
		if(idx>0){
			ASPath=ASPath.substring(0,idx)	;
		}
		String[] ASNS=ASPath.split(" ");
		return Integer.parseInt(ASNS[ASNS.length-1]);
		
		
	}
	static public void main(String args[])throws Exception{

		//System.out.println(""+getLastASN("3356 6762 6429 23140"));
		/*
		Telnet con=new Telnet("route-server.eu.gblx.net");
		System.out.println("Conectado");
		BufferedReader br = con.ask("192.80.24.6");
		con.quit();
		con.close();
		
		System.out.println("best= "+getBestPath(br));
		
		*/


	}
}
