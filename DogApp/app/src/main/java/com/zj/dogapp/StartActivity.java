package com.zj.dogapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.image.sdk.IncSDK;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.zj.image.ActivityMain;

import java.util.ArrayList;

/**
 * Created by jjx on 2016/4/13.
 */
public class StartActivity extends AppCompatActivity{

    SweetSheet mSweetSheet;
    RelativeLayout rl;
    MyAdapter mAdapter;
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton mFabButton;
    String tittles[]=new String[]{"贵宾犬","秋田犬","杜宾犬"};
    int imgIds[]=new int[]{R.drawable.dog1,R.drawable.dog2,R.drawable.dog3};
    String LostMenu[]=new String[]{"贵宾犬 三岁 北京","秋田犬 一岁 武汉","杜宾犬 两岁 上海","金毛 三岁 北京","哈士奇 一岁 北京"
    ,"金毛 一岁 武汉","贵宾犬 一岁 武汉","秋田犬 两岁 上海","贵宾犬 三岁 上海","泰迪 两岁 北京"};

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigationView;
    String filepath;
    static int idNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("狗脸识别");
        //createFiles();

        //设置DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        {
            //关闭侧边栏时响应
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            //打开侧边栏时响应
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //设置NavigationView点击事件
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);
        //设置NavigationView点击事件



        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(StartActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);



        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);




        mFabButton= (FloatingActionButton) findViewById(R.id.fab_normal);
        //mFabButton.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idNum++;
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        initSheet();





    }

    private void initSheet() {
        rl= (RelativeLayout) findViewById(R.id.rl);
        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(rl);


        final ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.ic_account_child;
        //menuEntity1.titleColor = 0xff000000;
        menuEntity1.title = "更多";
        /*MenuEntity menuEntity = new MenuEntity();
        menuEntity.iconId = R.drawable.ic_account_child;
        //menuEntity.titleColor = 0xffb3b3b3;
        menuEntity.title = "item";
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);*/

        for(int i=0;i<10;i++)
        {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.iconId = R.drawable.ic_account_child;
            //menuEntity.titleColor = 0xffb3b3b3;
            menuEntity.title = LostMenu[i];
            list.add(menuEntity);
        }

//设置数据源 (数据源支持设置 list 数组,也支持从menu 资源中获取)
        mSweetSheet.setMenuList(list);

        //根据设置不同的 Delegate 来显示不同的风格.
                mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
//根据设置不同Effect来设置背景效果:BlurEffect 模糊效果.DimEffect 变暗效果,NoneEffect 没有效果.
        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
//设置菜单点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {

                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                //Toast.makeText(StartActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StartActivity.this, DogFindActivity.class);
                startActivity(intent);
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        //MenuItem item=menu.findItem(R.id.write_p);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.write_p:
                //Toast.makeText(getApplication(),"abc",Toast.LENGTH_SHORT).show();;
                mSweetSheet.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_item_example:
                                //switchToExample();
                                //switchToOrder();
                                Intent intent=new Intent(StartActivity.this, ActivityMain.class);
                                startActivity(intent);
                                break;
                            case R.id.navigation_item_blog:

                                break;
                            case R.id.navigation_item_about:
                                //switchToAbout();
                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void createFiles() {
        Log.i("test", "createFiles");
        filepath = Environment.getExternalStorageDirectory().getPath()+ "/Samples";
        IncSDK.copyFilesFassets("samples", filepath, getApplicationContext());

    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



        private final TypedValue mTypedValue = new TypedValue();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            public TextView mTextView;
            public ImageView iv_food;
            public TextView textDesc;
            public int pos=0;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.tvTitle);
                iv_food= (ImageView) v.findViewById(R.id.iv_food_item);
                textDesc= (TextView) v.findViewById(R.id.tvDesc);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                /*String text = "您的"+mTextView.getText() + "已下单";
                Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
                int id=this.pos;
                Log.i("sharpreses", id + "\n");

                //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
                SharedPreferences sharedPreferences= getSharedPreferences("orders",
                        Activity.MODE_PRIVATE);
                int num=sharedPreferences.getInt("food"+(id+1),0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("food"+(id+1),num+1);
                editor.commit();*/
// 使用getString方法获得value，注意第2个参数是value的默认值

//使用toast信息提示框显示信息


            }

        }

        // Provide a suitable constructor (depends on the kind of dataset)

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.food_item, parent, false);

            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //holder.mTextView.setText(mDataset[position]);
            holder.pos=position;
            holder.mTextView.setText(tittles[position]);
            holder.iv_food.setBackgroundResource(imgIds[position]);
            holder.textDesc.setText("家乡：欧洲 \n气质特点：聪明，活泼，性情优良，极易近人 \n历史：贵宾犬曾经是贵妇人的宠物。18世纪繁殖成小的长卷毛犬");
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return 3;
        }
    }

}
