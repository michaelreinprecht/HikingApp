package facade;

import models.Hike;
import java.util.List;

public interface IDatabaseFacade {
	/**
	 * INSERT - inserts the given objects into the database
	 * @param value
	 */
	public void insert(Object value);
	
	/**
	 * UPDATE - attempts to update the given object in the database, if it doesn't yet exists, instead inserts a new object
	 * @param value
	 */
	public void update(Object value);
	
	/**
	 * DELETE - attempts to delete the given object from the database
	 * @param value
	 */
	public void delete(Object value);
}
