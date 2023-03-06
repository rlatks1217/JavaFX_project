package bookView;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;
import vo.Book;
import vo.User;

public class MyPage implements Initializable {

	// 마이페이지
	@FXML
	private Button home;
	@FXML
	private TextField userName;
	@FXML
	private TextField userPhoneNum;
	@FXML
	private Label bookPoint;
	@FXML
	private Button updateInfo;
	@FXML
	private TableView<Book> tableView;
	@FXML
	private TableColumn<Book, String> rentalDate;
	@FXML
	private TableColumn<Book, Integer> bisbn;
	@FXML
	private TableColumn<Book, String> btitle;
	@FXML
	private TableColumn<Book, Integer> bprice;
	@FXML
	private TableColumn<Book, String> bauthor;
	@FXML
	private TableColumn<Book, Integer> bpage;
	@FXML
	private TableColumn<Book, String> bpublisher;
	@FXML
	private TableColumn<Book, String> btranslator;
	@FXML
	private TableColumn<Book, String> returnDate;
	@FXML
	private TableColumn<Book, String> status;
	@FXML
	private Button deleteMember;
	@FXML
	private Button changePwBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Service service = new Service();
		User user = service.getProfile(Shared.getFieldId());
		
		userName.setText(user.getUserName());
		userPhoneNum.setText(user.getUserPhoneNum());
		bookPoint.setText(user.getPoint() + "점");

		rentalDate.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
		bisbn.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
		btitle.setCellValueFactory(new PropertyValueFactory<>("btitle"));
		bprice.setCellValueFactory(new PropertyValueFactory<>("bprice"));
		bauthor.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
		bpage.setCellValueFactory(new PropertyValueFactory<>("bpage"));
		bpublisher.setCellValueFactory(new PropertyValueFactory<>("bpublisher"));
		btranslator.setCellValueFactory(new PropertyValueFactory<>("btranslator"));
		returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));

		ObservableList<Book> list = service.getUserRentBook(Shared.getFieldId());

		tableView.setItems(list);
		
		tableView.setRowFactory(e -> {
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

	@FXML
	private void gohome() {
		try {
			if (Shared.getFieldId()!= null) {
				Parent online;
				online = FXMLLoader.load(getClass().getResource("online.fxml"));
				Scene onlineScene = new Scene(online);
				Shared.stageShow(onlineScene, "Library System");
			} else {
				Parent main = FXMLLoader.load(getClass().getResource("main.fxml"));
				Scene mainScene = new Scene(main);
				Shared.stageShow(mainScene, "Library System");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void updateInfo() {
		String name = userName.getText();
		String phone = userPhoneNum.getText();
		Service service = new Service();
		User user = service.updateInfo(Shared.getFieldId(), name, phone);

		userName.setText(user.getUserName());
		userPhoneNum.setText(user.getUserPhoneNum());
		
		if (user != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("수정성공!");
			alert.setContentText("수정이 완료되었습니다.");
			alert.show();
		}
	}

	@FXML
	private void moveChangePw() {
		try {
			Parent changePwPage = FXMLLoader.load(getClass().getResource("changePw.fxml"));
			Scene mainScene = new Scene(changePwPage);
			Shared.stageShow(mainScene, "Change Password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void deleteMember() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("회원 정보 삭제");
		alert.setHeaderText("잠깐! 회원을 탈퇴하시겠습니까?");
		alert.setContentText("OK 버튼 클릭 시 탈퇴가 진행 됩니다.");
		Optional<ButtonType> click = alert.showAndWait();
		if (click.get() == ButtonType.OK) {
			Service service = new Service();
			int result = service.deleteMember(Shared.getFieldId());

			if (result == 1) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("회원 정보 삭제");
				alert.setContentText("회원 탈퇴되었습니다.");
				alert.show();
				
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

		} else if (click.get() == ButtonType.CANCEL) {
			System.out.println("취소됨");
		}
	}
}
