package app.rkmapp.rkmapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Data {

	private InputStream stream;
	private String result;
	
	//LoadData
	public void LoadData(String _path) {

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(_path);
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			stream = entity.getContent();
		} catch (Exception e) {
			Log.d("LoadData", e.toString());
		}

	}

	//Convertdata
	public void ConvertData(InputStream _stream) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					_stream, "UTF-8"));
			StringBuilder build = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				build.append(line + "\n");
			}
			_stream.close();
			result = build.toString();
		} catch (Exception e) {
			Log.d("ConvertData",e.toString());
		}
	}
	public  InputStream getStream(){
		return this.stream;
	}
	public String getResult(){
		return this.result;
		
	}
}

