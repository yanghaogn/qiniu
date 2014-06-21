 

import java.io.File;

import org.json.JSONException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;

public class UploadFile {

	static{
		 Config.ACCESS_KEY = "xNhLB5xOGkEi14r23ILfMy1_Vv0wr2PbiTAz8mI1";
	        Config.SECRET_KEY = "3_wk7HRdA66r5OFmWcovoWIz6F1WvUQ9yHbUaRTw";
	}
	public static void deleteFile(String bucketName,String key ){
 
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        RSClient client = new RSClient(mac);
        CallRet res=client.delete(bucketName, key);
        System.out.println(res.getResponse());
	}
	public static void uploadFile(String bucketName,String key,String file){
		 
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        RSClient client = new RSClient(mac);
        client.delete(bucketName, key);
           // 请确保该bucket已经存在
        
        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken;
		try {
			uptoken = putPolicy.token(mac);
			 PutExtra extra = new PutExtra();
		       
		        String localFile =file;
		        PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
		        System.out.println("response\t"+ret.response);
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		if(args.length<=0||null==args[0]){
			System.err.println("Please set the input file");
			return;
		}
		File file=new File(args[0]);
		 String key = file.getName();
		 String bucketName = "ptmind";
		 deleteFile(bucketName,key);
		 uploadFile(bucketName,key,file.toString());
	       
	}

}
