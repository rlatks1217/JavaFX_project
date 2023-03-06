package bookView;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import service.Service;
import vo.Book;

public class RegisterBook {
	
	@FXML
	private TextField isbn;
	@FXML
	private TextField title;
	@FXML
	private TextField date;
	@FXML
	private TextField page;
	@FXML
	private TextField price;
	@FXML
	private TextField author;
	@FXML
	private TextField translator;
	@FXML
	private TextField publisher;
	
	@FXML
	private Button registerBtn;

	@FXML
	private void registerBook() {
		try {
			String bisbn = isbn.getText();
			String bTitle = title.getText();
			String bDate = date.getText();
			int bPage = Integer.parseInt(page.getText());
			int bPrice = Integer.parseInt(price.getText());
			String bAuthor = author.getText();
			String bTranslator = translator.getText();
			String bPublisher = publisher.getText();
			
			System.out.println(bPublisher);
			Book book = new Book(bisbn, bTitle, bPrice, bDate, bAuthor,
					bPage,bTranslator,bPublisher);
			
			Service service = new Service();
			int result = service.registerBook(book);
			System.out.println(result);
			if (result == 1) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("새로운 책 등록");
				alert.setContentText("등륵되었습니다");
				Optional<ButtonType> click = alert.showAndWait();
				if (click.get() == ButtonType.OK) {
					Shared.getDetail().close();
					Parent adminMain;
					try {
						adminMain = FXMLLoader.load(getClass().getResource("adminMain.fxml"));
						Scene adminMainScene = new Scene(adminMain);
						Shared.stageShow(adminMainScene, "Library System");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("새로운 책 등록");
				alert.setContentText("이미 등록되어 있는 책입니다.");
				alert.show();
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return;
		}
	}
}
