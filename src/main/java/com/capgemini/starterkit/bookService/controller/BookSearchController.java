package com.capgemini.starterkit.bookService.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.capgemini.starterkit.bookService.dataprovider.BookProvider;
import com.capgemini.starterkit.bookService.dataprovider.data.AuthorVO;
import com.capgemini.starterkit.bookService.dataprovider.data.BookVO;
import com.capgemini.starterkit.bookService.model.BookSearchModel;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BookSearchController {
	private static final Logger LOG = Logger.getLogger(BookSearchController.class);

	@FXML
	ResourceBundle resources;

	@FXML
	URL location;

	@FXML
	TextField nameField;

	@FXML
	Button searchButton;

	@FXML
	TableView<BookVO> resultTable;

	@FXML
	TableColumn<BookVO, Number> idColumn;

	@FXML
	TableColumn<BookVO, String> titleColumn;

	@FXML
	TableColumn<BookVO, String> authorsColumn;

	private final BookProvider bookProvider = BookProvider.INSTANCE;

	private final BookSearchModel model = new BookSearchModel();

	@FXML
	private void initialize() {
		LOG.debug("initialize()");

		initializeResultTable();

		nameField.textProperty().bindBidirectional(model.titleProperty());
		resultTable.itemsProperty().bind(model.resultProperty());
		nameField.textProperty().set("");
	}

	private void initializeResultTable() {
		idColumn.setCellValueFactory(cellData -> new ReadOnlyLongWrapper(cellData.getValue().getId()));
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		authorsColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BookVO, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BookVO, String> param) {
						StringBuilder stringBuilder = new StringBuilder();

						Iterator<AuthorVO> itr = param.getValue().getAuthors().iterator();
						AuthorVO autor;
						while (itr.hasNext()) {
							autor = itr.next();
							stringBuilder.append(autor.getFirstName() + " ");
							stringBuilder.append(autor.getLastName());
							if (itr.hasNext()) {
								stringBuilder.append(", ");
							}
						}

						return new ReadOnlyStringWrapper(stringBuilder.toString());
					}
				});
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
	}

	@FXML
	public void searchButtonAction() {
		LOG.debug("'Search' button clicked");

		searchBooks();
	}

	private void searchBooks() {
		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {

			@Override
			protected Collection<BookVO> call() throws Exception {
				LOG.debug("call() called");

				return bookProvider.findBooks(model.getTitle());
			}
		};

		backgroundTask.stateProperty().addListener(new ChangeListener<Worker.State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue == State.SUCCEEDED) {
					LOG.debug("changed() called");

					model.setResult(new ArrayList<BookVO>(backgroundTask.getValue()));

					resultTable.getSortOrder().clear();
				}
			}
		});

		new Thread(backgroundTask).start();
	}

	@FXML
	public void addBookButtonAction() throws IOException {
		Stage stage = new Stage();

		stage.setTitle("Add Book");

		Parent root = FXMLLoader.load(
				getClass().getResource("/com/capgemini/starterkit/bookService/view/add-book.fxml"), //
				ResourceBundle.getBundle("com/capgemini/starterkit/bookService/bundle/bundle"));

		Scene scene = new Scene(root);

		scene.getStylesheets()
				.add(getClass().getResource("/com/capgemini/starterkit/bookService/css/standard.css").toExternalForm());

		stage.setScene(scene);
		stage.show();

	}
}
