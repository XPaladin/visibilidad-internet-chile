package src;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LGConnection {

	static public void asdasd()throws Exception{
		URL url=new URL("https://service.asdasd.it/lg/?query=bgp&protocol=IPv4&addr=192.80.24.6&router=mi-caldera-ix");
		HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
		int code = uc.getResponseCode();
		String response = uc.getResponseMessage();
		System.out.println("HTTP/1.x " + code + " " + response);
		for (int j = 1; ; j++) {
			String header = uc.getHeaderField(j);
			String key = uc.getHeaderFieldKey(j);
			if (header == null || key == null) break;
		//	System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
		}  // end for
		BufferedReader in=new BufferedReader(new InputStreamReader(uc.getInputStream()));

		String line;
		while(null!=(line=in.readLine())){
			if(line.indexOf("AS")>-1 && line.indexOf("<FONT COLOR=\"#FF0000\"")>-1){
				System.out.println(line);
			}
		}
	}
	
	static public void eunetip()throws Exception{
		URL url=new URL("http://seeker.fi.eunetip.net/lg?query=show+route&arguments=192.80.24.6&router=bbr1.ldn1.uk.eunetip.net&ipv=both&showroute=best");
		HttpURLConnection uc = (HttpURLConnection) url.openConnection();
		int code = uc.getResponseCode();
		String response = uc.getResponseMessage();
		System.out.println("HTTP/1.x " + code + " " + response);
		for (int j = 1; ; j++) {
			String header = uc.getHeaderField(j);
			String key = uc.getHeaderFieldKey(j);
			if (header == null || key == null) break;
		//	System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
		}  // end for
		BufferedReader in=new BufferedReader(new InputStreamReader(uc.getInputStream()));

		String line;
		while(null!=(line=in.readLine())){
			if(line.indexOf("AS path")>-1){
				System.out.println(line);
			}
		}
	}
	
	static public void main(String args[])throws Exception{
		eunetip();
	}
}
