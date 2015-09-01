package com.capgemini.starterkit.bookService.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.starterkit.bookService.dataprovider.data.AuthorVO;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class AddBookModel {

	private final StringProperty title = new SimpleStringProperty();
	private final ListProperty<AuthorVO> authors = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public String getTitle() {
		return title.get();
	}

	public final void setTitle(String value) {
		title.set(value);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public List<AuthorVO> getAuthors() {
		return authors.get();
	}

	public final void setAuthors(List<AuthorVO> value) {
		authors.setAll(value);
	}

	public ListProperty<AuthorVO> authorsProperty() {
		return authors;
	}
}
