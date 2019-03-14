package haituntiandi.com.haituntiandi.utils;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Author:xcb
 *
 * @author xcb
 * @date 2018/9/7
 */
public class MyFaceCameraView extends SurfaceView implements SurfaceHolder.Callback2 {

    private Camera camera = null;
    private static SurfaceHolder holder;
    private Context context;


    public MyFaceCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        holder = getHolder();
        holder.addCallback(this);
    }

    public MyFaceCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        holder = getHolder();
        holder.addCallback(this);
    }

    public MyFaceCameraView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (camera == null) {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(final byte[] data, final Camera camera) {

                }
            });
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (camera != null) {
            camera.startPreview();
            Camera.Parameters parameters = camera.getParameters();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


}
