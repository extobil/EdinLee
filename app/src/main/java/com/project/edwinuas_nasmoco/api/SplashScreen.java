package com.project.edwinuas_nasmoco.api;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.edwinuas_nasmoco.R;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DURATION = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final ImageView logo = findViewById(R.id.logoImageView);
        final ImageView circleEffect = findViewById(R.id.circleEffectImageView);

        // 1. Logo langsung tampil di tengah
        logo.setVisibility(View.VISIBLE);
        circleEffect.setVisibility(View.INVISIBLE);

        // 2. Load animasi
        Animation circleZoomHalf = AnimationUtils.loadAnimation(this, R.anim.circle_zoom_half);
        Animation circleZoomFull = AnimationUtils.loadAnimation(this, R.anim.circle_zoom_full);
        Animation logoZoom = AnimationUtils.loadAnimation(this, R.anim.zoom_logo); // Zoom pol untuk logo

        // 3. Jalankan animasi zoom setengah pada circle
        new Handler().postDelayed(() -> {
            circleEffect.setVisibility(View.VISIBLE);
            circleEffect.startAnimation(circleZoomHalf);
        }, 500); // Delay sedikit agar logo terlihat dulu

        // 4. Listener saat animasi setengah selesai
        circleZoomHalf.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Ganti warna pinggiran circle
                circleEffect.setImageResource(R.drawable.circle_shape_update);

                // Tunggu 1 detik lalu lanjut zoom full
                new Handler().postDelayed(() -> {
                    circleEffect.startAnimation(circleZoomFull);
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // 5. Listener ketika zoom full selesai → mulai logo zoom
        circleZoomFull.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Circle hilang
                circleEffect.setVisibility(View.GONE);

                // Jalankan animasi zoom logo (zoom pol)
                logo.startAnimation(logoZoom);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // 6. Handler pindah ke login setelah splash selesai
        new Handler().postDelayed(() -> {
            logo.clearAnimation();
            circleEffect.clearAnimation();

            Intent intent = new Intent(SplashScreen.this, login.class);
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}