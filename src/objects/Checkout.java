package objects;

public class Checkout
{
	private int checkoutId;
	private String dateOfCheckout;
	private String dateToReturn;
	private String overdueFees;
	private boolean returnedStatus;
	private int checkedOutISBN;
	private int memberCheckedOutId;
	
	public int getCheckoutId() {
		return checkoutId;
	}
	public void setCheckoutId(int checkoutId) {
		this.checkoutId = checkoutId;
	}
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
	public int getCheckedOutISBN() {
		return checkedOutISBN;
	}
	public void setCheckedOutISBN(int checkedOutISBN) {
		this.checkedOutISBN = checkedOutISBN;
	}
	public int getMemberCheckedOutId() {
		return memberCheckedOutId;
	}
	public void setMemberCheckedOutId(int checkedOutId) {
		this.memberCheckedOutId = checkedOutId;
	}
	
	
}
