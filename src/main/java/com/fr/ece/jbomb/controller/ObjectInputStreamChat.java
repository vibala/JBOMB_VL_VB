package com.fr.ece.jbomb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
/**
 * Wrap de la classe ObjectInputStream qui permet d'ajouter des améliorations futures
 * @author huong
 *
 */
public class ObjectInputStreamChat extends ObjectInputStream {
private PrintWriterChat w;
/**
 * Constructeur
 * @param in Wrap du in
 * @param w Réccup un write pour une amorce (amérlioration futures possibles)
 * @throws IOException Exception
 */
	public ObjectInputStreamChat(InputStream in,PrintWriterChat w) throws IOException {
		super(in);
                this.w=w;
	}
        /**
         * Lecture de l'objet depuis le serveur
         * @return retourne objet lu
         * @throws IOException Excption
         * @throws ClassNotFoundException Exception
         */
        public Object lire() throws IOException, ClassNotFoundException{
            w.envoyer("Amorce");
            return readObject();
        }
}
