package motocitizen.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

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