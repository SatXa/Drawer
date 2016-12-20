package com.example.satxa.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Lienzo lienzo;
    int[] colores = new int[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RelativeLayout layoutLienzo = (RelativeLayout) findViewById(R.id.content_main);
        Lienzo lienzo = new Lienzo(this);
        layoutLienzo.addView(lienzo);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.circle:
                lienzo.drawCircle();
                break;
            case R.id.square:
                lienzo.drawSquare();
                break;
            case R.id.kappa:
                lienzo.drawKappa();
                break;
            case R.id.colour:
                lienzo = new Lienzo(this);
                lienzo.setBackgroundColor(colores[(int) (Math.random() * 11 + 1)]);
                System.out.println(colores[(int) (Math.random() * 11 + 1)]);
                break;
            case R.id.clear:
                lienzo = new Lienzo(this);
                lienzo.setBackgroundColor(Color.TRANSPARENT);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Lienzo extends View {
        private float x, y;
        private Bitmap bm;
        private Paint pathPaint, bgPaint;
        private Path path;
        private int action;

        public Lienzo(Context context) {
            super(context);

            pathPaint = new Paint();
            bgPaint = new Paint();
            path = new Path();

            bgPaint.setColor(Color.GREEN);
            pathPaint.setColor(Color.BLUE);
            pathPaint.setStrokeWidth(10);
            pathPaint.setStyle(Paint.Style.STROKE);
            pathPaint.setStrokeJoin(Paint.Join.ROUND);
            pathPaint.setStrokeCap(Paint.Cap.ROUND);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawPath(path, bgPaint);

            switch (action) {
                case 0: // mientras se está pulsando
                    path.lineTo(x, y);
                    break;
                case 1: // al soltar
                    path.moveTo(x, y);
                    break;
                case 2: // al pulsar
                    path.moveTo(x, y);
                    break;
            }

            canvas.drawPath(path, pathPaint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: // mientras se está pulsando
                    action = 0;
                    break;
                case MotionEvent.ACTION_UP: // al soltar
                    action = 1;
                    break;
                case MotionEvent.ACTION_DOWN: // al pulsar
                    action = 2;
                    break;
            }

            invalidate();

            return true;
        }

        public void drawCircle() {
            Drawable d = getResources().getDrawable(R.drawable.circle);
            //d.setBounds(left, top, right, bottom);
            d.draw(new Canvas());
        }

        public void drawSquare() {
            Drawable d = getResources().getDrawable(R.drawable.square);
            //d.setBounds(left, top, right, bottom);
            d.draw(new Canvas());
        }

        public void drawKappa() {
            Drawable d = getResources().getDrawable(R.drawable.kappa);
            //d.setBounds(left, top, right, bottom);
            d.draw(new Canvas());
        }
    }
}
