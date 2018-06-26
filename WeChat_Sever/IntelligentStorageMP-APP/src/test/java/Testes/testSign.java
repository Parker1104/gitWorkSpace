package Testes;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class testSign {
      public static void main(String[] args) {
    	    String shortname = "01";
    	  
			String sShowStartBoxNo = "";
			String sShowEndBoxNo = "";
			
			String Boxcode="1";
			int sum=10;
			
			sShowStartBoxNo = Boxcode;	
			sShowEndBoxNo = String.valueOf((Integer.parseInt(Boxcode)+sum-1));
			while (sShowStartBoxNo.length()<4){
				sShowStartBoxNo = "0" + sShowStartBoxNo;
			}
			while (sShowEndBoxNo.length()<4){
				sShowEndBoxNo = "0" + sShowEndBoxNo;
			}
			System.out.println("1          sShowStartBoxNo="+sShowStartBoxNo+"   sShowEndBoxNo="+sShowEndBoxNo);
 
			//if (te.getNumbering() != 0) {//如果按区域显示的话在前面加上区域简称
			if (true) {
				sShowStartBoxNo = shortname + sShowStartBoxNo;
				sShowEndBoxNo = shortname + sShowEndBoxNo;
			}
			System.out.println("2 按照区编码  sShowStartBoxNo="+sShowStartBoxNo+"  sShowEndBoxNo="+sShowEndBoxNo);
			
			
			
    	  openBox();
    	  
    	  
	  }
	   public static  String openBox(){
		/* 
                                               传过来的    sign=1
				   testEnvironment=true
				   UserKey=app_cqjdhygy_180000  app_key=app_cqjdhygy  box_no=1  format=json  gui_no=210017005b4031030c010000
				   method=dcdz.app.mg.box.open  open_seq=20171018190727497242  open_type=9  open_user=app_cqjdhygy  sign_method=1
				   timestamp=2017-11-07 08:44:00  sign=A9BD3B91DBBF878D625843E3A78C20F6
				   开箱请求参数 app_key=app_cqjdhygy  format=json method=dcdz.app.mg.box.open open_seq=20171018190727497242 open_type=9
				  open_user=app_cqjdhygy sign_method=1 timestamp=2017-11-07 08:44:00 gui_no=210017005b4031030c010000  box_no=1 sign=A9BD3B91DBBF878D625843E3A78C20F6
         */
		   String   secret="app_cqjdhygy_180000";  
		   String   app_key="app_cqjdhygy";  
		   String   box_no="1";   
		   String   format="json";  
		   String   gui_no="444357463138303130303137"; 
		   String   method="dcdz.app.mg.box.open";   
		   
		   String   open_seq="20171018190727497242";   
		   String   open_type="9";   
		   String   open_user="app_cqjdhygy";   
		   String   sign_method="1"; 
		   String   timestamp="2018-01-30 11:02:00"; 
		   
	 
		  // String sign="A9BD3B91DBBF878D625843E3A78C20F6"; 
		   String sign=  get_sign_md5openBox(  secret,  app_key,  box_no,  format,  gui_no,  method,
	    		                    open_seq,  open_type,  open_user,  sign_method,  timestamp   );
		   System.out.println("---------sign="+sign);
          return null;
	  }
	   
	   
	     public  static  String get_sign_md5openBox(String secret,String app_key,String box_no,String format,String gui_no,String method,
    		     String open_seq,String open_type,String open_user,String sign_method,String timestamp   ){
	          StringBuffer signBuffer = new StringBuffer();
                 signBuffer.append(secret)
		        .append("app_key").append(app_key)
		        .append("box_no").append(box_no)
		        .append("format").append(format)
		        .append("gui_no").append(gui_no)
		        .append("method").append(method)
		        
		        .append("open_seq").append(open_seq)
		        .append("open_type").append(open_type)
		        .append("open_user").append(open_user)
		        .append("sign_method").append(sign_method)
		        .append("timestamp").append(timestamp)
		        .append(secret);

                 String sign_md5 = signMD5(signBuffer.toString());
   
    	 return sign_md5;
     }
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	   //=================基础的东西====================
	   public static final String CHARSET_UTF8     = "utf-8";
	   public static String signMD5(String data){
	     	byte[] bytes;
	 		try {
	 			bytes = encryptMD5(data);
	 			return byte2hex(bytes);
	 		} catch (IOException e) {
	 			e.printStackTrace();
	 		}
	     	return "";
	    }
	 	private static byte[] encryptMD5(String data) throws IOException {
	 		byte[] bytes = null;
	 		try {
	 			MessageDigest md5=MessageDigest.getInstance("MD5");
	 			bytes = md5.digest(data.getBytes(CHARSET_UTF8));
	 		} catch (NoSuchAlgorithmException e) {
	 			// TODO Auto-generated catch block
	 			throw new IOException(e.toString());
	 		}
	 	    return bytes;
	 	}
	 	private static String byte2hex(byte[] bytes) {
	 	    StringBuilder sign = new StringBuilder();
	 	    for (int i = 0; i < bytes.length; i++) {
	 	        String hex = Integer.toHexString(bytes[i] & 0xFF);
	 	        if (hex.length() == 1) {
	 	            sign.append("0");
	 	        }
	 	        sign.append(hex.toUpperCase());
	 	    }
	 	    return sign.toString();
	 	}
	     
}
