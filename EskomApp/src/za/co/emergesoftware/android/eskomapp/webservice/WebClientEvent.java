package za.co.emergesoftware.android.eskomapp.webservice;

public class WebClientEvent {
	
	private Exception error;
	private WebClientResponse result;
	
	public WebClientEvent(WebClientResponse result, Exception error) {
		this.error = error;
		this.result = result;
	}

	public Exception getError() {
		return error;
	}

	public WebClientResponse getResult() {
		return result;
	}
	
	
}
