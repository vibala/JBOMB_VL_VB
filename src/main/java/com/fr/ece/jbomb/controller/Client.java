package com.fr.ece.jbomb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
/**
 * Représente la connexion du client
 * @author huong
 *
 */
public class Client implements Serializable{
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostname;
      private int port;
      private String pseudo="Pseudo non initialisé"; //Doit etre unique dans un topic
      private transient Socket connection=null;
      /**
       * Constructeur Client
       * @param hostname hostname
       * @param port port hote
       * @throws IOException
       */
      public Client(String hostname,int port) throws IOException{
          this.hostname=hostname;
          this.port=port;
          this.connection=new Socket();

     
      }
      /**
       * Configuration de la connexion
       * @param servername
       * @param serverPort
       * @throws IOException
       */
      public void connect(String servername,int serverPort) throws IOException{
          connection.bind(new InetSocketAddress(getHostname(), getPort()));
          connection.connect(new InetSocketAddress(servername, serverPort));
      }
/**
 * Reccupérer la connexion
 * @return
 */
    public Socket getConnection() {
        return connection;
    }
/**
 * Fermeture de la connexion
 */
    public void close() {
    	try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Reccuperer pseudo   
     * @return
     */
   public String getPseudo() {
        return pseudo;
    }
/**
 * Modifier le pseudo
 * @param name
 */
    public void setPseudo(String name) {
        this.pseudo = name;
    }

/**
 * Reccupérer hostname
 * @return
 */
    public String getHostname() {
        return hostname;
    }
/**
 * Modifier le hostname
 * @param hostname
 */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
/**
 * reccup port
 * @return
 */
    public int getPort() {
        return port;
    }
/**
 * modifier le port
 * @param port
 */
    public void setPort(int port) {
        this.port = port;
    }
/**
 * Comparer
 */
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
/**
 * Description du client
 */
	@Override
	public String toString(){
	return this.pseudo;
	}
}