package com.newsknow.min.newsknow;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newsknow.min.newsknow.fragment.MeiziFragment;
import com.newsknow.min.newsknow.fragment.TopsFragment;
import com.newsknow.min.newsknow.fragment.ZhihuFragment;
import com.newsknow.min.newsknow.util.AnimUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    private SimpleArrayMap<Integer, String> mTitleArryMap = new SimpleArrayMap<>();
    private MenuItem currentMenuItem;
    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //drawer=this.findViewById(R.id.drawer);
        //toolbar=this.findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
//        toolbar.inflateMenu(R.menu.toolmenu);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//
//                drawer.openDrawer(GravityCompat.END);
//                return true;
//            }
//        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
                }
        }
        );
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            animateToolbar();
//        }

        addfragmentsAndTitle();

        drawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

//        if (savedInstanceState == null) {
//            nevigationId = SharePreferenceUtil.getNevigationItem(this);
//            if (nevigationId != -1) {
//                currentMenuItem = navView.getMenu().findItem(nevigationId);
//            }
//            if (currentMenuItem == null) {
//                currentMenuItem = navView.getMenu().findItem(R.id.zhihuitem);
//            }
//            if (currentMenuItem != null) {
//                currentMenuItem.setChecked(true);
//                // TODO: 16/8/17 add a fragment and set toolbar title
//                Fragment fragment = getFragmentById(currentMenuItem.getItemId());
//                String title = mTitleArryMap.get((Integer) currentMenuItem.getItemId());
//                if (fragment != null) {
//                    switchFragment(fragment, title);
//                }
//            }
//        } else {
//            if (currentMenuItem != null) {
//                Fragment fragment = getFragmentById(currentMenuItem.getItemId());
//                String title = mTitleArryMap.get((Integer) currentMenuItem.getItemId());
//                if (fragment != null) {
//                    switchFragment(fragment, title);
//                }
//            } else {
                switchFragment(new ZhihuFragment(), " ");
                currentMenuItem = navView.getMenu().findItem(R.id.zhihuitem);
//
//            }
//        }

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
//                if(currentMenuItem!=null&&item.getItemId()==R.id.menu_about)
//                {
//                    Intent intent = new Intent(getApplication(), AboutActivity.class);
//                    getApplication().startActivity(intent);
//                    return true;
//                }
//
//
//                if (currentMenuItem != item && currentMenuItem != null) {
//                    currentMenuItem.setChecked(false);
//                    int id = item.getItemId();
//                    SharePreferenceUtil.putNevigationItem(MainActivity.this, id);
//                    currentMenuItem = item;
//                    currentMenuItem.setChecked(true);
//                    switchFragment(getFragmentById(currentMenuItem.getItemId()), mTitleArryMap.get(currentMenuItem.getItemId()));
//                }
                if(item.getItemId()==R.id.zhihuitem){
                    switchFragment(new ZhihuFragment(),"");
                    Toast.makeText(MainActivity.this,"zhihu",Toast.LENGTH_LONG).show();
                }else if(item.getItemId()==R.id.topnewsitem){
                    switchFragment(new TopsFragment(),"");
                    Toast.makeText(MainActivity.this,"tops",Toast.LENGTH_LONG).show();
                }else if(item.getItemId()==R.id.meiziitem){
                    switchFragment(new MeiziFragment(),"");
                    Toast.makeText(MainActivity.this,"meizi",Toast.LENGTH_LONG).show();
                }else if(item.getItemId()==R.id.nav_send){
                    Toast.makeText(MainActivity.this,"shezhi",Toast.LENGTH_LONG).show();
                }else if(item.getItemId()==R.id.nav_theme){
                    Toast.makeText(MainActivity.this,"theme",Toast.LENGTH_LONG).show();
                }
                drawer.closeDrawer(GravityCompat.END, true);
                return true;
            }
        });

    }

    private void addfragmentsAndTitle() {
        mTitleArryMap.put(R.id.zhihuitem, getResources().getString(R.string.zhihu));
        mTitleArryMap.put(R.id.topnewsitem, getResources().getString(R.string.topnews));
        mTitleArryMap.put(R.id.meiziitem, getResources().getString(R.string.meizi));
    }

    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(500)
                    .setDuration(900)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(this)).start();
        }
        View amv = toolbar.getChildAt(1);
        if (amv != null & amv instanceof ActionMenuView) {
            ActionMenuView actions = (ActionMenuView) amv;
            popAnim(actions.getChildAt(0), 500, 200); // filter
            popAnim(actions.getChildAt(1), 700, 200); // overflow
        }
    }

    private void popAnim(View v, int startDelay, int duration) {
        if (v != null) {
            v.setAlpha(0f);
            v.setScaleX(0f);
            v.setScaleY(0f);

            v.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(startDelay)
                    .setDuration(duration)
                    .setInterpolator(AnimationUtils.loadInterpolator(this,
                            android.R.interpolator.overshoot)).start();
        }
    }

    private void switchFragment(Fragment fragment, String title) {

        if (fragment != null) {
            if (currentFragment == null || !currentFragment
                    .getClass().getName().equals(fragment.getClass().getName())) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                        .commit();
            }
            currentFragment = fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.toolmenu,menu);
        return true;
    }
}
