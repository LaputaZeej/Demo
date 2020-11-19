package com.laputa.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.laputa.webview.util.Constant.KEY_URL
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {
    private var mUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        web_view.setCallback(onPageStarted = {
            pb.visibility = View.VISIBLE
        }, onPageFinished = {
            pb.visibility = View.GONE
        }, onError = {
            pb.visibility = View.GONE
            Toast.makeText(requireContext(), "web-$it", Toast.LENGTH_SHORT).show()
        }, onTitleUpdate = {
            // TODO
            val activity = requireActivity()
            if (activity is WebViewActivity) {
                activity.updateTitle(it)
            }
        })
        loadUrl()
    }

    private fun loadUrl() {
        mUrl?.run {
            web_view.loadUrl(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUrl = arguments?.getString(KEY_URL)
    }

    companion object {


        fun newInstance(url: String, extras: Bundle? = null): Fragment {
            val data = extras ?: Bundle()
            return WebViewFragment().apply {
                arguments = data.apply {
                    putString(KEY_URL, url)
                }
            }
        }
    }
}