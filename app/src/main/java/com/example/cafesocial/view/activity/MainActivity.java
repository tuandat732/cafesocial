package com.example.cafesocial.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafesocial.R;
import com.example.cafesocial.core.activity.BaseActivity;
import com.example.cafesocial.core.adapter.FragmentAdapter;
import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.User;
import com.example.cafesocial.service.AsyncStorage;
import com.example.cafesocial.service.HttpService;
import com.example.cafesocial.service.UserService;
import com.example.cafesocial.service.iservice.AuthService;
import com.example.cafesocial.utils.Toastify;
import com.example.cafesocial.view.fragment.AccountFragment;
import com.example.cafesocial.view.fragment.AddStoreFragment;
import com.example.cafesocial.view.fragment.DetailStoreFragment;
import com.example.cafesocial.view.fragment.DiscoverFragment;
import com.example.cafesocial.view.fragment.HomeFrament;
import com.example.cafesocial.view.fragment.ProfileFragment;
import com.example.cafesocial.view.fragment.SaleFragment;
import com.example.cafesocial.view.fragment.SaveFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private BottomNavigationView navigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationViewDraw;
    private Toolbar toolbar;
    private Button btnLoginRegister, btnLogout;
    TextView drawUsername, drawEmail;
    private ImageView btnToggleDrawMenu, menuIconSearch;
    private LinearLayout viewLoginRegister,
    drawViewUser,
    drawEditInfo,
    drawSave,
    drawHome,
    drawDiscovery,
    drawSale,
    drawNewStore,
    drawGioiThieu,
    drawDieuKhoan,
    drawLienHe,
    drawViewLogout;

    private String tabTitles[] = new String[]{"Trang chủ", "Khám phá", "Khuyến mãi", "Đã lưu", "Tài khoản"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        System.out.println("init");
        String token = AsyncStorage.build(this).get("token");
        System.out.println("token nef" +token);
        if(!token.equals("")) {
            HttpService.get(AuthService.class).getMe(token).enqueue(new Callback<ResponseData<User>>() {
                @Override
                public void onResponse(Call<ResponseData<User>> call, Response<ResponseData<User>> response) {
                    if(response.code() ==200) {
                        System.out.println("call aut me");
                        UserService.setAuth(response.body().getData(), token);
                        drawViewUser.setVisibility(View.VISIBLE);
                        drawViewLogout.setVisibility(View.VISIBLE);

                        viewLoginRegister.setVisibility(View.GONE);
                        drawUsername.setText(UserService.getUser().getUsername());
                        drawEmail.setText(UserService.getUser().getEmail());
                    } else {
                        viewPager.setCurrentItem(0);
                        drawViewUser.setVisibility(View.GONE);
                        drawViewLogout.setVisibility(View.GONE);
                        viewLoginRegister.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseData<User>> call, Throwable t) {
                    Toastify.toastError(getApplicationContext(), null);
                    drawViewUser.setVisibility(View.GONE);
                    drawViewLogout.setVisibility(View.GONE);
                    viewLoginRegister.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }

    @Override
    public void initView() {
        // drawMenu
        btnLogout = findViewById(R.id.drawLogout);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);
        drawUsername = findViewById(R.id.drawUsername);
        drawEmail = findViewById(R.id.drawEmail);
        viewLoginRegister = findViewById(R.id.viewLoginRegisterBtn);
        drawViewLogout = findViewById(R.id.drawViewLogout);
        drawViewUser = findViewById(R.id.drawViewUser);
        drawEditInfo = findViewById(R.id.drawEditInfo);
        drawSave = findViewById(R.id.drawSave);
        drawHome = findViewById(R.id.drawHome);
        drawDiscovery = findViewById(R.id.drawDiscovery);
        drawSale = findViewById(R.id.drawSale);
        drawNewStore = findViewById(R.id.drawNewStore);
        drawGioiThieu = findViewById(R.id.drawGioiThieu);
        drawDieuKhoan = findViewById(R.id.drawDieuKhoan);
        drawLienHe = findViewById(R.id.drawLienHe);
        btnToggleDrawMenu = findViewById(R.id.btnToggeDrawMenu);
        menuIconSearch = findViewById(R.id.menuIconSearch);
        viewPager = findViewById(R.id.viewPager);
        navigationView = findViewById(R.id.bottomNavMain);
        navigationViewDraw = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        btnLogout = findViewById(R.id.drawLogout);

        actionToolBar();

        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFrament());
        list.add(new DiscoverFragment());
        list.add(new SaleFragment());
        list.add(new SaveFragment());
        list.add(new AccountFragment());
//        list.add(new AddStoreFragment());

        adapter = new FragmentAdapter(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                list,
                tabTitles
        ); // cái này giúp khi chuyển tab thì sẽ gọi resume
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserService.isAuthen()) {
            drawViewUser.setVisibility(View.VISIBLE);
            drawViewLogout.setVisibility(View.VISIBLE);

            viewLoginRegister.setVisibility(View.GONE);
            drawUsername.setText(UserService.getUser().getUsername());
            drawEmail.setText(UserService.getUser().getEmail());
        } else {
            drawViewUser.setVisibility(View.GONE);
            drawViewLogout.setVisibility(View.GONE);
            viewLoginRegister.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");
        if(fragment != null) {
            if (fragment.equals("discovery")) {
                viewPager.setCurrentItem(1);
            } else if(fragment.equals("profile")) {
                Bundle bundle = new Bundle();
                bundle.putLong("userId", intent.getLongExtra("userId", 1));
                ProfileFragment profileFragment = new ProfileFragment();
                addFragment(R.id.frameLayout, profileFragment, "profile", "profile", bundle);
            } else if(fragment.equals("detailStore")) {
                Bundle bundle = new Bundle();
                bundle.putLong("storeId", intent.getLongExtra("storeId", 1));
                addFragment(R.id.frameLayout, new DetailStoreFragment(), "detailStore", "detailStore", bundle);
            } else if(fragment.equals("saveStore")) {
                viewPager.setCurrentItem(3);
            }
        }
    }

    @Override
    public void initEvent() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.clearAuth();
                AsyncStorage.build(getApplicationContext()).set("token", "");
                viewPager.setCurrentItem(0);
                drawViewUser.setVisibility(View.GONE);
                drawViewLogout.setVisibility(View.GONE);
                viewLoginRegister.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
            }
        });
        menuIconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(SearchActivity.class);
            }
        });
        drawNewStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(R.id.frameLayout, new AddStoreFragment(), "storeFrag", "storeFrag", null);
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(LoginActivity.class);
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        drawDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        drawHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        drawSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        drawSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("possitotnfa "+position);
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.menuItemHome).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.menuItemPost).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.menuItemSale).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.menuItemSave).setChecked(true);
                        break;
                    case 4:
                        navigationView.getMenu().findItem(R.id.menuItemAccount).setChecked(true);
                        break;
                    default:
                        navigationView.getMenu().findItem(R.id.menuItemHome).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println("id "+item.getItemId());
                switch (item.getItemId()) {
                    case R.id.menuItemHome:
                        System.out.println("chọn home");
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.menuItemPost:
                        System.out.println("chọn post");
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.menuItemSale:
                        System.out.println("chọn sale");
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.menuItemSave:
                        System.out.println("chọn save");
                        viewPager.setCurrentItem(3);
                        return true;
                    case R.id.menuItemAccount:
                        System.out.println("chọn account");
                        viewPager.setCurrentItem(4);
                        return true;
                    default:
                        viewPager.setCurrentItem(0);
                        return true;
                }
            }
        });
    }

    public void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(null);
        btnToggleDrawMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}