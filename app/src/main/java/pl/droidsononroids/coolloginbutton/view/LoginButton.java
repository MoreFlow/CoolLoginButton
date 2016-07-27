package pl.droidsononroids.coolloginbutton.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsononroids.coolloginbutton.R;

public class LoginButton extends FrameLayout {

    @BindView(R.id.login_button_container) FrameLayout container;
    @BindView(R.id.normal_view) TextView normalView;
    @BindView(R.id.success_view) FrameLayout successView;
    @BindView(R.id.failure_view) FrameLayout failureView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    int cx, cy;

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_button, this, true);
        ButterKnife.bind(this);
    }

    public void revealView(View view) {
        progressBar.setVisibility(GONE);
        cx = getWidth() / 2;
        cy = getHeight() / 2;
        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void hideView(final View view) {
        cx = view.getWidth() / 2;
        cy = view.getHeight() / 2;
        float initialRadius = (float) Math.hypot(cx, cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }

    public void success() {
        container.setEnabled(false);
        revealView(successView);
    }

    public void failure() {
        container.setEnabled(false);
        revealView(failureView);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideView(failureView);
            }
        }, 2000);
        container.setEnabled(true);
    }

    public void setProgressCircle() {
        progressBar.setVisibility(VISIBLE);
    }
}
