package hk.opensource.android.openvote.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends ActionBarActivity {
    private RadioGroup radioGroup;
    private RadioButton radioChoice;
    private Button btnSubmit;
    private EditText etHKID;
    private String HKID;
    private int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadQuestions();

        addListenerOnButton();

        etHKID = (EditText) findViewById(R.id.et_HKID);
    }

    // parse JSON

    public void loadQuestions() {


    }

    private void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioChoice = (RadioButton) findViewById(selectedId);

                Toast.makeText(MainActivity.this,
                        radioChoice.getText(), Toast.LENGTH_SHORT).show();

                encryteHKID();

//                new PostTask().execute();
//                postSubmit();
            }

        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void encryteHKID() {

        HKID = etHKID.getText().toString();
        Log.d("HKID ", HKID);
        try {
            // md5(*pt) [hex format]
            byte[] md5Byte = Encrypt.encryptMD5(HKID);
            String md5Message = Encrypt.convertToHex(Encrypt.encryptMD5(HKID));
            Log.d("MD5(HKID) ", md5Message);

            // sha(md5(*pt) [hex format]
            String sha1Message = Encrypt.convertToHex(Encrypt.md5ToSHA1(md5Byte));
            Log.d("SHA1(MD5(HKID)) ", sha1Message);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void postSubmit() throws JSONException {

        RestClient.post("submit", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray timeline) {
                // Pull out the first event on the public timeline
//                JSONObject firstEvent = timeline.get(0);
//                String tweetText = firstEvent.getString("text");

                // Do something with the response
                System.out.println("...");
            }
        });

    }

//    private class PostTask extends AsyncTask<URL, Integer, Long> {
//        protected Long doInBackground(URL... urls) {
//            long totalSize = 0;
//
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost("http://localhost:8080/post.php");
//
//            try {
//                // Add your data
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//                nameValuePairs.add(new BasicNameValuePair("HKID", HKID));
//                nameValuePairs.add(new BasicNameValuePair("choice", radioChoice.getText().toString()));
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                // Execute HTTP Post Request
//                HttpResponse response = httpclient.execute(httppost);
//
//            } catch (ClientProtocolException e) {
//                // TODO Auto-generated catch block
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//            }
//
//            return totalSize;
//        }
//
////        protected void onProgressUpdate(Integer... progress) {
////            Toast.makeText(MainActivity.this, "onProgress...", Toast.LENGTH_SHORT).show();
////        }
//
//        protected void onPostExecute(Long result) {
//            Toast.makeText(MainActivity.this, "Submitted! Thanks!", Toast.LENGTH_SHORT).show();
//        }
//    }

}
