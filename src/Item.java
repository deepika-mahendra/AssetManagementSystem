
public class Item {
	private int Id;
	private String Name;
	public Item (int id ,String name) {
		this.Id=id;
		this.Name=name;
	}
	public int getId() {
		return this.Id;
		
	}
	public String getName() {
		return this.Name;
	}
	public void setId(int id) {
		this.Id=id;
		
	}
	public void setName(String name)
	{
		this.Name=name;
	}
	
}
