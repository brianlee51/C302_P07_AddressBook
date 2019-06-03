package sg.edu.rp.c346.c302_p07_addressbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateContactActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnCreate;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCreateOnClick(v);
            }
        });

    }//end onCreate


    private void btnCreateOnClick(View v) {
        //TODO: call createContact.php to save new contact details
        client = new AsyncHttpClient();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String mobile = etMobile.getText().toString();
        if (firstName.length() == 0 || lastName.length() == 0 || mobile.length() == 0) {
            Toast.makeText(getBaseContext(), "Fields cannot be empty", Toast.LENGTH_LONG).show();
        } else {
            RequestParams params = new RequestParams();
            params.add("firstname", firstName);
            params.add("lastname", lastName);
            params.add("mobile", mobile);
            client.post("http://10.0.2.2/C302_P07/addContact.php", params, new JsonHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        finish();
    }//end btnCreateOnClick
}
