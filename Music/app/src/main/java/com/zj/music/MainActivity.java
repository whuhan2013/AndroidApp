package com.zj.music;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView pause;
    ImageView start;
    ImageView stop;
    ImageView previous;
    ImageView next;
    boolean isPlaying=false;
    String filepath="http://115.28.65.162:8080/music";
    String nowMusic;
    int id=1;
    MediaPlayer mediaPlayer;
    SeekBar song_progress_normal;
    private Timer timer;
    private TimerTask task;
    ViewPager mViewPager;
    MyPagerAdapter adapter;
    TabLayout tabLayout;
    SweetSheet mSweetSheet;
    RelativeLayout rl;

    String autors[]=new String[]{"肖邦","斯美塔那","德沃夏克","德沃夏克","Artur Rubinstein","舒伯特",
            "格里格","格里格","舒伯特","舒伯特","舒伯特","舒伯特","舒伯特","肖邦","肖邦","George Frideric Handel",
            "Georges Bizet","Georges Bizet","巴赫","海顿","海顿","巴赫","巴赫","勃拉姆斯","贝多芬","贝多芬","普契尼",
            "普契尼","贝多芬","门德尔松","舒曼","李斯特","巴赫","贝多芬","柴可夫斯基","柴可夫斯基","柴可夫斯基",
            "柴可夫斯基","崔岩光","崔岩光","莫扎特","门德尔松","比才","普契尼","李斯特","李斯特","里姆斯基克萨科夫",
            "马斯卡尼","亨德尔","穆索尔斯基","帕勒斯特里纳","塞基洛斯墓志铭","格鲁克","舒伯特","瓦格纳","安吉拉·乔治乌"};


    String songs[]=new String []{"小狗圆舞曲","伏尔塔瓦河，出自交响套曲《我的祖国》","新世界交响曲：第二乐章广板"
    ,"新世界交响曲：第四乐章雄伟的快板","降E大调作品第9号第2首","艺术歌曲《魔王》","《培尔金特》第一组曲第一乐章《朝景》"
            ,"《培尔金特》第二组曲第四乐章“索尔维格之歌”","艺术歌曲“菩提树”，选自声乐套曲《冬之旅》","弦乐四重奏，《死神与少女》第一乐章"
            ,"弦乐四重奏，《死神与少女》第二乐章","艺术歌曲，小夜曲","第八（“未完成”）交响曲，第一乐章","“革命”练习曲","“军队”波洛涅兹舞曲",
            "D大调第二水上音乐组曲：第5乐章布列舞曲","斗牛士(选自《卡门组曲1号》)","哈巴涅拉舞曲(选自《卡门组曲2号》",
            "《马太受难曲》“我们落泪，我们下跪”","“皇帝”弦乐四重奏，第二乐章","“惊愕”交响曲第二乐章","《b小调弥撒曲》“垂怜经”"
            ,"《十二平均律》第一首，C大调前奏曲","《匈牙利舞曲》第五首","《第三交响曲》第二乐章","第九交响曲第四乐章",
            "蝴蝶夫人咏叹调One beautiful day《晴朗的一天》","《今夜无人入睡》，选自普契尼歌剧《图兰朵》","第五交响曲第四乐章"
            ,"婚礼进行曲，选自《仲夏夜之梦》","“梦幻曲”，选自钢琴套曲《童年情景》","匈牙利舞曲第二号"," G弦上的咏叹调"
            ,"Symphony no.7 Allegretto 第七交响曲第二乐章","第一钢琴协奏曲-第一乐章","胡桃夹子-花之圆舞曲","四季-六月船歌",
            "天鹅湖-圆舞曲","圣母颂(古诺)","圣母颂(舒伯特)","“鞭打我吧”，选自莫扎特歌剧《唐璜》","钢琴.门德尔松.春之歌",
            "歌剧《卡门》选段——卡门序曲","“今夜星光灿烂” ，出自歌剧《托斯卡》","李斯特，钢琴作品，钟","李斯特，钢琴作品- 爱之梦 (第3首)",
            "天方夜谭 - 第三乐章：王子和公主","独幕剧《乡村骑士》 - 间奏曲","“哈利路亚“，出自《弥赛亚》","图画展览会-漫步主题",
            "马采鲁斯教皇弥撒曲的羔羊经","塞基洛斯墓志铭","“世上没有优丽狄茜我怎能活”，选自歌剧《奥菲欧》","艺术歌曲，死神与少女",
            "01_罗恩格林_婚礼大合唱","饮酒歌选自《茶花女》当红女高音安吉拉·乔治乌"};

    String titles[]=new String[]{"1.小狗圆舞曲","2.伏尔塔瓦河","3.新世界交响曲：第二乐章","4.新世界交响曲：第四乐章",
            "5.降E大调作品第9号第2首","6.魔王","7.朝景","8.索尔维格之歌","9.菩提树","10.《死神与少女》第一乐章","11.《死神与少女》第二乐章","12.小夜曲",
            "13.第八（“未完成”）交响曲","14.“革命”练习曲","15.“军队”波洛涅兹舞曲","16.第5乐章布列舞曲","17.斗牛士","18.哈巴涅拉舞曲","19.马太受难曲",
            "20.“皇帝”弦乐四重奏","21.“惊愕”交响曲第二乐章","22.b小调弥撒曲","23.十二平均律","24.匈牙利舞曲","25.第三交响曲",
    "26.第九交响曲第四乐章","27.One beautiful day","28.今夜无人入睡","29.第五交响曲第四乐章","30.婚礼进行曲","31.梦幻曲","32.匈牙利舞曲第二号",
            "33.G弦上的咏叹调","34.第七交响曲第二乐章","35.第一钢琴协奏曲","36.胡桃夹子","37.六月船歌","38.圆舞曲", "39.圣母颂","40.圣母颂",
            "41.鞭打我吧","42.春之歌","43.卡门序曲","44.今夜星光灿烂","45.钟","46.爱之梦","47.王子和公主","48.乡村骑士","49.哈利路亚","50.图画展览会",
            "51.马采鲁斯教皇弥撒曲的羔羊经","52.塞基洛斯墓志铭","53.世上没有优丽狄茜我怎能活","54.死神与少女","55.婚礼大合唱","56.饮酒歌"};

    private static final int UPDATE = 0;
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case UPDATE:
                    song_progress_normal.setProgress(mediaPlayer.getCurrentPosition());
                    Log.i("mediaplayer",mediaPlayer.getCurrentPosition()+"");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTOP();

        pause= (ImageView) findViewById(R.id.pause);
        start= (ImageView) findViewById(R.id.start);
        stop= (ImageView) findViewById(R.id.stop);
        previous= (ImageView) findViewById(R.id.previous);
        next= (ImageView) findViewById(R.id.next);
        song_progress_normal= (SeekBar) findViewById(R.id.song_progress_normal);




        song_progress_normal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int postion = seekBar.getProgress();
                mediaPlayer.seekTo(postion);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id > 1) {
                    id--;
                    setupViewPager(mViewPager);
                    tabLayout.setupWithViewPager(mViewPager);
                    //adapter.notifyDataSetChanged();
                    stopMusic();
                    startMusic();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id<=autors.length) {
                    id++;

                    //setupViewPager(mViewPager);
                    //adapter.notifyDataSetChanged();
                    setupViewPager(mViewPager);

                    tabLayout.setupWithViewPager(mViewPager);

                    stopMusic();
                    startMusic();
                }
            }
        });


        pause.setBackgroundResource(R.drawable.btn_playback_pause);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusic();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        stopMusic();
                        finish();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlaying=!isPlaying;
                System.out.println("1243544444444");
                if (isPlaying)
                {
                    pause.setBackgroundResource(R.drawable.btn_playback_pause);
                    rePlayMusic();
                }else
                {
                    pause.setBackgroundResource(R.drawable.btn_playback_play);
                    pauseMusic();
                }
            }
        });

        initSheet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
        finish();

    }

    private void initTOP() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("音乐鉴赏");
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle("音乐鉴赏");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后Toolbar上字体的颜色


        //设置ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

