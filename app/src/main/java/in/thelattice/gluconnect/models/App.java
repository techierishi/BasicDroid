package in.thelattice.gluconnect.models;
import android.content.Context;
import android.util.Log;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;

public class App implements Serializable{

	private static final long serialVersionUID = -5544349509820553767L;
	public User user;
	public String session_id;
	public String session_name;
	private static App instance = null;
    public Setting setting=null;
    public Context context;

	public Setting getSetting(Context context) {
		if(setting==null){
			String FILENAME = "Quitchen1";
			try{
					FileInputStream fis = context.openFileInput(FILENAME);
					ObjectInputStream is =new ObjectInputStream(fis);
					this.setting =(Setting)is.readObject();
					is.close();
			}catch(Exception e){
				return setting;
			}
		}
		return setting;
	}

	public void setSetting(Context context,Setting setting) {
		this.setting = setting;
		String FILENAME = "Quitchen1";
		try{
				FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
				ObjectOutputStream out = new ObjectOutputStream(fos);
				out.writeObject(setting);
				out.close();
				fos.close();
		}catch(Exception e){
			Log.d("getSetting",e.getMessage());
			}
	}


	public App(){
		user = new User();

	}


	public static App getAppInstance() {
	      if(instance == null) {
	         instance = new App();
	      }
	      return instance;
	}
	public static String priceFormatter(float price) {
	      String formattedPrice;
	      DecimalFormat df = new DecimalFormat("#0.00");
	      formattedPrice="$"+df.format(price);
	      return formattedPrice;
	}
}
