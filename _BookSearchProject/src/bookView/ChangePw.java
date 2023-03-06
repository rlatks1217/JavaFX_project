package bookView;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import service.Service;

public class ChangePw {
	
	@FXML
	private Button changeBtn;
	
	@FXML
	private TextField changePw; 
	
	@FXML
	private void changePw() {
		String password = changePw.getText();
		Service service = new Service();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		boolean equal =service.checkPw(password);
		if (equal) {
			alert.setTitle("비밀번호 중복");
			alert.setContentText("동일한 비밀번호로는 변경할 수 없습니다.");
			alert.show();
			return;
		} else {
			int result = service.changePw(password);
			if (result == 1) {
				alert.setTitle("변경 성공");
				alert.setContentText(Shared.getFieldId() + "님의 비밀번호가 변경되었습니다");
				alert.show();
			} else {
				alert.setTitle("변경 실패");
				alert.setContentText(Shared.getFieldId() + "로 등록된 정보가 없습니다");
				alert.show();
				
			}
			
			try {
				Parent myPage = FXMLLoader.load(getClass().getResource("myPage.fxml"));
				Scene myPageScene = new Scene(myPage);
				Shared.stageShow(myPageScene, "My Page");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
