package vo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Book {
	
	private String rentalDate;
	private String bisbn;
	private String btitle;
	private int bprice;
	private String bdate;
	private String bauthor;
	private int bpage;
	private String bpublisher;
	private String btranslator;
	private String returnDate;
	private String status;
	private String bimgbase64;
	private ImageView bimgView;
	private String id;
	
	public Book() {
		
	}
	
	public Book(String rendtalDate, String bisbn, String btitle, int bprice, String bauthor, int bpage,
			String bpublisher, String btranslator) {
		super();
		this.rentalDate = rendtalDate;
		this.bisbn = bisbn;
		this.btitle = btitle;
		this.bprice = bprice;
		this.bauthor = bauthor;
		this.bpage = bpage;
		this.bpublisher = bpublisher;
		this.btranslator = btranslator;
	}
	
	public Book(String bisbn, String btitle, int bprice, String bdate, String bauthor, int bpage,
			String bpublisher, String btranslator) {
		super();
		this.bisbn = bisbn;
		this.btitle = btitle;
		this.setBdate(bdate);
		this.bprice = bprice;
		this.bauthor = bauthor;
		this.bpage = bpage;
		this.bpublisher = bpublisher;
		this.btranslator = btranslator;
	}

	public Book(String bisbn, String btitle, int bprice, String bauthor) {
		this.bisbn = bisbn;
		this.btitle = btitle;
		this.bprice = bprice;
		this.bauthor = bauthor;
	}
	
	public Book(String bisbn, String btitle, int bprice, String bauthor, String rentalDate) {
		this.bisbn = bisbn;
		this.btitle = btitle;
		this.bprice = bprice;
		this.bauthor = bauthor;
		this.rentalDate = rentalDate;
	}
	
	public Book(String bisbn, String btitle, int bprice, String bauthor, int bpage,
			String bpublisher, String btranslator, String bimgbase64) {
		super();
		this.bisbn = bisbn;
		this.btitle = btitle;
		this.bprice = bprice;
		this.bauthor = bauthor;
		this.bpage = bpage;
		this.bpublisher = bpublisher;
		this.btranslator = btranslator;
		this.bimgbase64 = bimgbase64;
		setbBase64(bimgbase64.split(",")[1]);
	}
	
	public void setbBase64(String bimgbase64) {
		
		try {
			Decoder decoder = Base64.getDecoder(); // 바이트배열로의 변환을 위해 필요함
			byte[] imgByte = decoder.decode(bimgbase64); 
			ByteArrayInputStream bis = new ByteArrayInputStream(imgByte); 
			
			ImageView bimgView = new ImageView(new Image(bis)); //byte타입의 데이터로 객체를 만들기 위해 변환이 필요한 것
			this.bimgView = bimgView;
		
			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	public String getbImgbase64() {
		return bimgbase64;
	}

	public void setbImgbase64(String bimgbase64) {
		this.bimgbase64 = bimgbase64;
	}

	public ImageView getBimgView() {
		return bimgView;
	}

	public void setBimgView(ImageView bimgView) {
		this.bimgView = bimgView;
	}

	public String getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(String rendtalDate) {
		this.rentalDate = rendtalDate;
	}

	public String getBisbn() {
		return bisbn;
	}

	public void setBisbn(String bisbn) {
		this.bisbn = bisbn;
	}

	public String getBtitle() {
		return btitle;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public int getBprice() {
		return bprice;
	}

	public void setBprice(int bprice) {
		this.bprice = bprice;
	}

	public String getBauthor() {
		return bauthor;
	}

	public void setBauthor(String bauthor) {
		this.bauthor = bauthor;
	}

	public int getBpage() {
		return bpage;
	}

	public void setBpage(int bpage) {
		this.bpage = bpage;
	}

	public String getBpublisher() {
		return bpublisher;
	}

	public void setBpublisher(String bpublisher) {
		this.bpublisher = bpublisher;
	}

	public String getBtranslator() {
		return btranslator;
	}

	public void setBtranslator(String btranslator) {
		this.btranslator = btranslator;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
