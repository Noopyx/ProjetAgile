package com.noopyx.map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.frenchcomputerguy.rest.*;
import com.frenchcomputerguy.utils.*;
import org.json.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

    }

    public void retourAcceuil(View view) { setContentView(R.layout.acceuil);
    }

    public void goToSignIn(View view) {
        setContentView(R.layout.login);
    }

    public void goToInscription(View view) { setContentView(R.layout.inscription); }

    public void validation (View view) {
        EditText login = (EditText) findViewById(R.id.login);
        EditText pwd = (EditText) findViewById(R.id.pwd);
        TextView output = (TextView) findViewById(R.id.output);

        com.frenchcomputerguy.rest.GetRequest get = new GetRequest("172.18.48.90/v1/userdb/"+login.getText().toString());
        Log.d("YOLO : ", "YOLO : 172.18.48.90/v1/userdb/" + login.getText().toString());
        Map<String,String> param = new HashMap<>();
        param.put("name",login.getText().toString());
        param.put("pwd", pwd.getText().toString());
        get.GET("172.18.48.90/v1/userdb/"+login.getText().toString(),param);

        boolean connextion = false;
        JSONObject json;
        try {
            do {
                json = get.getResponse().getJSONObject();
                if (login.getText().toString().equals(json.get("name")) && pwd.getText().toString().equals(json.get("pwd")))
                    connextion = true;
            }while(json != null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(connextion) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
        else {
            output.setVisibility(View.VISIBLE);
            output.setText("Erreur de login ou de mot de passe");
        }
    }

}

