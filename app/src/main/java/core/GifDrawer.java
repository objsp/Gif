package core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Administrator on 2016-05-22 0022.
 */
public class GifDrawer {

    // 完成gif的绘制
    private InputStream is;
    private ImageView imageView;
    private Movie movie;
    private Bitmap bitmap;
    private Canvas canvas;
    private final int frameDuration = 16; // 眼睛最高频率60
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 绘制
            canvas.save();
            movie.draw(canvas, 0, 0);
            canvas.restore(); // 绘制完一次保存
            // 显示哪一帧
            // 写死1000，只显示一张，所以要循环显示
            // 系统时间磨gif时间
            movie.setTime((int) SystemClock.uptimeMillis() % movie.duration()); // 2000毫秒循环一次，显示哪个时刻
            imageView.setImageBitmap(bitmap);

            // 上面封装方法
            handler.postDelayed(runnable, frameDuration);
        }
    };

    // 传递imageView，将gif绘制到imageView
    public void into (ImageView imageView) {
        this.imageView = imageView;
        if (is == null) { // 互联网有空流的情况
            return;
        } else if (imageView == null) {
            throw new RuntimeException("ImageView can not be null");
        } else {
            // 开始在imageView里面绘制gif
            movie = Movie.decodeStream(is); // gif
            if (movie == null) {
                throw new IllegalArgumentException("Illegal gif file");
            }
            if (movie.width() <= 0 || movie.height() <= 0) {
                // 当gif宽高出现问题
                return;
            }

            // gif绘制到imageView
            // 需要一个Bitmap 来获取canvas
            bitmap = Bitmap.createBitmap(movie.width(), movie.height(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap); // 拿到幕布，绘制gif，
            // 准备把canvas绘制的gif显示在imageView里
            // 不断绘制，区别相片
            handler.post(runnable);
        }
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
