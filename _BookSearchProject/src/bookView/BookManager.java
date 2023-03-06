package bookView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;
import vo.Book;

public class BookManager implements Initializable {
	
	@FXML
	private TableView<Book> tableView1;
	@FXML
	private TableColumn<Book, String> userId1;
	@FXML
	private TableColumn<Book, String> rentalDate1;
	@FXML
	private TableColumn<Book, Integer> bisbn1;
	@FXML
	private TableColumn<Book, String> btitle1;
	@FXML
	private TableColumn<Book, Integer> bprice1;
	@FXML
	private TableColumn<Book, String> bauthor1;
	@FXML
	private TableColumn<Book, Integer> bpage1;
	@FXML
	private TableColumn<Book, String> bpublisher1;
	@FXML
	private TableColumn<Book, String> btranslator1;
	@FXML
	private TableColumn<Book, String> returnDate1;
	@FXML
	private TableColumn<Book, String> status1;
	@FXML
	private TableView<Book> tableView2;
	@FXML
	private TableColumn<Book, String> userId2;
	@FXML
	private TableColumn<Book, String> rentalDate2;
	@FXML
	private TableColumn<Book, Integer> bisbn2;
	@FXML
	private TableColumn<Book, String> btitle2;
	@FXML
	private TableColumn<Book, Integer> bprice2;
	@FXML
	private TableColumn<Book, String> bauthor2;
	@FXML
	private TableColumn<Book, Integer> bpage2;
	@FXML
	private TableColumn<Book, String> bpublisher2;
	@FXML
	private TableColumn<Book, String> btranslator2;
	@FXML
	private TableColumn<Book, String> returnDate2;
	@FXML
	private TableColumn<Book, String> status2;
	@FXML
	private Button home;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Service service = new Service();

		rentalDate1.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
		bisbn1.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
		btitle1.setCellValueFactory(new PropertyValueFactory<>("btitle"));
		bprice1.setCellValueFactory(new PropertyValueFactory<>("bprice"));
		bauthor1.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
		bpage1.setCellValueFactory(new PropertyValueFactory<>("bpage"));
		bpublisher1.setCellValueFactory(new PropertyValueFactory<>("bpublisher"));
		btranslator1.setCellValueFactory(new PropertyValueFactory<>("btranslator"));
		returnDate1.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
		status1.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		rentalDate2.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
		bisbn2.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
		btitle2.setCellValueFactory(new PropertyValueFactory<>("btitle"));
		bprice2.setCellValueFactory(new PropertyValueFactory<>("bprice"));
		bauthor2.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
		bpage2.setCellValueFactory(new PropertyValueFactory<>("bpage"));
		bpublisher2.setCellValueFactory(new PropertyValueFactory<>("bpublisher"));
		btranslator2.setCellValueFactory(new PropertyValueFactory<>("btranslator"));
		returnDate2.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
		status2.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		ArrayList<ObservableList<Book>> list = service.getUserRentBookAll();
		
		tableView1.setItems(list.get(0));
		tableView2.setItems(list.get(1));
		
		tableView1.setRowFactory(e -> {
			TableRow<Book> row = new TableRow<>();
			row.setOnMouseClicked(e1 -> {
				String bookTitle = (row.getItem()).getBtitle();
				String id = (row.getItem()).getId();
				if (e1.getClickCount() > 1 && Shared.getFieldId() != null) {
					Shared.setDetail(new Stage());

					Book bookDetail = service.selectBookDetail(bookTitle);

					StringBuilder sb = new StringBuilder();
					sb.append("ISBN | " + id + "\n");
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
					ManagerReturn.setId(id);

					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("managerReturn.fxml"));

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
		
		tableView2.setRowFactory(e -> {
			TableRow<Book> row = new TableRow<>();
			row.setOnMouseClicked(e1 -> {
				String bookTitle = (row.getItem()).getBtitle();
				String id = (row.getItem()).getId();
				if (e1.getClickCount() > 1 && Shared.getFieldId() != null) {
					Shared.setDetail(new Stage());

					Book bookDetail = service.selectBookDetail(bookTitle);

					StringBuilder sb = new StringBuilder();
					sb.append("USERID | " + id + "\n");
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
					ManagerReturn.setId(id);

					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("managerReturn.fxml"));

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
				online = FXMLLoader.load(getClass().getResource("AdminMain.fxml"));
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



}
