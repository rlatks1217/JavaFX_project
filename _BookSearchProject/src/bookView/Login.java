package bookView;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import service.Service;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login {

	// 로그인 페이지
	@FXML
	private TextField textId;
	@FXML
	private TextField textPw;
	@FXML
	private static Button loginBtn;
	
	
	@FXML // 로그인 버튼 클릭 시
	private void login() {

		String checkId = textId.getText();
		String checkPw = textPw.getText();

		Alert alert = new Alert(AlertType.INFORMATION);
		boolean success = false;
		if (!checkId.equals("") && !checkPw.equals("")) {
			Service service = new Service(); 
			success = service.checkIdPw(checkId, checkPw);
			if (success) {
				alert.setTitle("로그인 성공");
				alert.setContentText("로그인 성공!!!!!!!!!!!");
				alert.show();
				Shared.setFieldId(checkId);
				if (Shared.getFieldId().equals("admin")) {
					try {
						Parent adminMain = FXMLLoader.load(getClass().getResource("adminMain.fxml"));
						Scene adminMainScene = new Scene(adminMain);
						Shared.stageShow(adminMainScene, "Library System");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						Parent online = FXMLLoader.load(getClass().getResource("online.fxml"));
						Scene onlineScene = new Scene(online);
						Shared.stageShow(onlineScene, "Library System");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				alert.setTitle("오류");
				alert.setContentText("아이디, 비밀번호 좀 확인해라;;");
				alert.show();

			}
		}

		textId.clear();
		textPw.clear();
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
	
	@FXML
	private void gohome() {
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
	
	


}
