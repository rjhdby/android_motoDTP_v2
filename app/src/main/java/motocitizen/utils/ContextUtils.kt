package motocitizen.utils

import android.app.Activity
import android.app.Fragment
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import motocitizen.presentation.base.viewmodel.VMFragment

/*
fun Context.copyToClipBoard(text: String) {
    (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip = ClipData.newPlainText("text", text)
}

fun Context.showToast(text: String) = runOnUiThread { Toast.makeText(this, text, Toast.LENGTH_LONG).show() }

fun Context.showToast(resource: Int) = runOnUiThread { showToast(getString(resource)) }
*/
fun Context.makeDial(number: String) = try {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
    startActivity(intent)
} catch (e: Exception) {
    e.printStackTrace()
}
/*
fun Activity.goTo(target: Screens, bundle: Map<String, Any>) {
    val intentBundle = Bundle()
    bundle.forEach {
        when (it.value) {
            is Int    -> intentBundle.putInt(it.key, it.value as Int)
            is String -> intentBundle.putString(it.key, it.value as String)
        }
    }
    val intent = Intent(this, target.activity)
    intent.putExtras(intentBundle)
    startActivity(intent)
}

fun Activity.goTo(target: Screens) = startActivity(Intent(this, target.activity))

fun Fragment.goTo(target: Screens) = activity.goTo(target)
*/
fun Activity.toExternalMap(latLng: LatLng) {
    val uri = "geo:${latLng.latitude},${latLng.longitude}"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}

fun Activity.changeFragmentTo(containerView: Int, fragment: Fragment) = fragmentManager
    .beginTransaction()
    .replace(containerView, fragment)
    .commit()

fun <T : View> Fragment.bindView(id: Int) = lazy { activity.findViewById<T>(id) }

fun <T : View> Activity.bindView(id: Int) = lazy { findViewById<T>(id) }