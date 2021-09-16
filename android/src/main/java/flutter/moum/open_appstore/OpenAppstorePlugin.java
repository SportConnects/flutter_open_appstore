package flutter.moum.open_appstore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** OpenAppstorePlugin */
public class OpenAppstorePlugin implements MethodCallHandler, FlutterPlugin {
  
  private Context applicationContext;
  private MethodChannel channel;
  private static final String CHANNEL_NAME = "moum/open_appstore";

  /**
   * Plugin registration.
   */

  @Override
  public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
    channel.setMethodCallHandler(this);
    applicationContext = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding flutterPluginBinding) {
    channel.setMethodCallHandler(null);
    applicationContext = null;
  }
  
  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("openappstore")) {
		result.success("Android " + android.os.Build.VERSION.RELEASE);
		String android_id = call.argument("android_id");
		String manufacturer = android.os.Build.MANUFACTURER;
		if (manufacturer.equals("Amazon")) {
			applicationContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("amzn://apps/android?p=" + android_id)));
		} else {
			try {
				applicationContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + android_id)));
			} catch (android.content.ActivityNotFoundException e) {
				applicationContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + android_id)));
            }
		}
		result.success(null);
	} else {
		result.notImplemented();
    }
  }
}
