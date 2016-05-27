package core;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import utils.MD5Utils;

/**
 * Created by Administrator on 2016-05-22 0022.
 */
public class GifDecoder {

    // 完成gif文件的加载
    // 单例模式
    public static GifDecoder instance = new GifDecoder();
    public static Context context;

    private GifDecoder () { // 私有构造，new不出来

    }

    public static GifDecoder with (Context c) {
        context = c;
        return instance;
    }

    // load gif file from inputstream
    public GifDrawer load(InputStream is) {
        // 可以是String，uri，inputstream
        GifDrawer drawer = new GifDrawer();
        drawer.setIs(is);
        return drawer;
    }

    // 访问sd卡的gif
    public GifDrawer load (File file) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return load(is);
    }

    // 手机
    public GifDrawer load (Uri uri) {
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return load(is);
    }

    // load gif File From server
    public GifDrawer load (String url) {
        // 拿到远程的流
        // 切记不能用HttpUrlConnection加载。失帧
        // 先下载，放在本地。作为本地加载快很多
        InputStream is = null;
        // 下载目录，下载之前文件名不能用原文件名。会扫描到数据库去。
        // 加密
        String name = MD5Utils.encode(url);
        String path = context.getExternalCacheDir() + File.separator + name;
        File file = new File(path);
        if (file.exists()) {
            try {
                is = new FileInputStream(file);
                return load(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // 不存在，先下载
            GifDrawer drawer = new GifDrawer();
            LoadGifTask gifTask = new LoadGifTask(drawer);
            gifTask.execute(url);
            return drawer; // 主线程不会阻塞。绘制抱空return
        }
        return load(is);
    }

    class LoadGifTask extends AsyncTask<String, Void ,String> {

        private String key;
        private GifDrawer gifDrawer;

        public LoadGifTask (GifDrawer gifDrawer) {
            this.gifDrawer = gifDrawer;
        }

        @Override
        protected String doInBackground(String... params) {
            this.key = params[0];
            try {
                InputStream is = HttpLoader.getInInputStreamFromUrl(key);
                String name = MD5Utils.encode(key);
                String path = context.getExternalCacheDir() + File.separator + name;
                File file = new File(path);
                FileOutputStream os = null;
                os = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.close();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return key;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                // 下载完毕了，重点！！！
                instance.load(key).into(gifDrawer.getImageView());
            }
        }
    }

}
