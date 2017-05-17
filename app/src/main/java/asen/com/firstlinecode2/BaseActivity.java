package asen.com.firstlinecode2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 2017/4/25.
 */

public  class BaseActivity extends AppCompatActivity{
	public static void activityStart(Context context,Class targetC){
		Intent intent = new Intent(context,targetC);
		context.startActivity(intent);
	}
}
