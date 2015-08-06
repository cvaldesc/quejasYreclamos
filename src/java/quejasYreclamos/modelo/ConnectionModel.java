/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quejasYreclamos.modelo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author cvaldes
 */
public class ConnectionModel {
    private static final String PERSISTENCE_UNIT_NAME = "quejasYreclamosUN";
	private static ConnectionModel connection;
	private EntityManagerFactory factoryConnection;
	
	public ConnectionModel(){
		factoryConnection = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	public static ConnectionModel getConnection(){
		if(connection == null){
			connection = new ConnectionModel();
		}
		return connection;
	}
	public EntityManagerFactory getFactoryConnection() {
		return factoryConnection;
	}
}
