package objects;

public class Checkout
{
	private String dateOfCheckout;
	private String dateToReturn;
	private String overdueFees;
	private boolean returnedStatus;
	private long checkedOutISBN;
	private long checkedOutId;
	
	public String getDateOfCheckout() {
		return dateOfCheckout;
	}
	public void setDateOfCheckout(String dateOfCheckout) {
		this.dateOfCheckout = dateOfCheckout;
	}
	public String getDateToReturn() {
		return dateToReturn;
	}
	public void setDateToReturn(String dateToReturn) {
		this.dateToReturn = dateToReturn;
	}
	public String getOverdueFees() {
		return overdueFees;
	}
	public void setOverdueFees(String overdueFees) {
		this.overdueFees = overdueFees;
	}
	public boolean isReturnedStatus() {
		return returnedStatus;
	}
	public void setReturnedStatus(boolean returnedStatus) {
		this.returnedStatus = returnedStatus;
	}
	public long getCheckedOutISBN() {
		return checkedOutISBN;
	}
	public void setCheckedOutISBN(long checkedOutISBN) {
		this.checkedOutISBN = checkedOutISBN;
	}
	public long getCheckedOutId() {
		return checkedOutId;
	}
	public void setCheckedOutId(long checkedOutId) {
		this.checkedOutId = checkedOutId;
	}
	
	
}
