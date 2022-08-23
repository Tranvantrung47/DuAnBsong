package model.bean;

import java.sql.Timestamp;

public class Song {
	private int id;
	private String name;
	private String previewText;
	private String detailText;
	private Timestamp dateCreat;
	private String picture;
	private int counter;
	private Category category;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPreviewText() {
		return previewText;
	}
	public void setPreviewText(String previewText) {
		this.previewText = previewText;
	}
	public String getDetailText() {
		return detailText;
	}
	public void setDetailText(String detailText) {
		this.detailText = detailText;
	}
	public Timestamp getDateCreat() {
		return dateCreat;
	}
	public void setDateCreat(Timestamp dateCreat) {
		this.dateCreat = dateCreat;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Song() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Song(int id, String name, String previewText, String detailText, Timestamp dateCreat, String picture,
			int counter, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.previewText = previewText;
		this.detailText = detailText;
		this.dateCreat = dateCreat;
		this.picture = picture;
		this.counter = counter;
		this.category = category;
	}
	
	
}
