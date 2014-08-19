package za.co.emergesoftware.android.eskomapp.webservice;

import org.json.JSONObject;
import org.w3c.dom.Document;

public class WebClientResponse {
	
	private JSONObject jsonObject;
	private Document xmlDocument;
	private String plainText;
	
	private String mimeType;
	
	public WebClientResponse() {
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public Document getXmlDocument() {
		return xmlDocument;
	}

	public void setXmlDocument(Document xmlDocument) {
		this.xmlDocument = xmlDocument;
	}

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
}
