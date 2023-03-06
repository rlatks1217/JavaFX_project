package bookView;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import service.Service;

public class SignUp {
	
	private int duplicate;
	private String check_Id;
	@FXML
	private TextField textName;
	@FXML
	private TextField memberId;
	@FXML
	private TextField memberPw;
	@FXML
	private TextField verifyPw;
	@FXML
	private Label label;
	@FXML
	private TextField memberTp;
	@FXML
	private Button checkId; // 중복검사
	@FXML
	private Button memberBtn;
	@FXML
	private Button ShomeBtn;
	
	
	@FXML // 중복검사
	private void checkDuplication() {
		String checkId = memberId.getText();
		
		Service service = new Service(); 
		boolean success = service.checkDuplication(checkId);
		Alert alert = new Alert(AlertType.INFORMATION);

		if (success) {
			duplicate = 1;
			alert.setTitle("중복검사");
			alert.setHeaderText(checkId + "는");
			alert.setContentText("사용 가능한 아이디입니다");
			alert.show();
		} else {
			alert.setTitle("중복검사");
			alert.setHeaderText(checkId + "는");
			alert.setContentText("중복된 아이디입니다");
			alert.show();
		}
	}

	@FXML //비밀번호 확인
	private void checkAccord() {
		String pw = memberPw.getText();
		String accordPw = verifyPw.getText();

		if (pw.equals(accordPw)) {
			label.setText("비밀번호가 일치합니다");
		} else if (pw.equals("") || accordPw.equals("")){
			label.setText("");
		} else {
			label.setText("비밀번호가 일치하지 않습니다");
		}

	}

	@FXML // 회원가입
	private void signUp() {
		String mName = textName.getText();
		String mId = memberId.getText();
		String mPw = memberPw.getText();
		String accordPw = verifyPw.getText();
		String mPhone = memberTp.getText();
		
		Alert alert = new Alert(AlertType.INFORMATION);

		int success = 0;
		if (mPw.equals(accordPw)) {
			if (!mId.equals(check_Id) && duplicate != 0) {
				if (!mPhone.equals("") && mPhone.length() == 11) {
					Service service = new Service(); 
					success = service.signUp(mName, mId, mPw, mPhone);
				} else {
					alert.setTitle("오류");
					alert.setContentText("전화번호를 확인하세요");
					alert.show();
				}
			} else {
				alert.setTitle("오류");
				alert.setContentText("아이디를 확인하세요");
				alert.show();
			}
		} else {
			alert.setTitle("오류");
			alert.setContentText("비밀번호가 일치하지 않습니다");
			alert.show();
		}

		if (success == 1) {
			alert.setTitle("회원가입 성공!");
			alert.setContentText("환영합니다!^^ " + mName + "님");
			alert.show();
			
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
