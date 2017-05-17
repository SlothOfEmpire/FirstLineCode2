package asen.com.firstlinecode2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private  String Tag = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(Tag,"onCreate ----");
		setContentView(R.layout.activity_main);
		if (savedInstanceState!=null){
			String saveContent = savedInstanceState.getString(KEY_SAVE_INSTANCE);
			if (!TextUtils.isEmpty(saveContent)){
				TextView tvContent = (TextView) findViewById(R.id.tv_save);
				tvContent.setText(saveContent);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_add:
				Toast.makeText(this,"add",Toast.LENGTH_SHORT).show();
				activityStart(this,SecondActivity.class);
				break;
			case R.id.menu_remove:
				actionView();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	private static final String KEY_SAVE_INSTANCE = "key_save";
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_SAVE_INSTANCE,"My sweet honey : Huiying");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(Tag,"onRestart ----");
	}

	private void actionView(){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("https://www.baidu.com"));
		startActivity(intent);
	}
}
