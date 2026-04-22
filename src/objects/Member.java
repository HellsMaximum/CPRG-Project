package objects;

public class Member 
{
	long id;
	String name;
	String totalOverdueFees;
	boolean canCheckout;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTotalOverdueFees() {
		return totalOverdueFees;
	}
	public void setTotalOverdueFees(String totalOverdueFees) {
		this.totalOverdueFees = totalOverdueFees;
	}
	public boolean isCanCheckout() {
		return canCheckout;
	}
	public void setCanCheckout(boolean canCheckout) {
		this.canCheckout = canCheckout;
	}
	
}
