package haituntiandi.com.haituntiandi.utils;

/**
 * zhangxin
 * APP更新类
 */
public class AppUpDateManage {
    /*public static void inspectVersion(final Context mContext){
        UserApi.inspectVersion(mContext, new requestCallback() {
            @Override
            public void onSuccess(Object object) {
                try {
                    UpdateBean bean = (UpdateBean) object;
                    if (null == bean){
                        return;
                    }
                    if (null == bean.getData()){
                        return;
                    }
                    if (null == bean.getData().getUpdateFlag()){
                        return;
                    }
                    switch (bean.getData().getUpdateFlag()){
                        case "0":
                            //无新版本
                            break;
                        case "1":
                            //非强制更新
                            showUpdateDialog(mContext,bean,1);
                            break;
                        case "2":
                            //强制更新
                            showUpdateDialog(mContext,bean,2);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String errCode, String errMessage) {
                try {
                    LogUtil.e(AppUpDateManage.class.getSimpleName(), "inspectVersion() onFailed:"+ errMessage);
//                    ToastUtil.showMsg(errMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void showUpdateDialog(final Context mContext, final UpdateBean bean, int tag) {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("更新提示");
            builder.setMessage(bean.getData().getNewVerMessage()+ "");
            builder.setCancelable(false);
            builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new RxPermissions((Activity) mContext).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) {
                                    if (aBoolean) {
                                        dialog.dismiss();
                                        loadNewVersionProgress(mContext,bean.getData().getNewVerUrl());
                                    } else {
                                        ToastUtil.showMsg("您拒绝了下载所需要的权限");
                                    }
                                }
                            });
                }
            });
            if (tag == 1){
                //      1     非强更          2   强更（取消以后更新这个按钮）
                builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            Dialog noticeDialog = builder.create();
            noticeDialog.setCanceledOnTouchOutside(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadNewVersionProgress(final Context mContext, final String uri) {
        try {
            final ProgressDialog pd;    //进度条对话框
            pd = new ProgressDialog(mContext);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(false);
            pd.setMessage("正在下载更新");
            pd.show();
            //启动子线程下载任务
            new Thread(){
                @Override
                public void run() {
                    try {
                        File file = getFileFromServer(uri, pd);
//                        sleep(2000);
                        installApk(mContext,file);
                        pd.dismiss(); //结束掉进度条对话框
//                        System.exit(0);
                    } catch (Exception e) {
                        //下载apk失败
                        ToastUtil.showMsg("更新失败,请稍后重试。。。");
                        e.printStackTrace();
                        LogUtil.e(AppUpDateManage.class.getSimpleName(), "更新失败."+ e.getMessage());
//                        TCAgent.onEvent();
                    }
                }}.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    *//**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行）
     *//*
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(uri);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time= System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time+"uniBank.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }
    *//**
     * 安装apk
     *//*
    protected static void installApk(Context context, File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
