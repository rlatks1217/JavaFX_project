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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;
import vo.User;

public class MemberManager implements Initializable {
	
	@FXML
	private TableView<User> tableView;
	@FXML
	private TableColumn<User, String> userName; 
	@FXML
	private TableColumn<User, String> pw;
	@FXML
	private TableColumn<User, String> userId;
	@FXML
	private TableColumn<User, String> telephone;
	@FXML
	private TableColumn<User, Integer> point;
	@FXML
	private TextField selectText; 
	@FXML
	private Button home;
	@FXML
	private Button modifyBtn;
	@FXML
	private Button deleteMember;
	
	static User user;

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Service service = new Service();
		ObservableList<User> list = service.getUSerInfo();
		
		userName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
		pw.setCellValueFactory(new PropertyValueFactory<>("pw"));
		userId.setCellValueFactory(new PropertyValueFactory<>("id"));
		telephone.setCellValueFactory(new PropertyValueFactory<>("UserPhoneNum"));
		point.setCellValueFactory(new PropertyValueFactory<>("point"));
		
		tableView.setItems(list);
		
		tableView.setRowFactory(e -> {
			TableRow<User> row = new TableRow<>();
			row.setOnMouseClicked(e1 -> {
				user = row.getItem();
			});
			return row;
		});
	}
	
	@FXML
	private void selectKeyword() {
		String selectname = selectText.getText();
		Service service = new Service();
		ObservableList<User> list = service.getUSerselect(selectname);
		tableView.setItems(list);
	}
	
	@FXML
	private void gohome() {
		
		Parent online;
		try {
			online = FXMLLoader.load(getClass().getResource("AdminMain.fxml"));
			Scene onlineScene = new Scene(online);
			Shared.stageShow(onlineScene, "Library System");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void modify() {
		if (user != null) {
			Shared.setDetail(new Stage());
			Parent modifyInfo;
			try {
				modifyInfo = FXMLLoader.load(getClass().getResource("modifyInfo.fxml"));
				Scene modifyInfoScene = new Scene(modifyInfo);
				Shared.getDetail().setTitle("Modify Information");
				Shared.getDetail().setScene(modifyInfoScene);
				Shared.getDetail().show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("오류");
			alert.setContentText("회원을 선택해주세요!!");
			alert.show();
		}
	}
	
	@FXML
	private void deleteMember() {
		if (user != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("회원 정보 삭제");
			alert.setHeaderText("잠깐! 해당 회원을 탈퇴시키겠습니까?");
			alert.setContentText("OK 버튼 클릭 시 탈퇴가 진행 됩니다.");
			Optional<ButtonType> click = alert.showAndWait();
			if (click.get() == ButtonType.OK) {
				Service service = new Service();
				int result = service.deleteMember(user.getId());
				
				if (result == 1) {
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("회원 정보 삭제");
					alert.setContentText("해당 회원 탈퇴되었습니다.");
					alert.show();
					
					if (Shared.getFieldId().equals("admin")) {
						Parent adminMain;
						try {
							adminMain = FXMLLoader.load(getClass().getResource("adminMain.fxml"));
							Scene adminMainScene = new Scene(adminMain);
							Shared.stageShow(adminMainScene, "Library System");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						System.out.println("들어옴");
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
					
				} else {
					System.out.println("회원선택 안 됨");
				}
				
			} else if (click.get() == ButtonType.CANCEL) {
				System.out.println("취소됨");
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("오류");
			alert.setContentText("회원을 선택해주세요!!");
			alert.show();
		}
	}

}
