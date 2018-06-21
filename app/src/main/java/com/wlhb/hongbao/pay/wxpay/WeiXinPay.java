package com.wlhb.hongbao.pay.wxpay;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlhb.hongbao.bean.Pay;


/**
 * Created by qiyunfeng on 15/9/24.
 */
public class WeiXinPay {

    private Context context;
    private Pay mWXPayingEntity;
    private PayReq req;
    private IWXAPI msgApi;

    public WeiXinPay(Context context, Pay wxPayingEntity) {
        this.context = context;
        this.mWXPayingEntity = wxPayingEntity;
        genPayReq();
    }

    private void genPayReq() {
        msgApi = WXAPIFactory.createWXAPI(WeiXinPay.this.context, null);
        req = new PayReq();
        Constants.APP_ID = mWXPayingEntity.appid;
        req.appId = mWXPayingEntity.appid;
        req.partnerId = mWXPayingEntity.partnerid;
        req.prepayId = mWXPayingEntity.prepayid;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = mWXPayingEntity.noncestr;
        req.timeStamp = mWXPayingEntity.timestamp;
        req.sign = signNum();
        msgApi.registerApp(mWXPayingEntity.appid);
        msgApi.sendReq(req);
    }

    private String signNum() {
        String stringA =
                "appid=" + req.appId
                        + "&noncestr=" + req.nonceStr
                        + "&package=" + req.packageValue
                        + "&partnerid=" + req.partnerId
                        + "&prepayid=" + req.prepayId
                        + "&timestamp=" + req.timeStamp;


        String stringSignTemp = stringA + "&key=" + "VmMmlActUT7de4nwD1FFWFIFZVXE2EHP";
        String sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();
        return sign;
    }

    /**
     * 微信支付签名算法sign
     *
     * @param parameters
     * @return
     */
//    public static String createSign(SortedMap<Object, Object> parameters) {
//
//        StringBuffer sb = new StringBuffer();
//        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
//        Iterator it = es.iterator();
//        while (it.hasNext()) {
//            @SuppressWarnings("rawtypes")
//            Map.Entry entry = (Map.Entry) it.next();
//            String k = (String) entry.getKey();
//            Object v = entry.getValue();
//            if (null != v && !"".equals(v) && !"sign".equals(k)
//                    && !"key".equals(k)) {
//                sb.append(k + "=" + v + "&");
//            }
//        }
//        sb.append("key=" + Constants.KEY); //KEY是商户秘钥
//        String sign = AbMd5.MD5(sb.toString()).toUpperCase();
//        return sign;
//    }

}
