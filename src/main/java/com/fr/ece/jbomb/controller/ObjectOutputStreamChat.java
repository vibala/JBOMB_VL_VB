package com.fr.ece.jbomb.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Wrap de la classe ObjectOutputStreamChat qui permet d'ajouter des améliorations futures
 * @author huong
 *
 */
public class ObjectOutputStreamChat extends ObjectOutputStream {
private BufferedReaderChat r;
/**
 * Constructeur
 * @param out sortie pour wrap la classe ObjectOutputStream
 * @param r prend un reader pour de futures améliorations possibles
 * @throws IOException
 */
	public ObjectOutputStreamChat(OutputStream out,BufferedReaderChat r) throws IOException {
		super(out);
		this.r=r;
	}
	/**
	 * Envoyer un objet vers le serveur
	 * @param o
	 */
	public void envoyer(Object o){
            try {
           r.lire();
           reset(); // IL FAUT RESET si tu veux qu'il le prenne en compte lorsque tu renvois un même objet sinon le Read de l'autre côté va comparer l'UID de la sérialization et verra qu'il a déjà donc il actualise pas et garde la version précédente qu'il a recu 
                writeObject(o);
                flush();
            } catch (IOException ex) {
                Logger.getLogger(ObjectOutputStreamChat.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}
