package com.capgemini.starterkit.bookService.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddAuthorModel {

	private final StringProperty firstName = new SimpleStringProperty();
	private final StringProperty lastName = new SimpleStringProperty();

	public String getFirstName() {
		return firstName.get();
	}

	public final void setFirstName(String value) {
		firstName.set(value);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public final void setLastName(String value) {
		lastName.set(value);
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}
}
