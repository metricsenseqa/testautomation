package factories;

public class DataFactory {

	private String taskid;
	private String name;
	private String phone;
	private String city;
	
	public String getTaskId() {
		return taskid;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getCity() {
		return city;
	}
	
	public void setTaskId(String taskid) {
		this.taskid = taskid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
