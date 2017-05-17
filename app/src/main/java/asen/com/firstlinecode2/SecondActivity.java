package asen.com.firstlinecode2;

import android.os.Bundle;
import android.view.View;

public class SecondActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		findViewById(R.id.bt_next).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity.activityStart(SecondActivity.this,MainActivity.class);
			}
		});
	}
}
