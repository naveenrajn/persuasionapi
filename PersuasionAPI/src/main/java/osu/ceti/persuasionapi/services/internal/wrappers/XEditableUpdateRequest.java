package osu.ceti.persuasionapi.services.internal.wrappers;

public class XEditableUpdateRequest {

	String name; //contains the name of the field updated
	String value; //contains the new value
	String pk; //contains the primary key reference to the field updated
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
}
