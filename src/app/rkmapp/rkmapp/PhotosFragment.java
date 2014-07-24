package app.rkmapp.rkmapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
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
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PhotosFragment extends Fragment {

	private ProgressDialog progressDialog;

	public PhotosFragment() {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	String url = "http://www.playslab.com/android/getImage.php";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_photos, container,
				false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		final ListView lstView1 = (ListView) getActivity().findViewById(
				R.id.listView1);

		try {
			JSONArray data = new JSONArray(getJSONUrl(url));

			final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			for (int i = 0; i < data.length(); i++) {
				JSONObject c = data.getJSONObject(i);
				map = new HashMap<String, String>();
				map.put("ImageID", c.getString("ImageID"));
				map.put("ImageDesc", c.getString("ImageDesc"));
				map.put("ImagePath", c.getString("ImagePath"));
				MyArrList.add(map);
			}

			lstView1.setAdapter(new ImageAdapter(getActivity(), MyArrList));

			final AlertDialog.Builder imageDialog = new AlertDialog.Builder(
					getActivity());
			final LayoutInflater inflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// OnClick
			lstView1.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					switch (position) {
					case 0:
						Intent intent = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://maps.google.com/maps?f=d&daddr=13.6652500,102.5478667?z=18"));
						intent.setComponent(new ComponentName(
								"com.google.android.apps.maps",
								"com.google.android.maps.MapsActivity"));
						startActivity(intent);
						break;
					default:
						break;
					}

				}

			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<HashMap<String, String>> MyArr = new ArrayList<HashMap<String, String>>();

		public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list) {
			// TODO Auto-generated method stub
			context = c;
			MyArr = list;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return MyArr.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.photo, null);
			}

			// ColImage
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.ColImgPath);
			imageView.getLayoutParams().height = 100;
			imageView.getLayoutParams().width = 100;
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			try {
				imageView.setImageBitmap(loadBitmap(MyArr.get(position).get(
						"ImagePath")));
			} catch (Exception e) {
				// When Error
				imageView
						.setImageResource(android.R.drawable.ic_menu_report_image);
			}

			// ColPosition
			TextView txtPosition = (TextView) convertView
					.findViewById(R.id.ColImgID);
			txtPosition.setPadding(10, 0, 0, 0);
			txtPosition.setText("ID : " + MyArr.get(position).get("ImageID"));

			// ColPicname
			TextView txtPicName = (TextView) convertView
					.findViewById(R.id.ColImgDesc);
			txtPicName.setPadding(50, 0, 0, 0);
			txtPicName
					.setText("Desc : " + MyArr.get(position).get("ImageDesc"));

			return convertView;

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
				Log.e("Log", "Failed to download file..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	private static final String TAG = "ERROR";
	private static final int IO_BUFFER_SIZE = 4 * 1024;

	public static Bitmap loadBitmap(String url) {
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;

		try {
			in = new BufferedInputStream(new URL(url).openStream(),
					IO_BUFFER_SIZE);

			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
			copy(in, out);
			out.flush();

			final byte[] data = dataStream.toByteArray();
			BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inSampleSize = 1;

			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
					options);
		} catch (IOException e) {
			Log.e(TAG, "Could not load Bitmap from: " + url);
		} finally {
			closeStream(in);
			closeStream(out);
		}

		return bitmap;
	}

	private static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				android.util.Log.e(TAG, "Could not close stream", e);
			}
		}
	}

	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}
}
