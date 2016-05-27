package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Title MD5Utils
 * @Package com.cmonbaby.utils
 * @Description MD5加密方法
 * @author 西西
 * @date 2015-12-08 下午 5:52
 * @version 1.1.2
 */
public class MD5Utils {

	/**
	 * MD5加密方法
	 * @param s 传入需要加密的字符串
	 * @return 返回加密好的字符串
	 */
	public static String encode(String s) {
		try {
			// 获取到数字消息的摘要器
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// 执行加密操作
			byte[] result = digest.digest(s.getBytes());
			StringBuilder sb = new StringBuilder();
			// 将每个byte字节的数据转换成16进制的数据
			for (int i = 0; i < result.length; i++) {
				int number = result[i] & 0xff;
				String str = Integer.toHexString(number);// 将十进制的number转换成十六进制数据
				if (str.length() == 1) {// 判断加密后的字符的长度，如果长度为1，则在该字符前面补0
					sb.append("0");
					sb.append(str);
				} else {
					sb.append(str);
				}
			}
			return sb.toString();// 将加密后的字符转成字符串返回
		} catch (NoSuchAlgorithmException e) {// 加密器没有被找到，该异常不可能发生。因为我们填入的“MD5”是正确的
			e.printStackTrace();
			// CNA'T REACH;
			return "";
		}
	}
	
	public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	
	//字节数组转化为16进制字符串
    public static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }
 
    //字节数组md5算法
    public static byte[] md5 (byte [] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getMD5ForFile(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHashString(digest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getHashString(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();
		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}
		return builder.toString();
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

}
