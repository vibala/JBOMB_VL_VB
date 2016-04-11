package com.fr.ece.jbomb.server;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MainServer {

	public static void main(String[] args) throws InterruptedException, IOException {
		Server server=null;
		int Port=1234;
		int nbConnectionMax=4;
		String ipServer="127.0.0.1";
		server = new Server (nbConnectionMax,new InetSocketAddress(ipServer,Port));
		server.start();

	}
}
