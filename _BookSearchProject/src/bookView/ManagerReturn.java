package bookView;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import service.Service;
import vo.Book;

public class ManagerReturn implements Initializable {
	
	private static String id;
	@FXML
	private TextArea detailText;
	@FXML
	private TableView<Book> imageTable;
	@FXML
	private TableColumn<Book, ImageView> bookImg;
	@FXML
	private Button rentBtn;
	@FXML
	private Button returnBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg) {
		detailText.setText(Detail.getText());
		ObservableList<Book> list = FXCollections.observableArrayList();
		list.add(Detail.getBook());
		bookImg.setCellValueFactory(new PropertyValueFactory<>("bimgView"));
		imageTable.setItems(list);

		Service service = new Service();
		int result = service.checkRent(Shared.getFieldId(), Detail.getBook());

	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		ManagerReturn.id = id;
	}
	
	@FXML
	private void returnBook() {
		Service service = new Service();
		
		int count = service.returnBook(id, Detail.getBook());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		if (count == 1) {
			alert.setTitle("반납 성공");
			alert.setHeaderText(Detail.getBook().getBtitle() + "(이)가");
			alert.setContentText("반납 처리되었습니다");
			alert.show();
			
			Shared.getDetail().close();
			
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("BookManger.fxml"));
				Scene scene = new Scene(root);
				Shared.getDetail().setTitle("세부사항");
				Shared.getDetail().setScene(scene);
				Shared.getDetail().show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				Parent online = FXMLLoader.load(getClass().getResource("online.fxml"));
				Scene onlineScene = new Scene(online);
				Shared.stageShow(onlineScene, "Library System");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
