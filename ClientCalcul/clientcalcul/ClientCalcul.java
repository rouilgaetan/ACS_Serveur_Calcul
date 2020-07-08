/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcalcul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 *
 * @author grouil
 */
public class ClientCalcul {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) throws MalformedURLException, IOException{
        if(args.length>4 || args.length<3){
            System.out.println("Wrong number parameters "+args.length);
        }
        else{
            String url_string;
            if(args.length == 3){
                url_string="http://"+args[0]+"/ServeurCalcul/Calcul?"+"operateur="+args[1]+"&operande1="+args[2];
            }
            else{
                url_string="http://"+args[0]+"/ServeurCalcul/Calcul?"+"operateur="+args[1]+"&operande1="+args[2]+"&operande2="+args[3];
            }
            System.out.println(url_string);
            URL url = new URL(url_string);
            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            InputStream input = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String message = "";
            do{
                String line = reader.readLine();
                message+=line+"\n";
            }while (reader.ready());
            System.out.println(message);
        }
    }
    
}
