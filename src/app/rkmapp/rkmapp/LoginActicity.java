package app.rkmapp.rkmapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class LoginActicity extends Activity { // StatusCallback {

	private Button btnLogin;
	private LoginButton loginButton;
	private UiLifecycleHelper uiHelper;
	private EditText txtUser, txtPass;

	// ProgressDialog dialog;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// uiHelper = new UiLifecycleHelper(this, this);
		// uiHelper.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);

		// Permission StrictMode
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		final AlertDialog.Builder ad = new AlertDialog.Builder(this);

		Viewmacthing();

		// loginButton.setPublishPermissions(Arrays.asList("email",
		// "public_action", "public_stream"));
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://www.playslab.com/android/checkLogin.php";
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("strUser", txtUser.getText()
						.toString()));
				params.add(new BasicNameValuePair("strPass", txtPass.getText()
						.toString()));
				String resultServer = getHttpPost(url, params);

				String strStatusID = "0";
				String strMemberID = "0";
				String strError = "Unknow Status!";

				JSONObject c;
				try {
					c = new JSONObject(resultServer);
					strStatusID = c.getString("StatusID");
					strMemberID = c.getString("MemberID");
					strError = c.getString("Error");

				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (strStatusID.equals("0")) {
					// Dialog
					ad.setTitle("Error! ");
					ad.setIcon(android.R.drawable.btn_star_big_on);
					ad.setPositiveButton("Close", null);
					ad.setMessage(strError);
					ad.show();
					txtUser.setText("");
					txtPass.setText("");
				} else {
					Toast.makeText(LoginActicity.this, "Login OK",
							Toast.LENGTH_SHORT).show();
					Intent newActivity = new Intent(LoginActicity.this,
							MainActivity.class);
					newActivity.putExtra("MemberID", strMemberID);
					startActivity(newActivity);
				}

			}

			public String getHttpPost(String url, List<NameValuePair> params) {
				StringBuilder str = new StringBuilder();
				HttpClient client = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
					HttpResponse response = client.execute(httpPost);
					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					if (statusCode == 200) { // Status OK
						HttpEntity entity = response.getEntity();
						InputStream content = entity.getContent();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(content));
						String line;
						while ((line = reader.readLine()) != null) {
							str.append(line);
						}
					} else {
						Log.e("Log", "Failed to download result..");
					}

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return str.toString();
			}
		});

	}

	// @Override
	// protected void onPause() {
	// uiHelper.onPause();
	// super.onPause();
	// }

	// @Override
	// protected void onResume() {
	// uiHelper.onResume();
	// super.onResume();
	// }

	// @Override
	// protected void onSaveInstanceState(Bundle outState) {
	// Session session = Session.getActiveSession();
	// Session.saveSession(session, outState);
	// super.onSaveInstanceState(outState);
	// uiHelper.onSaveInstanceState(outState);
	// }

	// @Override
	// protected void onDestroy() {
	// uiHelper.onDestroy();
	// super.onDestroy();
	// }

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// uiHelper.onActivityResult(requestCode, resultCode, data);
	// }

	private void Viewmacthing() {

		btnLogin = (Button) findViewById(R.id.btnLogin);
		loginButton = (LoginButton) findViewById(R.id.main_login_button);
		txtUser = (EditText) findViewById(R.id.txtEmail);
		txtPass = (EditText) findViewById(R.id.txtPassword);
	}

	// public LoginActicity() {
	// Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
	// }

	// @Override
	// public void call(Session session, SessionState state, Exception
	// exception) {
	// if (session.isOpened()) {
	// Intent intent = new Intent(this, MainActivity.class);
	// this.startActivity(intent);
	// LoginActicity.this.finish();
	// } else if (session.isClosed()) {
	// Intent intent = new Intent(this, LoginActicity.class);
	// this.startActivity(intent);
	// LoginActicity.this.finish();

	// }
	// }

}
