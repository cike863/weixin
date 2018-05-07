package cike.util;

import java.util.Arrays;

public class CheckUtil {
    public static final String token = "cike863";

    //String echostr=req.getParameter("echostr");
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        System.out.println("checkSignature=" + arr);
        //排序
        Arrays.sort(arr);
        //生成字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        //SHA1加密
        String temp = SHA1.encode(content.toString());
        System.out.println("checkSignature,temp=" + temp);
        return temp.equals(signature);
    }
}
