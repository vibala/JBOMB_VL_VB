package com.fr.ece.jbomb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;

public class Client implements Serializable{
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostname;
      private int port;
      private String pseudo="Pseudo non initialisé"; //Doit etre unique dans un topic
      private transient Socket connection=null;
      
      public Client(String hostname,int port) throws IOException{
          this.hostname=hostname;
          this.port=port;
          this.connection=new Socket();

     
      }
      
      public void connect(String servername,int serverPort) throws IOException{
          connection.bind(new InetSocketAddress(getHostname(), getPort()));
          connection.connect(new InetSocketAddress(servername, serverPort));
      }

    public Socket getConnection() {
        return connection;
    }

    public void close() {
    	try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
       
   public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String name) {
        this.pseudo = name;
    }


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.pseudo, other.pseudo)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString(){
	return this.pseudo;
	}
}