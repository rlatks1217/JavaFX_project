package bookView;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Shared {
	private static Stage stage;
	private static Stage detail;
	private static String fieldId;
	
	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		Shared.stage = stage;
	}

	public static Stage getDetail() {
		return detail;
	}
	
	public static void setDetail(Stage detail) {
		Shared.detail = detail;
	}
	

	public static String getFieldId() {
		return fieldId;
	}

	public static void setFieldId(String fieldId) {
		Shared.fieldId = fieldId;
	}

	
	public static void stageShow(Scene fxml, String title) {
		stage.setTitle(title);
		stage.setScene(fxml);
		stage.show();
	}

}
