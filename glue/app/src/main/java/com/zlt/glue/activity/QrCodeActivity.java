package com.zlt.glue.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.zxing.Result;
import com.zlt.framework.core.CoreManager;
import com.zlt.framework.qrcode.MipcaActivityCapture;

/**
 * Author: zlt
 * Date: 2016-07-06
 * Time: 11:05
 * 扫码
 */
public class QrCodeActivity extends MipcaActivityCapture {

    /**
     * 处理扫描结果
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        //扫描获取的 编码 不为空
        if(!TextUtils.isEmpty(result.getBarcodeFormat().toString())){
            //拍码后返回的编码格式
            String mBarcodeFormat = result.getBarcodeFormat().toString();
            if(mBarcodeFormat.equals(DATA_MATRIX)){
                CODE_TYPE = 3;
            }else if(mBarcodeFormat.equals(QR_CODE)){
                CODE_TYPE = 2;
            }else {
                CODE_TYPE = 1;
            }
            CoreManager.getLog().e("---> (1一维码、  2、二维码    3、其他码) " + CODE_TYPE);
            Toast.makeText(this,result.getText(), Toast.LENGTH_LONG).show();
        }
        if(CODE_TYPE==1)
        {
            //一维码
        }else if(CODE_TYPE==2)
        {
            //二维码

        }
        continueScan();
    }

}
