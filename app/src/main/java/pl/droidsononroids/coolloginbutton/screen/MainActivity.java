package pl.droidsononroids.coolloginbutton.screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsononroids.coolloginbutton.R;
import pl.droidsononroids.coolloginbutton.api.LoginManager;
import pl.droidsononroids.coolloginbutton.view.LoginButton;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.email_edit_text) EditText mEmailEditText;
    @BindView(R.id.login_button) LoginButton loginButton;
    private LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginManager = new LoginManager();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mLoginManager.setLoginListener(new LoginManager.LoginListener() {
            @Override
            public void loginSuccess() {
                loginButton.onSuccess();
            }

            @Override
            public void loginFailure() {
                loginButton.onFailure();
            }
        });
    }

    @OnClick(R.id.normal_view)
    public void login() {
        loginButton.setProgressCircle();
        mLoginManager.performLogin(mEmailEditText.getText().toString());
    }

    @OnClick(R.id.content_main)
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
}