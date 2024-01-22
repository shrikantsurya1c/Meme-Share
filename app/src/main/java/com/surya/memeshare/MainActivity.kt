package com.surya.memeshare





import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {

    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        loadmeme()

    }



    private fun loadmeme() {

        findViewById<Button>(R.id.shareButton).isEnabled = false
        findViewById<Button>(R.id.nextButton).isEnabled = false
        findViewById<ProgressBar>(R.id.progressbar).isVisible = true


        val url = "https://meme-api.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")

                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progressbar).isVisible = false
                        findViewById<Button>(R.id.nextButton).isEnabled = true
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        findViewById<ProgressBar>(R.id.progressbar).isVisible = false
                        findViewById<Button>(R.id.nextButton).isEnabled = true
                        findViewById<Button>(R.id.shareButton).isEnabled = true
                        return false
                    }
                }).into(findViewById(R.id.memeImageView))
                findViewById<ProgressBar>(R.id.progressbar).visibility(View.GONE)
            },

            { error ->
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_LONG).show()

            })

// Access the RequestQueue through your singleton class.
        MySingleton.MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

        fun nextme(view: View) {
            loadmeme()
        }
    fun shareme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,Checkout this meme i got from Reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this meme using..")
        startActivity(chooser)
    }
}

private fun ProgressBar.visibility(visible: Int) {

}
