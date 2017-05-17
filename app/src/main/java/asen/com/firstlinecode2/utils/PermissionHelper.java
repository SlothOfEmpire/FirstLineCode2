package asen.com.firstlinecode2.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 2017/5/8.
 */

public class PermissionHelper {

	private static final String TAG = "PermissionHelper";
	private static final int READ_PHONE_CODE = 100;
	private static final int READ_SMS_CODE = 101;
	private static final int GET_CAMERA_CODE = 102;
	private static final int WRITE_SET_CODE = 103;
	private static final int MULTI_PERMISSION_CODE = 104;
	private static final int CALL_PHONE_CODE = 105;


	private Activity mActivity;

	public PermissionHelper(Activity activity) {
		mActivity = activity;
	}

	public void getMultiPermissions() {
		ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, MULTI_PERMISSION_CODE);
	}

	public void getSetPermission() {
		PermissionModel contactModel = new PermissionModel("系统设置", Manifest.permission.WRITE_SETTINGS, "修改系统设置", WRITE_SET_CODE);
		checkPermissionGranted(contactModel);
	}

	public void getContactPermission() {
		PermissionModel contactModel = new PermissionModel("读取联系人", Manifest.permission.READ_CONTACTS, "需要从通讯录获取联系人", READ_PHONE_CODE);
		checkPermissionGranted(contactModel);
	}

	public void getSmsPermission() {
		PermissionModel contactModel = new PermissionModel("读取短信", Manifest.permission.READ_SMS, "读取短信信息", READ_SMS_CODE);
		checkPermissionGranted(contactModel);
	}

	public void getCallPhonePermission() {
		PermissionModel contactModel = new PermissionModel("打电话", Manifest.permission.CALL_PHONE, "允许拨打电话", CALL_PHONE_CODE);
		checkPermissionGranted(contactModel);
	}

	public void getCameraPermission() {

		PermissionModel contactModel = new PermissionModel("相机", Manifest.permission.CAMERA, "允许拍照", GET_CAMERA_CODE);
		checkPermissionGranted(contactModel);
	}

	private void checkPermissionGranted(PermissionModel model) {
		if (ContextCompat.checkSelfPermission(mActivity, model.permission)
				!= PackageManager.PERMISSION_GRANTED) {
			//没有获取到权限
			if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, model.permission)) {
				//需不需要进行权限的描述
				new AlertDialog.Builder(mActivity).setTitle("权限描述")
						.setMessage("权限描述的内容")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {

							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {

							}
						})
						.setCancelable(false)
						.show();
			} else {
				ActivityCompat.requestPermissions(mActivity,
						new String[]{model.permission},
						model.requestCode);
			}
		} else {
			//允许或询问状态下 都是Granted
		}
	}

	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		switch (requestCode) {
			case READ_PHONE_CODE:
				break;
			case GET_CAMERA_CODE:
				Log.i(TAG, "onRequestPermissionsResult Permission : " + permissions[0]);
				Log.i(TAG, "onRequestPermissionsResult grantResult : " + grantResults[0]);
				if (grantResults.length == 0 || PackageManager.PERMISSION_GRANTED != grantResults[0]) {
					if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0])) {
						AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
								.setTitle("权限请求")
								.setMessage("允许打开相机")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										getCameraPermission();
									}
								})
								.setCancelable(false);
						builder.show();
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
								.setTitle("设置")
								.setMessage("去应用设置相机权限")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										openApplicationSetting(GET_CAMERA_CODE);
									}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										Toast.makeText(mActivity, "彻底失去获取权限的机会", Toast.LENGTH_SHORT).show();
									}
								});
						builder.setCancelable(false);
						builder.show();

					}
				}
				break;
			case READ_SMS_CODE:

				break;
			case MULTI_PERMISSION_CODE:
				if (grantResults.length > 0) {
					if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
						if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE)) {
							Toast.makeText(mActivity, "Get CallPhone Permission Success!", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mActivity, "Get CallPhone Permission Failure!", Toast.LENGTH_SHORT).show();
						}
						if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)) {
							Toast.makeText(mActivity, "Get Camera Permission Success!", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mActivity, "Get Camera Permission Failure!", Toast.LENGTH_SHORT).show();
						}
					}
				}
				break;
			case WRITE_SET_CODE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(mActivity, "Get Write_Settings Permission Success!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mActivity, "Get Write_Settings Permission Failure!", Toast.LENGTH_SHORT).show();
					AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
							.setTitle("系统设置")
							.setMessage("允许改变系统设置")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									openApplicationSetting(WRITE_SET_CODE);
								}
							})
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {

								}
							});
					builder.setCancelable(false);
					builder.show();
				}
				break;
			case CALL_PHONE_CODE:
				Log.i(TAG, "onRequestPermissionsResult Permission : " + permissions[0]);
				Log.i(TAG, "onRequestPermissionsResult grantResult : " + grantResults[0]);
				if (grantResults.length == 0 || PackageManager.PERMISSION_GRANTED != grantResults[0]) {
					if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0])) {
						AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
								.setTitle("权限请求")
								.setMessage("允许打电话")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										getCallPhonePermission();
									}
								})
								.setCancelable(false);
						builder.show();
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
								.setTitle("设置")
								.setMessage("去应用设置打电话权限")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										openApplicationSetting(CALL_PHONE_CODE);
									}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										Toast.makeText(mActivity, "彻底失去获取权限的机会", Toast.LENGTH_SHORT).show();
									}
								});
						builder.setCancelable(false);
						builder.show();

					}
				} else {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 10086));
					mActivity.startActivity(intent);
				}
				break;
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case GET_CAMERA_CODE:
				if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA))
					Toast.makeText(mActivity, "搞定权限了", Toast.LENGTH_SHORT).show();
				Toast.makeText(mActivity, "没有给予权限", Toast.LENGTH_SHORT).show();
				break;
			case WRITE_SET_CODE:
				if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED)
					Toast.makeText(mActivity, "搞定权限了", Toast.LENGTH_SHORT).show();
				Toast.makeText(mActivity, "没有给予权限", Toast.LENGTH_SHORT).show();
				break;
			case CALL_PHONE_CODE:
				if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
					Log.i(TAG, "call phone permission has granted ! you can call phone now");
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 10086));
					mActivity.startActivity(intent);
				} else {
					Toast.makeText(mActivity, "没有给予权限", Toast.LENGTH_SHORT).show();

				}
				break;
		}
	}

	private void openApplicationSetting(int requestCode) {
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.parse("package:" + mActivity.getPackageName()));
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		mActivity.startActivityForResult(intent, requestCode);
	}

	private void openWriteSetting(int requestCode) {
		Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + mActivity.getPackageName()));
		mActivity.startActivityForResult(intent, requestCode);
	}

	private static class PermissionModel {

		/**
		 * 权限名称
		 */
		public String name;

		/**
		 * 请求的权限
		 */
		public String permission;

		/**
		 * 解析为什么请求这个权限
		 */
		public String explain;

		/**
		 * 请求代码
		 */
		public int requestCode;

		public PermissionModel(String name, String permission, String explain, int requestCode) {
			this.name = name;
			this.permission = permission;
			this.explain = explain;
			this.requestCode = requestCode;
		}
	}
}
