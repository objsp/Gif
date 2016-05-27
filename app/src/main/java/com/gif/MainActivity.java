package com.gif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import core.GifDecoder;

// GifDecoder 完成gif加载
// GifDrawer
// 网络请求
// 缓存策略
public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private String url = "http://s1.dwstatic.com/group1/M00/A9/B6/ccfa8090401d7d375f474e6d2cbeec38.gif";
    private String url1 = "http://s1.dwstatic.com/group1/M00/E5/4D/409997449b661149ecd6b1f2398885d8.gif";
    private String url2 = "http://s1.dwstatic.com/group1/M00/31/1E/9bb7b6303208b4d18edd1be03a7779da.gif";
    private String url3 = "http://img5.imgtn.bdimg.com/it/u=2695557640,2526776933&fm=21&gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageview);
//        try {
//            // 不能等图片下载完了再显示，线程卡在这个地方
//            // 所以，在主线程必须跑完。在跑之前一定是空
//            GifDecoder.with(this).load(this.getAssets().open("test.gif")).into(imageView);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        GifDecoder.with(this).load(url3).into(imageView);
    }
}
