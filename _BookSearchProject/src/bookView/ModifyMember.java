package bookView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.Service;

public class ModifyMember implements Initializable{
	
	@FXML
	private TextField updateName;
	@FXML
	private TextField updateId;
	@FXML
	private TextField updatePw;
	@FXML
	private TextField tel;
	@FXML
	private Button modifycomplete;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		updateName.setText(MemberManager.user.getUserName());
		updateId.setText(MemberManager.user.getId());
		updatePw.setText(MemberManager.user.getPw());
		tel.setText(MemberManager.user.getUserPhoneNum());
	}
	
	@FXML
	private void modifycomplete () {
		String name = updateName.getText();
		String id = updateId.getText();
		String pw = updatePw.getText();
		String telephone = tel.getText();
		
		Service service = new Service();
		
		int result = service.modifycomplete(name, id, pw, telephone);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		if (result == 1) {
			alert.setTitle("수정완료");
			alert.setContentText("수정이 완료되었습니다");
			alert.show();
			
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
			alert.setTitle("수정완료");
			alert.setContentText("수정이 완료되었습니다");
			alert.show();
		}
	}

}
