package app.rkmapp.rkmapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HomeFragment extends Fragment {

	public HomeFragment() {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

	}

	//String url = "http://www.playslab.com/android/getJSON.php";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		return rootView;
	}
}

	/*@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final ListView lisView1 = (ListView) getActivity().findViewById(
				R.id.listView1);

		try {

			JSONArray data = new JSONArray(getJSONUrl(url));

			final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			for (int i = 0; i < data.length(); i++) {
				JSONObject c = data.getJSONObject(i);

				map = new HashMap<String, String>();
				map.put("MemberID", c.getString("MemberID"));
				map.put("Name", c.getString("Name"));
				map.put("Tel", c.getString("Tel"));
				MyArrList.add(map);

			}

			SimpleAdapter sAdap;
			sAdap = new SimpleAdapter(getActivity(), MyArrList,
					R.layout.activity_column, new String[] { "MemberID",
							"Name", "Tel" }, new int[] { R.id.ColMemberID,
							R.id.ColName, R.id.ColTel });
			lisView1.setAdapter(sAdap);

			final AlertDialog.Builder viewDetail = new AlertDialog.Builder(
					getActivity());
			// OnClick Item
			lisView1.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> myAdapter, View myView,
						int position, long mylng) {

					String sMemberID = MyArrList.get(position).get("MemberID")
							.toString();
					String sName = MyArrList.get(position).get("Name")
							.toString();
					String sTel = MyArrList.get(position).get("Tel").toString();

					// String sMemberID = ((TextView)
					// myView.findViewById(R.id.ColMemberID)).getText().toString();
					// String sName = ((TextView)
					// myView.findViewById(R.id.ColName)).getText().toString();
					// String sTel = ((TextView)
					// myView.findViewById(R.id.ColTel)).getText().toString();

					viewDetail.setIcon(android.R.drawable.btn_star_big_on);
					viewDetail.setTitle("Member Detail");
					viewDetail.setMessage("MemberID : " + sMemberID + "\n"
							+ "Name : " + sName + "\n" + "Tel : " + sTel);
					viewDetail.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
					viewDetail.show();

				}
			});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getJSONUrl(String url) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Download OK
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

}
*/