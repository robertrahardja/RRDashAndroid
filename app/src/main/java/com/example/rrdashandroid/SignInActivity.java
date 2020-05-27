package com.example.rrdashandroid;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {

    Button customerButton, driverButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        customerButton = (Button)findViewById(R.id.button_customer);
        driverButton = (Button)findViewById(R.id.button_driver);

        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                customerButton.setTextColor(getResources().getColor(android.R.color.white));

                driverButton.setBackgroundColor(getResources().getColor(android.R.color.white));
                driverButton.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                driverButton.setTextColor(getResources().getColor(android.R.color.white));

                customerButton.setBackgroundColor(getResources().getColor(android.R.color.white));
                customerButton.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        Button buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("public_profile", "email"));

                ColorDrawable customerButtonColor = (ColorDrawable) customerButton.getBackground();

//                if (customerButtonColor.getColor() == getResources().getColor(R.color.colorAccent)){
//                    Intent intent = new Intent(getApplicationContext(), CustomerMainActivity.class);
//                    startActivity(intent);
//                }else {
//                    Intent intent = new Intent(getApplicationContext(), DriverMainActivity.class);
//                    startActivity(intent);
//                }

            }
        });

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d("Facebook Token", loginResult.getAccessToken().getToken());

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.d("FACEBOOK DETAILS", object.toString());
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
