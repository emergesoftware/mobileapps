package za.co.emergesoftware.android.eskomapp.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public abstract class HttpWebClient {
	
	public static final String GET = "GET";
	public static final String POST = "POST";
	
	// constant values
	public static final String WEB_SERVICE_BASE_URL = 
			"http://cloud.eswebserv.co.za/app/eskomapp";
	public static final int WEB_SERVICE_PORT = 9000;
	public static final int TIMEOUT = 60;
	
	// variables
	protected HttpClient client;
	protected HttpResponse response;
	protected HttpRequest request; 
	protected HttpEntity body;
	protected StatusLine statusLine;
	
	protected static Context context;
	protected List<WebClientEventListener> listeners;
	protected String url;
	protected String method;
	
	/**
	 * Constructor
	 */
	protected HttpWebClient() {
		context = null;
		listeners = new ArrayList<WebClientEventListener>();
	}
	
	/**
	 * Starts an HTTP client connection
	 * 
	 * @param method
	 * @param url
	 */
	public void connect(String method, String url) {
		this.url = url;
		this.method = method;
		
		new AsyncHttpConnectionTask().execute(new Void[0]); 
	}
	
	/**
	 * An asynchronous worker thread that
	 * initiates an HTTP connection and
	 * awaits an HTTP responses then fires off
	 * events once completed.
	 * 
	 * @author tsepo
	 *
	 */
	private class AsyncHttpConnectionTask extends AsyncTask<Void, Void, WebClientResponse> {

		private boolean successful;
		private WebClientEvent event;
		private Exception error;
		private WebClientResponse result;
		
		public AsyncHttpConnectionTask() {
			this.successful = false;
		}
		
		@Override
		protected WebClientResponse doInBackground(Void... arg0) {
			
			client = new DefaultHttpClient();
			
			if (method.equalsIgnoreCase(GET))
				request = new HttpGet(url);
			else if (method.equalsIgnoreCase(POST))
				request = new HttpPost(url);
			
			if (body != null && request instanceof HttpPost) {
				((HttpPost)request).setEntity(body);
			}
			
			try {
				response = client.execute((HttpUriRequest)request);
				statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				
				if (statusCode == StatusCode.OK) {
					HttpEntity entity = response.getEntity();
					String contentType = entity.getContentType().getValue();
					
					InputStream input = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					StringBuilder content = new StringBuilder();
					
					String line = null;
					while ((line = reader.readLine()) != null) {
						content.append(line);
					}
					
					reader.close();
					
					result = new WebClientResponse();
					
					if (contentType.equals(MimeType.JSON)) {
						JSONObject object = new JSONObject(content.toString()); 
						result.setJsonObject(object);
					}
					
					else if (contentType.equals(MimeType.PLAIN_TEXT)) {
						result.setPlainText(content.toString());
					}
					
					successful = true;
					
				}
			} 
			
			catch (ClientProtocolException e) {
				Log.d("DEBUG", e.getMessage());
				error = e;
			}
			
			catch (IOException e) {
				Log.d("DEBUG", e.getMessage());
				error = e;
			}
			
			catch (JSONException e) {
				Log.d("DEBUG", e.getMessage());
				error = e;
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(WebClientResponse result) {
			
			event = new WebClientEvent(result, error);
			notifyHttpWebClientCompleted(event, successful);
			
			super.onPostExecute(result);
		}
		
	}
	
	/**
	 * Sets the content of the body of
	 * the HTTP request
	 * 
	 * @param content
	 * @throws UnsupportedEncodingException
	 */
	public void setRequestBody(String content) throws UnsupportedEncodingException {
		body = new StringEntity(content); 
	}
	
	/**
	 * Registers a new asynchronous web client event listener
	 * 
	 * @param l
	 */
	public void addAsyncWebClientEventListener(WebClientEventListener l) {
		if (l != null && !listeners.contains(l))
			listeners.add(l);
	}
	
	/**
	 * De-registers an existing asynchronous web client listener
	 * 
	 * @param l
	 */
	public void removeAsyncWebClientEventListener(WebClientEventListener l) {
		if (l != null && listeners.contains(l))
			listeners.remove(l);
	}
	
	/**
	 * Notifies and fires off the event when
	 * the http client as completed
	 * 
	 * @param e
	 * @param result
	 */
	protected void notifyHttpWebClientCompleted(WebClientEvent e, boolean successful) {
		if (listeners != null && !listeners.isEmpty()) {
			Iterator<WebClientEventListener> iterator = listeners.iterator();
			while(iterator.hasNext()) {
				WebClientEventListener listener = iterator.next();
				
				if (successful)
					listener.onSuccessful(context, e);
				else
					listener.onFailure(context, e);
			}
		}
	}
	
	/**
	 * Returns an absolute path from
	 * a given relative path.
	 * 
	 * @param url
	 * @return
	 */
	public static String getAbsoluteUrl(String url) {
		 if (url.startsWith("/"))
			 url = url.substring(1);
		 return WEB_SERVICE_BASE_URL + "/" + url;
	 }

}
