package com.capgemini.starterkit.bookService.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.starterkit.bookService.dataprovider.data.BookVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class BookSearchModel {

	private final StringProperty title = new SimpleStringProperty();
	private final ListProperty<BookVO> result = new SimpleListProperty<>(
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

	public final List<BookVO> getResult() {
		return result.get();
	}

	public final void setResult(List<BookVO> value) {
		result.setAll(value);
	}

	public ListProperty<BookVO> resultProperty() {
		return result;
	}
}
