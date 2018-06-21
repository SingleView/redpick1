package com.wlhb.hongbao.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/4/10/010.
 */

public class SaveImageUiils {

    public static void saveImageToGallery(Context context, Bitmap bmp) {

        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "xiaoyuan");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);


            if(file.exists()){
                file.delete();
                MyToastUtils.showShortToast(context, "图片已保存至\"xiaoyuan\"文件夹");
            }else {
                try {
                FileOutputStream fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                MyToastUtils.showShortToast(context, "保存成功");
            }
         catch (FileNotFoundException e) {
            MyToastUtils.showShortToast(context, "保存失败");
            e.printStackTrace();
        } catch (IOException e) {
            MyToastUtils.showShortToast(context, "保存失败");
            e.printStackTrace();
        }
            }


        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
    }
}
