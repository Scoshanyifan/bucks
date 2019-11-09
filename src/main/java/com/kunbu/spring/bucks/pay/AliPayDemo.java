package com.kunbu.spring.bucks.pay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-10-14 14:54
 **/
public class AliPayDemo {

    public static void main(String[] args) {

//        String notifyStr = "gmt_create=2019-10-13 13:10:49, charset=UTF-8, seller_email=3566129564@qq.com, subject=黄桃豆饮, sign=ypFtcseNyb+xLkZDkSlsIzmICMt98o/19qgvdnoeROxfB48/LJlipcV/PcH6h+UwD74sFAUXZFv7Zw9/qYEEr+TVgysXiL9ZXeZad2/tSCiQQPpjTFEbv6fxKZJX9ohj6DUL0qAU3BJgeimuhqdbB8VwLLYyv4+8IZ39QRcFt4mTV4gVRufoIqiapRBt4Ch97QNMYrND9SOGGcCiiYTbw0IVa29HIPvrs6oofL+DNiyrNlImG47zhDZOeqxBf9PULnrpAmcyiTbkEjkc2eJqefadKc67kTu1XFzHGHxqtXnosPgBAmdop/hHg5+sQIIDDdRoaW03V8CXocZyC7/+6w==, body=黄桃豆饮，订单号: 2019101313102938273B77E33842443A, buyer_id=2088922259502174, invoice_amount=3.00, notify_id=2019101300222131050002170506594279, fund_bill_list=[{\"amount\":\"3.00\",\"fundChannel\":\"PCREDIT\"}], notify_type=trade_status_sync, trade_status=TRADE_SUCCESS, receipt_amount=3.00, buyer_pay_amount=3.00, app_id=2019080666130255, sign_type=RSA2, seller_id=2088721402105860, gmt_payment=2019-10-13 13:10:50, notify_time=2019-10-14 13:39:34, version=1.0, out_trade_no=2019101313102938273B77E33842443A, total_amount=3.00, trade_no=2019101322001402170593215406, auth_app_id=2019080666130255, buyer_logon_id=155****3712, point_amount=0.00";
        String notifyStr = "gmt_create=2019-10-14 14:46:37, charset=UTF-8, seller_email=feiyue@lhcoffeetime.com, subject=卡布奇诺, sign=VbgGdUF+ZKOs8dLqaSVl7O6lODUsmNyVmAbfsVb2KreuTuhKG3dmAk4idKIfl0Eob1pPt/+nsn/WICfERyAVecGHrGNT8D1bkKyS1a0hcC2P0o2shKA23d1S9KD5bojY7Y4BHJrTGOpsblZMAIVOmqxs20RoqTJtniam1O3UXZyYJyACVkEoezZHj0AOhMrbiLW0wmCUNGNQXOd4mMz5+4WhRbkh/qxkxd+BomxNH5a4CnGn/imAqFVGxUdGF3oRFUy/sgjlzdqt0wLGi+gGly+CQbBHrqFV/v4gdjtyti8BSKy68gmvxC/oM/zRsC27FVgREUC6dJ/vgIhQcXjMsA==, body=卡布奇诺，订单号: 201910141446243780B3DCDD86BA74E6, buyer_id=2088202281502303, invoice_amount=1.00, notify_id=2019101400222144638002301402341918, fund_bill_list=[{\"amount\":\"1.00\",\"fundChannel\":\"PCREDIT\"}], notify_type=trade_status_sync, trade_status=TRADE_SUCCESS, receipt_amount=1.00, buyer_pay_amount=1.00, app_id=2019022563370239, sign_type=RSA2, seller_id=2088431558497833, gmt_payment=2019-10-14 14:46:37, notify_time=2019-10-14 14:46:38, version=1.0, out_trade_no=201910141446243780B3DCDD86BA74E6, total_amount=1.00, trade_no=2019101422001402301401052677, auth_app_id=2019022563370239, buyer_logon_id=hua***@yahoo.com.cn, point_amount=0.00";


        List<String> items = Splitter.on(", ").splitToList(notifyStr);
        System.out.println(">>> itemList size: " + items.size());
//        String[] items = notifyStr.split(", ");
//        System.out.println(">>> items size: " + items.length);

        Map<String, String> params = Maps.newHashMap();
        for (String i : items) {
            String[] kv = i.split("=");
            params.put(kv[0], kv[1]);
            System.out.println("key=" + kv[0] + ", value=" + kv[1]);
        }
        //TODO 字符串分隔 ==不能分隔，原因不详
        String sign = "VbgGdUF+ZKOs8dLqaSVl7O6lODUsmNyVmAbfsVb2KreuTuhKG3dmAk4idKIfl0Eob1pPt/+nsn/WICfERyAVecGHrGNT8D1bkKyS1a0hcC2P0o2shKA23d1S9KD5bojY7Y4BHJrTGOpsblZMAIVOmqxs20RoqTJtniam1O3UXZyYJyACVkEoezZHj0AOhMrbiLW0wmCUNGNQXOd4mMz5+4WhRbkh/qxkxd+BomxNH5a4CnGn/imAqFVGxUdGF3oRFUy/sgjlzdqt0wLGi+gGly+CQbBHrqFV/v4gdjtyti8BSKy68gmvxC/oM/zRsC27FVgREUC6dJ/vgIhQcXjMsA==";
        params.put("sign", sign);



//        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6GjXBYAFQ3ig88Ll74FYEdzgKhzcvZaJx1pd7+9VjLG5hs2L2ZPUogx2AsRsLmKpb+rwekwRr06m/DbYuVx2yNeKk9Fvd/c79wJ6k7QVYnuffAQj1sIXkHj5HfNMpUs8WFNW/T3e8/XWfkYHioSqu6F0TfmKRb8RZisTyaUTKT6BPDqprvBkFru3bLYXHzV8cGO52zTgdBa6EKRgntPKZtyiuiOaW2vbqWsttd6Q/15upZsGOD4IIyCen3kJV2woNH+iSq4MBNLdRtlT58Oxq2Yso2cHRpFv5mZYPNOaJiNiDaKcJ0bPnZsgVJxUpuWaffJFVrkGTx8QCnJ0uRDp/wIDAQAB";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgom1MrJKjIuJHi2vT7/mCRX4k549g5ndgWUbw/G2bq/o1gzBb4kgOCZXFPJsxpT7eOjz+/h9oGolYAQz2dhPRL9W6kcMHSvJkP/G22iHdQgbPVAWqCsRvoUn/Jnvr6BNU753DjzwcXitNGMZ2FS9NgD2wF9I5TkRQPPLIdazckBEHu14Dl+0Po6kvApNVBRc0LDZ1V5K05IcXvRFy7E0V1yr5nz8ygIYtM61e5nOfL+s2W+fUdFQMRs0qRWDxyTGDcLAqTflNYo/Aqma9LpvIeyI9aKBgHYfI91jaa0H+oyAE2wh4sUaS/R9tToVgop6vJISbrddv5vxv6JekgVD3QIDAQAB";
        String signType = "RSA2";

        try {
            boolean check = AlipaySignature.rsaCheckV1(
                    params,
                    publicKey,
                    AlipayConstants.CHARSET_UTF8,
                    signType);
            System.out.println(check);


        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> generateParams(String notifyStr) {
        Map<String, String> params = Maps.newHashMap();

        JSONObject notifyJon = JSONObject.parseObject(notifyStr);


        return params;
    }
    

}
