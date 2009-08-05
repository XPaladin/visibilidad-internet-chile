package src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.Iterator;

public class DataMatrix {

	protected Vector<String[]> data;
	
	public DataMatrix(String file, String delimiter){
		try{
			BufferedReader in= new BufferedReader(new FileReader(file));
			String line;

			data= new Vector<String[]>();


			in= new BufferedReader(new FileReader(file));
			while((line=in.readLine())!=null){
				data.add(line.split(delimiter));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public DataMatrix(Vector<String[]> _data){
		data=_data;
		
	}

	public Vector<String> selectCells(int select_idx, int key_idx, String key){
		Vector<String> ret = new Vector<String>();
		Iterator<String[]> itr = data.iterator();
		while(itr.hasNext()){
			String[] reg=itr.next();
			if(reg[key_idx].equals(key)){
				ret.add(reg[select_idx]);
			}
		}  
		return ret;
	}

	public DataMatrix filter(int key_idx, String key){
		Vector<String[]> ret = new Vector<String[]>();
		Iterator<String[]> itr = data.iterator();
		String[] reg;
		while(itr.hasNext()){
			reg=itr.next();
			if(reg[key_idx].equals(key)){
				ret.add(reg);
			}
		}  
		return new DataMatrix(ret);
	}
	
	public String toString(){
		String ret ="";
		Iterator<String[]> itr = data.iterator();
		String[] reg;
		while(itr.hasNext()){
			reg=itr.next();
			for(int i=0;i<reg.length;i++){
				ret+=reg[i]+"\t";
			}
			ret+="\n";
		}
		return ret;
	}

	public Vector<String> selectColumn(int col_idx){
		Vector<String> ret = new Vector<String>();
		Iterator<String[]> itr = data.iterator();
		while(itr.hasNext()){
			String[] reg=itr.next();
			ret.add(reg[col_idx]);
		}  
		return ret;
	}

	public int rows(){
		return data.size();
	}
	
	static public void main(String[] args){
		DataMatrix lgs= new DataMatrix("data/telnetLG"," ");
		lgs=lgs.filter(4, "F");
		System.out.println(lgs);
		
		/*
		DataMatrix lacnic= new DataMatrix("data/delegated-lacnic-latest","\\|");  
		data=data.filter(1, "CL");
		//* data = chile 
		DataMatrix ipv4=data.filter(2, "ipv4");
		DataMatrix AS=data.filter(2, "asn");
		DataMatrix ipv6=data.filter(2, "ipv6");
		
			Vector<String> v= ipv4.selectColumn(4);
			Iterator<String> itr = v.iterator();
			long count=0;
			while(itr.hasNext())
			count+=Integer.parseInt(itr.next());
		System.out.println("ipv4="+count);
		
		v= ipv6.selectColumn(4);
		itr = v.iterator();
		count=0;
		while(itr.hasNext())
			count+=Integer.parseInt(itr.next());
		System.out.println("ipv6="+count);
		
		v= AS.selectColumn(4);
		itr = v.iterator();
		count=0;
		while(itr.hasNext())
			count+=Integer.parseInt(itr.next());
		System.out.println("ASes="+count);
		
		System.out.println(ipv4);
		System.out.println(ipv6);
		System.out.println(AS);
		
		
		
		Vector<String> v= data.selectCells(3,1,"CL");
		Iterator<String> itr = v.iterator();
		System.out.println("Iterating through Vector elements...");
		while(itr.hasNext())
			System.out.println(itr.next());
		*/
		
	}
}


