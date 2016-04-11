package com.fr.ece.jbomb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ObjectInputStreamChat extends ObjectInputStream {
private PrintWriterChat w;

	public ObjectInputStreamChat(InputStream out,PrintWriterChat w) throws IOException {
		super(out);
                this.w=w;
	}
        
        public Object lire() throws IOException, ClassNotFoundException{
            w.envoyer("Amorce");
            return readObject();
        }
}
