package za.co.emergesoftware.android.eskomapp.entity;

public class UserAccount {
	
	private String lastName;
	private String firstNames;
	private String idNumber;
	private String emailAddress;
	private String confirmedEmailAddress;
	private String cellphoneNumber;
	private String province;
	private boolean acceptedTermsOfUse;
	private boolean acceptedPrivacyPolicy;
	
	private String sessionToken;
	
	public UserAccount(){
		acceptedTermsOfUse = false;
		acceptedPrivacyPolicy = false;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getConfirmedEmailAddress() {
		return confirmedEmailAddress;
	}

	public void setConfirmedEmailAddress(String confirmedEmailAddress) {
		this.confirmedEmailAddress = confirmedEmailAddress;
	}

	public String getCellphoneNumber() {
		return cellphoneNumber;
	}

	public void setCellphoneNumber(String cellphoneNumber) {
		this.cellphoneNumber = cellphoneNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public boolean isAcceptedTermsOfUse() {
		return acceptedTermsOfUse;
	}

	public void setAcceptedTermsOfUse(boolean acceptedTermsOfUse) {
		this.acceptedTermsOfUse = acceptedTermsOfUse;
	}

	public boolean isAcceptedPrivacyPolicy() {
		return acceptedPrivacyPolicy;
	}

	public void setAcceptedPrivacyPolicy(boolean acceptedPrivacyPolicy) {
		this.acceptedPrivacyPolicy = acceptedPrivacyPolicy;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	
}
