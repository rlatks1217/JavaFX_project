package bookView;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import service.Service;
import vo.Book;

public class DeleteBook implements Initializable {
	
	@FXML
	private TextArea detailText;
	@FXML
	private TableView<Book> imageTable;
	@FXML
	private TableColumn<Book, ImageView> bookImg;
	@FXML
	private Button deleteBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		detailText.setText(Detail.getText());
		ObservableList<Book> list = FXCollections.observableArrayList();
		list.add(Detail.getBook());
		bookImg.setCellValueFactory(new PropertyValueFactory<>("bimgView"));
		imageTable.setItems(list);
	}
	
	@FXML
	private void deleteBook() {
		Service service = new Service();
		int result = service.deleteBook(Detail.getBook().getBisbn());
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		if (result == 1) {
			alert.setTitle("삭제 성공");
			alert.setHeaderText(Detail.getBook().getBtitle() + "(이)가 ");
			alert.setContentText("삭제되었습니다");
			Optional<ButtonType> click = alert.showAndWait();
			if (click.get() == ButtonType.OK) {
				Shared.getDetail().close();
				Parent main;
				try {
					main = FXMLLoader.load(getClass().getResource("AdminMain.fxml"));
					Scene mainScene = new Scene(main);
					Shared.stageShow(mainScene, "Library System");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} else {
			System.out.println("삭제 실패!!");
		}
	}


}
