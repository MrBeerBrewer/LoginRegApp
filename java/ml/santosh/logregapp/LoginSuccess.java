package ml.santosh.logregapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginSuccess extends AppCompatActivity {
    /*TextView username,password;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        /*username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        //Fetch from bundle
        Bundle bundle = getIntent().getExtras();

        username.setText("Welcome" + bundle.getString("un"));
        password.setText("Password:" + bundle.getString("pwd"));*/

    }
}
