package bookView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;
import vo.Book;

public class AdminMain implements Initializable {

	@FXML
	private TextField selectText;
	@FXML
	private Button registerBtn;
	@FXML
	private Button logOut;
	@FXML
	private Button memberManager;
	@FXML
	private Button bookManager;
	@FXML
	private TableColumn<Book, String> btitleM;
	@FXML
	private TableColumn<Book, String> bauthorM;
	@FXML
	private TableColumn<Book, String> bisbnM;
	@FXML
	private TableColumn<Book, String> bpriceM;
	@FXML
	private TableView<Book> mainTableView;
	@FXML
	private Label userId;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Service service = new Service();
		ObservableList<Book> list = null;

		bisbnM.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
		btitleM.setCellValueFactory(new PropertyValueFactory<>("btitle"));
		bauthorM.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
		bpriceM.setCellValueFactory(new PropertyValueFactory<>("bprice"));

		if (Shared.getFieldId() != null) {
			userId.setText(Shared.getFieldId() + " 1님");
		}
		list = service.selectBook("");
		mainTableView.setItems(list);
		
		mainTableView.setRowFactory(e -> {
			TableRow<Book> row = new TableRow<>();
			row.setOnMouseClicked(e1 -> {
				String bookTitle = (row.getItem()).getBtitle();
				if (e1.getClickCount() > 1 && Shared.getFieldId() != null) {
					Shared.setDetail(new Stage());

					Book bookDetail = service.selectBookDetail(bookTitle);

					StringBuilder sb = new StringBuilder();
					sb.append("ISBN | " + bookDetail.getBisbn() + "\n");
					sb.append("TITLE | " + bookDetail.getBtitle() + "\n");
					sb.append("PRICE | " + bookDetail.getBprice() + "\n");
					sb.append("PAGE | " + bookDetail.getBpage() + "\n");
					sb.append("AUTHOR | " + bookDetail.getBauthor() + "\n");
					sb.append("PUBLISHER | " + bookDetail.getBpublisher() + "\n");
					if (bookDetail.getBtranslator().equals("")) {
						sb.append("없음" + "\n");
					} else {
						sb.append("TRANSLATOR | " + bookDetail.getBtranslator() + "\n");
					}

					Detail.setBook(bookDetail);
					Detail.setText(sb.toString());

					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("deleteBook.fxml"));

						Scene scene = new Scene(root);
						Shared.getDetail().setTitle("책 삭제");
						Shared.getDetail().setScene(scene);
						Shared.getDetail().show();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}
			});

			return row;
		});

	}

	@FXML
	private void moveRegister() {
		try {
			Parent registerPage = FXMLLoader.load(getClass().getResource("registerBook.fxml"));
			Scene scene = new Scene(registerPage);
			Shared.setDetail(new Stage());
			Shared.getDetail().setScene(scene);
			Shared.getDetail().setTitle("Register Book");
			Shared.getDetail().show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void selectKeyword() {
		String keyword = selectText.getText();
		Service service = new Service();
		ObservableList<Book> list = service.selectBook(keyword);
		bisbnM.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
		btitleM.setCellValueFactory(new PropertyValueFactory<>("btitle"));
		bauthorM.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
		bpriceM.setCellValueFactory(new PropertyValueFactory<>("bprice"));

		mainTableView.setItems(list);
		selectText.clear();
	}

	@FXML // 로그아웃
	private void logout() {
		Shared.setFieldId(null);
		Parent main;
		try {
			main = FXMLLoader.load(getClass().getResource("main.fxml"));
			Scene mainScene = new Scene(main);
			Shared.stageShow(mainScene, "Library System");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	private void movebookManager() {
		Parent bookManager;
		try {
			bookManager = FXMLLoader.load(getClass().getResource("bookManager.fxml"));
			Scene bookManagerScene = new Scene(bookManager);
			Shared.stageShow(bookManagerScene, "Library System");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void moveMemberManager() {
		Parent memberManager;
		try {
			memberManager = FXMLLoader.load(getClass().getResource("memberManager.fxml"));
			Scene memberManagerScene = new Scene(memberManager);
			Shared.stageShow(memberManagerScene, "Library System");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
