package bookView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
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

public class BookView extends Application implements Initializable {

	// main페이지
	@FXML
	private Button loginMain;
	@FXML
	private Button signUpBtn;
	@FXML
	private Button logOut;
	@FXML
	private TextField selectText;
	@FXML
	private TableView<Book> mainTableView;
	@FXML
	private TableColumn<Book, String> bisbnM;
	@FXML
	private TableColumn<Book, String> btitleM;
	@FXML
	private TableColumn<Book, String> bauthorM;
	@FXML
	private TableColumn<Book, Integer> bpriceM;
	@FXML
	private TableColumn<Book, String> rent;
	@FXML
	private Button myPageBtn;
	@FXML
	private Label userId;
	
	@FXML // 메인에서 책 검색
	private void selectKeyword() {
		String keyword = selectText.getText();
		Service service = new Service();
		ObservableList<Book> list = null;
		bisbnM.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
		btitleM.setCellValueFactory(new PropertyValueFactory<>("btitle"));
		bauthorM.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
		bpriceM.setCellValueFactory(new PropertyValueFactory<>("bprice"));

		if (rent != null) {
			list = service.onlineSelectBook(keyword);
			rent.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
		} else {
			list = service.selectBook(keyword);
		}

		mainTableView.setItems(list);
		selectText.clear();
	}
	
	@FXML // 마이페이지 이동
	private void moveMypage() {
		try {
			Parent myPage = FXMLLoader.load(getClass().getResource("myPage.fxml"));
			Scene myPageScene = new Scene(myPage);
			Shared.stageShow(myPageScene, "My Page");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML // 로그인 페이지 이동
	private void moveLogin() {
		try {
			Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene loginScene = new Scene(login);
			Shared.stageShow(loginScene, "Login Page");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML // 회원가입 페이지 이동
	private void moveSignUp() {
		Parent signUp;
		try {
			signUp = FXMLLoader.load(getClass().getResource("signUp.fxml"));
			Scene signUpScene = new Scene(signUp);
			Shared.stageShow(signUpScene, "SignUp Page");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void start(Stage primaryStage) throws Exception {
 
		Shared.setStage(primaryStage);
		Parent main = FXMLLoader.load(getClass().getResource("main.fxml"));

		Scene mainScene = new Scene(main);
		Shared.stageShow(mainScene, "Library System");

	}

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Service service = new Service();
		ObservableList<Book> list = null;
 
		bisbnM.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
		btitleM.setCellValueFactory(new PropertyValueFactory<>("btitle"));
		bauthorM.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
		bpriceM.setCellValueFactory(new PropertyValueFactory<>("bprice"));

		if (rent != null) {
			list = service.onlineSelectBook("");
			rent.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
			if (Shared.getFieldId() != null) {
				userId.setText(Shared.getFieldId() + " 님");
			}
		} else {
			list = service.selectBook("");
		}

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
					if (bookDetail.getRentalDate() != null) {
						sb.append("대여 날짜 | " + bookDetail.getRentalDate() + "\n");
					} else {
						sb.append("대여 날짜 | 대여 안함 ㅋ \n");
						
					}

					Detail.setBook(bookDetail);
					Detail.setText(sb.toString());

					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("detail.fxml"));

						Scene scene = new Scene(root);
						Shared.getDetail().setTitle("세부사항");
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

}
