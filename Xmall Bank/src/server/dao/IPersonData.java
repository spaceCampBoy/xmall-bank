package server.dao;

import shared.model.Person;

public interface IPersonData {
	Person logIn(String userName, String password);
	boolean registerPerson(Person person);
}
