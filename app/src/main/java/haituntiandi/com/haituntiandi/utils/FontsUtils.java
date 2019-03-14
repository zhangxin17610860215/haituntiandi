package haituntiandi.com.haituntiandi.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontsUtils {
    public static Typeface mNomalTypeface;
    public static Typeface mMediumTypeface;
    public static Typeface mBoldTypeFace;

    public static Typeface getNormalTypeface(Context context) {
        if (null == mNomalTypeface) {
            synchronized (FontsUtils.class) {
                mNomalTypeface = Typeface.DEFAULT;
            }
        }
        return mNomalTypeface;
    }

    public static Typeface getMediumTypeface(Context context) {
        if (null == mMediumTypeface) {
            synchronized (FontsUtils.class) {
//                mMediumTypeface = createFontFromAsset(context, "fonts/SourceHanSansCN-Medium.otf");
                mNomalTypeface = Typeface.DEFAULT;
            }
        }
        return mMediumTypeface;
    }

//    public static Typeface getBoldTypeFace(Context context) {
//        if (null == mBoldTypeFace) {
//            synchronized (FontsUtils.class) {
//                mBoldTypeFace = createFontFromAsset(context, "fonts/SourceHanSansCN-Bold.otf");
//            }
//        }
//        return mBoldTypeFace;
//    }

    private static Typeface createFontFromAsset(Context context, String fontPath) {
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        } catch (Exception e) {
            return null;
        }
        return tf;
    }
}
