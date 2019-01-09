package com.example.forest.numbertowordgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.forest.numbertowordgame.Helpers.RedirectHelper;
import com.example.forest.numbertowordgame.Helpers.SharedPref;
import com.example.forest.numbertowordgame.Repositories.Users.UserRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.firstname) EditText firstname;
    @BindView(R.id.lastname) EditText lastname;
    @BindView(R.id.birthday) EditText birthday;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.confirmPassword) EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register)
    public void registerAUser()
    {
        int count = 0 ;
        if  (!password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            password.setError("Password and Confirm password must be the same");
            count++;
        }

        if (password.length() < 6) {
            password.setError("Password must be minimum of 6 characters");
            count++;
        }

        if (confirmPassword.length() < 6) {
            confirmPassword.setError("Password confirmation must be minimum of 6 characters");
            count++;
        }

        if (UserRepository.isUserAlreadyExists(username.getText().toString(),getApplicationContext())) {
            username.setError("Username is already exists");
            count++;
        }

        if (count == 0)
        {
            UserRepository.createUser(
                    firstname.getText().toString(),lastname.getText().toString(),
                    birthday.getText().toString() , password.getText().toString() ,
                    username.getText().toString(),getApplicationContext()
            );
            finish();
        }


    }

    @OnClick(R.id.login)
    public void gotoLogin()
    {
       finish();
    }
}
