package com.example.creitiive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.creitiive.model.Params;
import com.example.creitiive.response.TokenResponse;
import com.example.creitiive.viewModel.MainActivityViewModel;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;


// https://www.creitive.com/careers/development/mobile-project
// email : candidate@creitive.com
// pass: 1234567

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @Email
    @Pattern(regex = "candidate@creitive.com")
    private EditText edtEmail;

    @NotEmpty
    @Password
    @Pattern(regex = "1234567")
    private EditText edtPassword;

    private Validator validator;
    MainActivityViewModel mainActivityViewModel;

    String email;
    String password;

    public static final String PREF_TOKEN = "PREF_TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edit_text_email);
        edtPassword = findViewById(R.id.edit_text_password);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public boolean login(View view) {
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        validator.validate();
        return true;
    }

    @Override
    public void onValidationSucceeded() {
        Params params = new Params(email, password);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.tokenLiveData.observe(this, new Observer<TokenResponse>() {
            @Override
            public void onChanged(TokenResponse tokenResponse) {
                if (tokenResponse == null) return;
                if(tokenResponse.getResponseCode() !=0){
                    //...
                }
                if(tokenResponse.getThrowable() != null){
                    Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mainActivityViewModel.getToken(params);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREF_TOKEN, MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if(!token.equals("")){
            BlogsActivity.start(MainActivity.this);
            finish();
        }
    }
}