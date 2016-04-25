package cn.buaaqingyuan.Entity;

public class Organization {
	
	private int id;
	private String orgname;
	private String logolurl;
	private String description;
	private String baikeurl;
	private String superfields;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getLogolurl() {
		return logolurl;
	}
	public void setLogolurl(String logolurl) {
		this.logolurl = logolurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBaikeurl() {
		return baikeurl;
	}
	public void setBaikeurl(String baikeurl) {
		this.baikeurl = baikeurl;
	}
	public String getSuperfields() {
		return superfields;
	}
	public void setSuperfields(String superfields) {
		this.superfields = superfields;
	}
}
