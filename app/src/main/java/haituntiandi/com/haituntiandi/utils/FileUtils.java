package haituntiandi.com.haituntiandi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
    private static final String TAG = "FileUtil";
    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    public static boolean makeDir(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return false;
        }

        File f = new File(dir);
        if (!f.exists()) {
            return f.mkdirs();
        }

        return true;
    }

    public static String formatFileSize(long fileS) {
        if (fileS < 0) {
            return "未知大小";
        }
        DecimalFormat df = new DecimalFormat("0.0");
        String fileSizeString;
        if (fileS < 1024) {
            fileSizeString = df.format(Rounding(fileS)) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format(Rounding((double) fileS / 1024)) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format(Rounding((double) fileS / 1048576)) + "M";
        } else {
            fileSizeString = df.format(Rounding((double) fileS / 1073741824)) + "G";
        }
        return fileSizeString;
    }

    private static double Rounding(double d) {
        BigDecimal bd = new BigDecimal(d);
        bd.setScale(1, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public static boolean IsFileExist(String file) {
        if (TextUtils.isEmpty(file)) {
            return false;
        }
        File f = new File(file);
        return f.exists();
    }

    public static String getFileName(String path) {
        return new File(path).getName();
    }

    public static String getFileNameEx(String file) {
        int nPos = file.lastIndexOf(".");
        if (nPos > 0) {
            file = file.substring(0, nPos);
            nPos = file.lastIndexOf("/");
            if (nPos > 0) {
                file = file.substring(nPos + 1);
            }
        }
        return file;
    }

    public static long getFileLen(String file) {
        if (file == null)
            return 0;
        File f = new File(file);
        return f.length();
    }

    // 获得一个目录下 所有文件的大小 包括子目录
    public static long getDirectorySize(String filePath) {
        if (filePath == null)
            return 0;

        File file = new File(filePath);
        if (!file.isDirectory())
            return 0;

        long size = 0;
        File list[] = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    size += getDirectorySize(list[i].getAbsolutePath());
                } else {
                    size += list[i].length();
                }
            }
        }

        return size;
    }

    private static void makesureParentExist(File file_) {
        if (file_ == null) {
            return;
        }
        File parent = file_.getParentFile();
        if ((parent != null) && (!(parent.exists())))
            makeDir(parent.getPath());
    }

    private static void makesureFileExist(File file) {
        if (file == null)
            return;
        if (!(file.exists())) {
            makesureParentExist(file);
            createNewFile(file);
        }
    }

    private static void createNewFile(File file_) {
        if (file_ == null) {
            return;
        }
        if (!(__createNewFile(file_)))
            throw new RuntimeException(file_.getAbsolutePath()
                    + " doesn't be created!");
    }

    private static boolean __createNewFile(File file_) {
        if (file_ == null) {
            return false;
        }
        makesureParentExist(file_);
        if (file_.exists())
            delete(file_);
        try {
            return file_.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void delete(File f) {
        if ((f != null) && (f.exists()) && (!(f.delete()))) {
            throw new RuntimeException(f.getAbsolutePath()
                    + " doesn't be deleted!");
        }

    }

    /**
     * 是否在目录层级内
     *
     * @param root      根目彿
     * @param path      根目录中的子目录
     * @param hierarchy 层级数，假娀昿1则永远返回true＿
     */
    public static boolean isInHierarchy(File root, File path, int hierarchy) {
        if (hierarchy < 0) {
            return true;
        } else {
            String[] nodes = path.getPath().substring(root.getPath().length()).split(File.separator);
            return nodes.length - 1 <= hierarchy;
        }
    }

    public static boolean moveFile(String srcFileName, String destFileName) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile())
            return false;

        File destFile = new File(destFileName);
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        return srcFile.renameTo(destFile);
    }


    /**
     * 拷贝文件
     *
     * @param sourceFile 源文仿
     * @param destFile   目标文件
     * @return 是否拷贝成功
     */
    public static boolean copyFile(File sourceFile, File destFile) {
        boolean isCopyOk = false;
        byte[] buffer;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        // 如果此时没有文件夹目录就创建
        String canonicalPath = "";
        try {
            canonicalPath = destFile.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!destFile.exists()) {
            if (canonicalPath.lastIndexOf(File.separator) >= 0) {
                canonicalPath = canonicalPath.substring(0, canonicalPath.lastIndexOf(File.separator));

                isCopyOk = FileUtils.makeDir(canonicalPath);

            }
        }

        if (!isCopyOk) {
            return false;
        }

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFile), DEFAULT_BUFFER_SIZE);
            bos = new BufferedOutputStream(new FileOutputStream(destFile), DEFAULT_BUFFER_SIZE);
            buffer = new byte[DEFAULT_BUFFER_SIZE];
            int len;
            while ((len = bis.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            isCopyOk = sourceFile.length() == destFile.length();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isCopyOk;
    }

    /**
     * 安全关闭流对象
     *
     * @param closeable
     */
    private static void safeClose(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容到字节数绿
     */
    public static byte[] readFileToBytes(File file) {
        byte[] bytes = null;
        if (file.exists()) {
            byte[] buffer;
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            ByteArrayOutputStream baos = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
                baos = new ByteArrayOutputStream();
                bos = new BufferedOutputStream(baos, DEFAULT_BUFFER_SIZE);
                buffer = new byte[DEFAULT_BUFFER_SIZE];
                int len;
                while ((len = bis.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
                bytes = baos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    public static byte[] readFileToBytes(File file, long offset, long len) {
        byte[] bytes = null;
        if (file.exists() && offset >= 0 && len > offset && offset < file.length()) {
            RandomAccessFile raf = null;
            ByteArrayOutputStream bos = null;
            try {
                raf = new RandomAccessFile(file, "r");
                raf.seek(offset);
                bos = new ByteArrayOutputStream();
                int b;
                long count = 0;
                while ((b = raf.read()) != -1 && count < len) {
                    bos.write(b);
                    count++;
                }
                bos.flush();
                bytes = bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (raf != null) {
                        raf.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    public static boolean writeBytesToFile(File file, byte[] bytes, long offset, int byteCount) {
        boolean isOk = false;
        if (!file.exists()) {
            try {
                isOk = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.exists() && bytes != null && offset >= 0) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file, "rw");
                raf.seek(offset);
                raf.write(bytes, 0, byteCount);
                isOk = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (raf != null) {
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isOk;
    }

    /**
     * 读取文件内容到字符串
     */
    public static String readFileToString(File file) {
        return readFileToString(file, null);
    }

    /**
     * 读取文件内容到字符串
     */
    public static String readFileToString(File file, String encoding) {
        String result = null;
        if (file.exists()) {
            char[] buffer;
            BufferedReader br = null;
            InputStreamReader isr = null;
            BufferedWriter bw = null;
            StringWriter sw = new StringWriter();
            try {
                isr = encoding == null ? new InputStreamReader(new FileInputStream(file)) : new InputStreamReader(new FileInputStream(file), encoding);
                br = new BufferedReader(isr);
                bw = new BufferedWriter(sw);
                buffer = new char[DEFAULT_BUFFER_SIZE];
                int len;
                while ((len = br.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bw.write(buffer, 0, len);
                }
                bw.flush();
                result = sw.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                    sw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 写字符串到文件，文件父目录娀果不存在，会自动创建
     */
    public static boolean writeStringToFile(File file, String content) {
        return writeStringToFile(file, content, false);
    }

    /**
     * 写字符串到文件，文件父目录娀果不存在，会自动创建
     */
    public static boolean writeStringToFile(File file, String content, boolean isAppend) {
        boolean isWriteOk = false;
        char[] buffer;
        int count = 0;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                createNewFileAndParentDir(file);
            }
            if (file.exists()) {
                br = new BufferedReader(new StringReader(content));
                bw = new BufferedWriter(new FileWriter(file, isAppend));
                buffer = new char[DEFAULT_BUFFER_SIZE];
                int len;
                while ((len = br.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bw.write(buffer, 0, len);
                    count += len;
                }
                bw.flush();
            }
            isWriteOk = content.length() == count;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isWriteOk;
    }

    /**
     * 写字节数组到文件，文件父目录如果不存在，会自动创建
     */
    public static boolean writeBytesToFile(File file, byte[] bytes) {
        return writeBytesToFile(file, bytes, false);
    }

    /**
     * 写字节数组到文件，文件父目录如果不存在，会自动创建
     */
    public static boolean writeBytesToFile(File file, byte[] bytes, boolean isAppend) {
        boolean isWriteOk = false;
        byte[] buffer;
        int count = 0;
        ByteArrayInputStream bais = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            if (!file.exists()) {
                createNewFileAndParentDir(file);
            }
            if (file.exists()) {
                bos = new BufferedOutputStream(new FileOutputStream(file, isAppend), DEFAULT_BUFFER_SIZE);
                bais = new ByteArrayInputStream(bytes);
                bis = new BufferedInputStream(bais, DEFAULT_BUFFER_SIZE);
                buffer = new byte[DEFAULT_BUFFER_SIZE];
                int len;
                while ((len = bis.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bos.write(buffer, 0, len);
                    count += len;
                }
                bos.flush();
            }
            isWriteOk = bytes.length == count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isWriteOk;
    }

    /**
     * 创建文件父目彿
     */
    public static boolean createParentDir(File file) {
        boolean isMkdirs = true;
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                isMkdirs = dir.mkdirs();
            }
        }
        return isMkdirs;
    }

    /**
     * 创建文件及其父目彿
     */
    public static boolean createNewFileAndParentDir(File file) {
        boolean isCreateNewFileOk;
        isCreateNewFileOk = createParentDir(file);
        // 创建父目录失败，直接返回false，不再创建子文件
        if (isCreateNewFileOk) {
            if (!file.exists()) {
                try {
                    isCreateNewFileOk = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    isCreateNewFileOk = false;
                }
            }
        }
        return isCreateNewFileOk;
    }


    /**
     * 根据文件名称获取文件的后缿͗符串
     *
     * @param filename 文件的名秿可能带路徿
     * @return 文件的后缿͗符串
     */
    public static String getFileExtension(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            int dotPos = filename.lastIndexOf('.');
            if (0 <= dotPos) {
                return filename.substring(dotPos + 1);
            }
        }
        return "";
    }

    // /a/b/cccc/t.txt  => /a/b/cccc/t
    public static String getPathExcludeExtension(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            int dotPos = filename.lastIndexOf('.');
            if (0 <= dotPos) {
                return filename.substring(0, dotPos);
            } else {
                return filename;
            }
        }
        return "";
    }

    public static String modifyFileExtension(String file, String newExtension) {
        int nPos1 = file.lastIndexOf(".");
        if (nPos1 > 0) {
            return file.substring(0, nPos1) + newExtension;
        }
        return "";
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与吿
     *
     * @param sPath 要删除的目录或文仿
     * @return 删除成功返回 true，否则返囿false〿
     */
    public static boolean deleteFileOrDirectory(String sPath) {
        File file = new File(sPath);
        // 判断目录或文件是否存圿
        if (!file.exists()) { // 不存在返囿false
            return false;
        } else {
            // 判断是否为文仿
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件吿
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        if (TextUtils.isEmpty(sPath)) {
            return false;
        }
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    public static boolean deleteDirectory(String sPath) {
        return deleteDirectoryImp(sPath, null);
    }


    /**
     * returning image / video
     */
    public static File getOutputMediaFile(@NonNull Context context, @NonNull String imageFolder, boolean isCrop) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                imageFolder);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;

        if (Build.VERSION.SDK_INT >= 24) {
            mediaFile = new File(context.getFilesDir() + File.separator
//                    + "crop" + File.separator
                    + (isCrop ? "IMG_crop" : "IMG_") + timeStamp + ".jpg");
        } else {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + (isCrop ? "IMG_crop" : "IMG_") + timeStamp + ".jpg");
        }
        return mediaFile;
    }


    public interface IDeleteFileFilter {
        boolean isDelete(File file);
    }

    public static boolean deleteDirectoryImp(String sPath, IDeleteFileFilter deleteFileFilter) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔笿
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则ꀥǿ
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文仿包括子目彿
        File[] files = dirFile.listFiles();
        if (files == null) {
            return false;
        }
        for (int i = 0; i < files.length; i++) {
            // 删除子文仿
            if (files[i].isFile()) {
                if (deleteFileFilter == null || deleteFileFilter.isDelete(files[i])) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            } // 删除子目彿
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        return dirFile.delete();
    }

    public static boolean renameFile(String newFileName, String oldFileName) {
        File oldfile = new File(oldFileName);
        File newfile = new File(newFileName);
        if (!oldfile.exists()) {
            return false;
        }
        boolean renameResult = oldfile.renameTo(newfile);
        oldfile.delete();
        return renameResult;
    }

    public static boolean pathFileExist(String sPath) {
        if (TextUtils.isEmpty(sPath)) {
            return false;
        }
        File dirFile = new File(sPath);
        return dirFile != null && dirFile.exists();
    }

    public static void closeQuietly(Closeable in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存图片到sdcard
     * @param context
     * @param mybitmap
     * @param name
     * @return
     */
//    public static String saveBitmapToSdCard(Context context, Bitmap mybitmap, String name){
//        String successFilePath = null;
//        //创建位图保存目录
//        String path = Environment.getExternalStorageDirectory() + File.separator + Constants.PIC_WECHAT_DIR + File.separator;
//        File sd = new File(path);
//        if (!sd.exists()){
//            sd.mkdir();
//        }
//
//        File file = new File(path + name + ".jpg");
//        FileOutputStream fileOutputStream = null;
//        if (file.exists()){
//            file.delete();
//        }
//
//        try {
//            // 判断SD卡是否存在，并且是否具有读写权限
//            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//                fileOutputStream = new FileOutputStream(file);
//                mybitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
//                fileOutputStream.flush();
//                fileOutputStream.close();
//
//                //update gallery
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri uri = Uri.fromFile(file);
//                intent.setData(uri);
//                context.sendBroadcast(intent);
//                successFilePath = file.toString();
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return successFilePath;
//    }

    /**
     *
     * @param view 需要截取图片的view
     * @return 截图
     */
    public static Bitmap getBitmapFromView(Activity activity, View view) {

        View screenView = activity.getWindow().getDecorView();
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();

        //获取屏幕整张图片
        Bitmap bitmap = screenView.getDrawingCache();

        if (bitmap != null) {

            //需要截取的长和宽
            int outWidth = view.getWidth();
            int outHeight = view.getHeight();

            //获取需要截图部分的在屏幕上的坐标(view的左上角坐标）
            int[] viewLocationArray = new int[2];
            view.getLocationOnScreen(viewLocationArray);

            //从屏幕整张图片中截取指定区域
            bitmap = Bitmap.createBitmap(bitmap, viewLocationArray[0], viewLocationArray[1], outWidth, outHeight);

        }

        return bitmap;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        final int IMAGE_SIZE=32768;//微信分享图片大小限制

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);

        // 微信对分享图片有32kb限制，进行压缩
        int options = 100;
        while (output.toByteArray().length > IMAGE_SIZE && options != 10) {
            output.reset();                       //清空 output
            bmp.compress(Bitmap.CompressFormat.JPEG, options, output);  //这里压缩options%，把压缩后的数据存放到 output 中
            options -= 10;
        }


        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 保存图片到手机相册，文件名为当前日期
     * @param activity
     * @param bitmap
     * @param bitName
     */
    public static void saveBitmapToAblum(Activity activity, Bitmap bitmap, String bitName){
        String fileName ;
        File file ;
        if(Build.BRAND .equals("Xiaomi") ){ // 小米手机
            fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+bitName ;
        }else{  // Meizu 、Oppo
            fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/"+bitName ;
        }
        file = new File(fileName);

        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out))
            {
                out.flush();
                out.close();
            // 插入图库
                MediaStore.Images.Media.insertImage(activity.getContentResolver(), file.getAbsolutePath(), bitName, null);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();

        }
        // 发送广播，通知刷新图库的显示
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
    }
}