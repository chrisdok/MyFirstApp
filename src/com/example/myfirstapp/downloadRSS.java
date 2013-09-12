package com.example.myfirstapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class downloadRSS {

	 public String[] DownloadRSS(String URL)
	    {
	        InputStream in = null;
	        String[] RSSTitles = null;
	        try {
	            in = OpenHttpConnection(URL);
	            Document doc = null;
	            DocumentBuilderFactory dbf = 
	            DocumentBuilderFactory.newInstance();
	            DocumentBuilder db;
	            
	            try {
	                db = dbf.newDocumentBuilder();
	                doc = db.parse(in);
	            } catch (ParserConfigurationException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            } catch (SAXException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }        
	            
	            doc.getDocumentElement().normalize(); 
	            
	            //---retrieve all the <item> nodes---
	            NodeList itemNodes = doc.getElementsByTagName("item"); 
	            
	            String strTitle = "";
	            RSSTitles = new String[itemNodes.getLength()];
	            for (int i = 0; i < itemNodes.getLength(); i++) { 
	                Node itemNode = itemNodes.item(i); 
	                if (itemNode.getNodeType() == Node.ELEMENT_NODE) 
	                {            
	                    //---convert the Node into an Element---
	                    Element itemElement = (Element) itemNode;
	                    
	                    //---get all the <title> element under the <item> 
	                    // element---
	                    NodeList titleNodes = 
	                        (itemElement).getElementsByTagName("title");
	                    
	                    //---convert a Node into an Element---
	                    Element titleElement = (Element) titleNodes.item(0);
	                    
	                    //---get all the child nodes under the <title> element---
	                    NodeList textNodes = 
	                        ((Node) titleElement).getChildNodes();
	                    
	                    //---retrieve the text of the <title> element---
	                    strTitle = ((Node) textNodes.item(0)).getNodeValue();
	                    RSSTitles[i]=strTitle;
	                    /*---display the title---
	                    Toast.makeText(getBaseContext(),strTitle, 
	                        Toast.LENGTH_LONG).show();*/
	                } 
	                
	                
	            }

	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();            
	        }
	        return RSSTitles;
	    }
	 
	 public InputStream OpenHttpConnection(String urlString) 
			    throws IOException
			    {
			        InputStream in = null;
			        int response = -1;
			               
			        URL url = new URL(urlString); 
			        URLConnection conn = url.openConnection();
			                 
			        if (!(conn instanceof HttpURLConnection))                     
			            throw new IOException("Not an HTTP connection");
			        
			        try{
			            HttpURLConnection httpConn = (HttpURLConnection) conn;
			            httpConn.setAllowUserInteraction(false);
			            httpConn.setInstanceFollowRedirects(true);
			            httpConn.setRequestMethod("GET");
			            httpConn.connect(); 

			            response = httpConn.getResponseCode();                 
			            if (response == HttpURLConnection.HTTP_OK) {
			                in = httpConn.getInputStream();                                 
			            }                     
			        }
			        catch (Exception ex)
			        {
			            throw new IOException("Error connecting");            
			        }
			        return in;     
			    }

}
