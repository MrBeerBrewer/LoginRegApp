package ml.santosh.logregapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button login_button;
    EditText UserName, Password;
    String username, password;
    String login_url = "http://192.168.2.91/al/process_login.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.reg_txt); //Reg new user.
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start the newly created activity.
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });

        builder = new AlertDialog.Builder(MainActivity.this); //Init builder.
        login_button = (Button) findViewById(R.id.bn_login);
        UserName = (EditText) findViewById(R.id.login_name);
        Password = (EditText) findViewById(R.id.login_password);
        //Click listner for login button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if they entered un and pwd
                username = UserName.getText().toString();
                password = Password.getText().toString();
                if (username.equals("")||password.equals("")){
                    builder.setTitle("Error");
                    //Call display Alert. Code it now.
                    displayAlert("Enter a valid username or password.");
                } else{
                    //Auth user using script.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            login_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Handle response, from server its a JSON array
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                if (code.equals("login_failed")){
                                    builder.setTitle("Login failed");
                                    displayAlert(jsonObject.getString("message"));
                                } else{
                                    //Start another activity. Create an intent.
                                    Intent intent = new Intent(MainActivity.this,
                                            LoginSuccess.class);
                                    /*//Some additional data to indent thus Bundle
                                    Bundle bundle = new Bundle();
                                    bundle.putString("un", jsonObject.getString("un"));
                                    bundle.putString("pwd", jsonObject.getString("pwd"));
                                    //Attact bundle to intent
                                    intent.putExtras(bundle);*/
                                    startActivity(intent);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }){
                        //We need to pass username and password, thus override getparams

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //As return type is a map create a map
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("un", username);
                            params.put("pwd", password);
                            return params;
                        }
                    };
                    //Add string request to request queue
                    MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);

                }
            }
        });
    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserName.setText(""); Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }
}
