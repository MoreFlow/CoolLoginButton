package pl.droidsononroids.coolloginbutton.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
    @BindView(R.id.success_icon) ImageView successIcon;
    @BindView(R.id.failure_icon) ImageView failureIcon;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    int cx, cy;

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_button, this, true);
        ButterKnife.bind(this);

        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoginButton);
        final String text = array.getString(R.styleable.LoginButton_text);
        final int successColor = array.getColor(R.styleable.LoginButton_success_color, ContextCompat.getColor(context, R.color.colorSuccess));
        final int failureColor = array.getColor(R.styleable.LoginButton_failure_color, ContextCompat.getColor(context, R.color.colorFailure));
        final Drawable successIcon = array.getDrawable(R.styleable.LoginButton_success_icon);
        final Drawable failureIcon = array.getDrawable(R.styleable.LoginButton_failure_icon);
        array.recycle();

        if (text != null) {
            normalView.setText(text);
        }
        successView.setBackgroundColor(successColor);
        failureView.setBackgroundColor(failureColor);
        if (successIcon != null) {
            this.successIcon.setImageDrawable(successIcon);
        }
        if (failureIcon != null) {
            this.failureIcon.setImageDrawable(failureIcon);
        }
    }

    public void revealView(View view) {
        progressBar.setVisibility(GONE);
        float finalRadius = calculateRadius();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            anim.start();
        }
        view.setVisibility(View.VISIBLE);
    }

    public void hideView(final View view) {
        float initialRadius = calculateRadius();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                }
            });
            anim.start();
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public void onSuccess() {
        revealView(successView);
    }

    public void onFailure() {
        revealView(failureView);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideView(failureView);
            }
        }, 2000);
        normalView.setEnabled(true);
    }

    private float calculateRadius() {
        cx = getWidth() / 2;
        cy = getHeight() / 2;
        return (float) Math.hypot(cx, cy);
    }

    public void setProgressCircle() {
        normalView.setEnabled(false);
        progressBar.setVisibility(VISIBLE);
    }
}
