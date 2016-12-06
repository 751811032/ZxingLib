# ZxingLib
ZxingLib 竖屏版本

##Installation

You can install FcPermissions by adding the following dependency to your build.gradle:
	
	allprojects {
		repositories{
			jcenter()
			maven { url "https://jitpack.io" }
		}
	}
		
	dependencies{
		compile 'com.github.751811032:ZxingLib:v1.0'
	}

##Usage
write The following information in your AndroidManifest:

	<activity
		android:name="com.google.zxing.client.android.CaptureActivity"
		android:clearTaskOnLaunch="true"
		android:screenOrientation="portrait"
		android:stateNotNeeded="true"
		android:windowSoftInputMode="stateAlwaysHidden" >
		<intent-filter>
			<action android:name="com.google.zxing.client.android.SCAN" />
			<category android:name="android.intent.category.DEFAULT" />
		</intent-filter>
	</activity>
   
Start CaptureActivity in your Activity

	Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
	startActivityForResult(intent, 1);
	
Override onActivityResult in your Activity

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 1:
				if (resultCode == RESULT_OK) {
					try {
						String result = data.getStringExtra("scan_result");
						Log.i("reesult", "resultresult:" + result);
					} catch (Exception e) {
						e.printStackTrace();
						Log.i("reesult", "scan Fail");
					}
				} else if (resultCode == RESULT_CANCELED) {
					Log.i("reesult", "scan Cancel");
				}
				break;
			default:
				break;
		}
	}
   
