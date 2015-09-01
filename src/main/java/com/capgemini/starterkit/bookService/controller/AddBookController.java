package com.capgemini.starterkit.bookService.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.capgemini.starterkit.bookService.dataprovider.BookProvider;
import com.capgemini.starterkit.bookService.dataprovider.data.AuthorVO;
import com.capgemini.starterkit.bookService.dataprovider.data.BookVO;
import com.capgemini.starterkit.bookService.model.AddBookModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddBookController {
	private static final Logger LOG = Logger.getLogger(AddBookController.class);

	@FXML
	ResourceBundle resources;

	@FXML
	URL location;

	@FXML
	TextField titleField;

	@FXML
	Button addBookButton;

	@FXML
	TableView<AuthorVO> resultTable;

	@FXML
	TableColumn<AuthorVO, String> firstNameColumn;

	@FXML
	TableColumn<AuthorVO, String> lastNameColumn;

	private final BookProvider bookProvider = BookProvider.INSTANCE;

	private final AddBookModel model = new AddBookModel();

	Stage bookStage;

	public AddBookModel getModel() {
		return model;
	}

	@FXML
	private void initialize() {
		titleField.textProperty().bindBidirectional(model.titleProperty());
		titleField.clear();
		resultTable.itemsProperty().bind(model.authorsProperty());
		resultTable.getItems().clear();

		firstNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirstName()));
		lastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));

		addBookButton.disableProperty().bind(model.titleProperty().isEmpty().or(model.authorsProperty().emptyProperty()));
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
	}

	@FXML
	public void addBookButtonAction() {
		BookVO book = new BookVO(null, model.getTitle(), new ArrayList<AuthorVO>(model.getAuthors()));
		saveBook(book);
		LOG.debug("'Add Book' button clicked");

		bookStage.close();
	}

	private void saveBook(BookVO book) {
		Task<Void> backgroundTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				LOG.debug("call() called");

				bookProvider.saveBook(book);
				return null;
			}
		};

		new Thread(backgroundTask).start();
	}

	@FXML
	public void addAuthorButtonAction() throws IOException {
		Stage authorStage = new Stage();
		/*
		 * REV: okno dodawania autora powinno byc modalne w stosunku do okna dodawania ksiazki:
		 * stage.initOwner(bookStage);
		 */
		FXMLLoader fxmlLoader = initializeFXMLLoader();

		Parent root = fxmlLoader.load();

		AddAuthorController controller = fxmlLoader.getController();
		controller.initializeController(model, authorStage);

		authorStage.setTitle("Add Author");

		Scene scene = new Scene(root);

		scene.getStylesheets()
				.add(getClass().getResource("/com/capgemini/starterkit/bookService/css/standard.css").toExternalForm());

		authorStage.setScene(scene);
		authorStage.show();

		handleBookStageSetOnHidden(authorStage);
	}

	private void handleBookStageSetOnHidden(Stage authorStage) {
		bookStage = (Stage) addBookButton.getScene().getWindow();
		bookStage.setOnHidden(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				authorStage.close();
			}
		});
	}

	private FXMLLoader initializeFXMLLoader() {
		URL location = getClass().getResource("/com/capgemini/starterkit/bookService/view/add-author.fxml");

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setResources(ResourceBundle.getBundle("com/capgemini/starterkit/bookService/bundle/bundle"));
		return fxmlLoader;
	}
}
