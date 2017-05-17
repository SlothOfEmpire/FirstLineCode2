package asen.com.firstlinecode2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import asen.com.firstlinecode2.utils.PermissionHelper;


public class SplashActivity extends AppCompatActivity {

	private static final String TAG ="SplashActivity" ;
	private PermissionHelper permissionHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		requestWindowFeature(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		permissionHelper = new PermissionHelper(this);
		permissionHelper.getMultiPermissions();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
	                                       @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		permissionHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
	}
//
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		permissionHelper.onActivityResult(requestCode,resultCode,data);
	}

}