//给TabLayout增加Tab, 并关联ViewPager
         tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("请您欣赏"));
        tabLayout.addTab(tabLayout.newTab().setText("作者简介"));
        tabLayout.addTab(tabLayout.newTab().setText("歌曲简介"));

        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager mViewPager) {
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailFragment.newInstance("请欣赏古典音乐，欲知作者与曲名可右滑"), "请您欣赏");
        adapter.addFragment(DetailFragment.newInstance(autors[id-1]), "作者简介");
        adapter.addFragment(DetailFragment.newInstance(songs[id-1]), "歌曲简介");

        mViewPager.setAdapter(adapter);
    }

     List<Fragment> mFragments;
    static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private  List<Fragment> mFragments=null;
        private  List<String> mFragmentTitles =null;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments=new ArrayList<>();
            mFragmentTitles = new ArrayList<>();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public void removeAllFra()
        {
            mFragments.clear();
            mFragmentTitles.clear();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }



    private void startMusic() {
        nowMusic=filepath+id+".mp3";
        try {
               Log.i("next","herere");

                mediaPlayer = new MediaPlayer();

                mediaPlayer.setDataSource(nowMusic);//设置播放的数据源。
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //mediaPlayer.prepare();//同步的准备方法。
                mediaPlayer.prepareAsync();//异步的准备
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        start.setEnabled(false);

                        int max = mediaPlayer.getDuration();
                        Log.i("mediaplayer", max + "最大");
                        Log.i("next","here2");
                        song_progress_normal.setMax(max);

                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        start.setEnabled(true);


                        //stopMusic();

                    }
                });

            new Thread(new Runnable() {
                @Override
                public void run() {

                    timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(UPDATE);
                        }
                    };
                    timer.schedule(task, 0, 10000);

                }
            }).start();




        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void pauseMusic() {

        if(mediaPlayer!=null)
        mediaPlayer.pause();

    }

    private void rePlayMusic()
    {
        if(mediaPlayer!=null)
        mediaPlayer.start();
    }
    public void stopMusic() {
        Log.i("next","here3");
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            timer.cancel();
            //this.finish();
        }

        start.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

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

        for(int i=0;i<titles.length;i++)
        {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.iconId = R.drawable.ic_account_child;
            //menuEntity.titleColor = 0xffb3b3b3;
            menuEntity.title = titles[i];
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
                Toast.makeText(MainActivity.this, menuEntity1.title, Toast.LENGTH_SHORT).show();
                id=position+1;
                setupViewPager(mViewPager);

                tabLayout.setupWithViewPager(mViewPager);

                stopMusic();
                startMusic();
                //Intent intent=new Intent(StartActivity.this,DogFindActivity.class);
                //startActivity(intent);
                return true;
            }
        });


    }
}
