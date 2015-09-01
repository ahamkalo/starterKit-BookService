package com.capgemini.starterkit.bookService.controller;

import java.net.URL;
import java.util.ResourceBundle;
import com.capgemini.starterkit.bookService.dataprovider.data.AuthorVO;
import com.capgemini.starterkit.bookService.model.AddAuthorModel;
import com.capgemini.starterkit.bookService.model.AddBookModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAuthorController {
	@FXML
	ResourceBundle resources;

	@FXML
	URL location;

	@FXML
	TextField firstNameField;

	@FXML
	TextField lastNameField;

	@FXML
	Button addAuthorButton;

	private final AddAuthorModel model = new AddAuthorModel();
	private AddBookModel addBookModel;
	Stage authorStage;

	@FXML
	private void initialize() {
		firstNameField.textProperty().bindBidirectional(model.firstNameProperty());
		lastNameField.textProperty().bindBidirectional(model.lastNameProperty());

		addAuthorButton.disableProperty()
				.bind(firstNameField.textProperty().isEmpty().or(lastNameField.textProperty().isEmpty()));
	}

	@FXML
	public void addAuthorButtonAction() {
		addBookModel.getAuthors().add(new AuthorVO(null, model.getFirstName(), model.getLastName()));
		authorStage.close();
	}

	public void initializeController(AddBookModel addBookModel, Stage authorStage) {
		this.addBookModel = addBookModel;
		this.authorStage = authorStage;
	}
}
