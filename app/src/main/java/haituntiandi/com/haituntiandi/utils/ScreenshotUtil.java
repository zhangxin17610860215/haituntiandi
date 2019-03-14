package haituntiandi.com.haituntiandi.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * zhangxin
 * 用于对特定的View实现截图效果
 */
public class ScreenshotUtil {
    public void screenshot(Context mContext,View view){
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        FileOutputStream fos;
        String imagePath = "";
        String fileName = null;
        File file = null;
        try {
            //获得系统相册路径
            imagePath = Environment.getExternalStorageDirectory()
                    + File.separator + Environment.DIRECTORY_DCIM
                    + File.separator + "Camera" + File.separator;
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                file = new File(imagePath, Calendar.getInstance().getTimeInMillis() + ".png");
                // 获得文件相对路径
                fileName = file.toString();
                fos = new FileOutputStream(fileName);
                imagePath = file.getAbsolutePath();
                if (null != fos) {
                    cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                }
            } else {
                throw new Exception("创建文件失败!");
            }
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //通知相册更新
        MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                cachebmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
        ToastUtil.showMsg("图片已保存到相册");
        view.destroyDrawingCache();
    }
    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0,0, w, h);
        v.draw(c);

        return bmp;
    }
}
