package cn.buaaqingyuan.Entity;

public class School {
	private int  id;
	private String name;
	private String place;
	private String type;
	private String properties;
	
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
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	public School()
	{
		
	}
	public School(String name)
	{
		this.name=name;
	}
}
