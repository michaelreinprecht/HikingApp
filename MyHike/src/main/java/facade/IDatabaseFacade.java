package facade;


public interface IDatabaseFacade {
	/**
	 * INSERT - inserts the given objects into the database
     */
	void insert(Object value);
	
	/**
	 * UPDATE - attempts to update the given object in the database, if it doesn't yet exist, instead inserts a new object
     */
	void update(Object value);
	
	/**
	 * DELETE - attempts to delete the given object from the database
     */
	void delete(Object value);
}
