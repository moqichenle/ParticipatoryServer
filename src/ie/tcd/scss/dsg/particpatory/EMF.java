package ie.tcd.scss.dsg.particpatory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@PersistenceUnit(unitName = "transactions-optional")
public final class EMF {

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EMF() {
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}
}