package com.task.ui.component.gallery

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.task.R
import com.task.data.Resource
import com.task.databinding.LayoutImageListActivityBinding
import com.task.ui.ViewModelFactory
import com.task.ui.base.BaseActivity
import com.task.ui.component.gallery.adapter.ImageListAdapter
import com.task.utils.*
import javax.inject.Inject

class ImageListActivity : BaseActivity() {

    private lateinit var binding: LayoutImageListActivityBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageListViewModel: ImageListViewModel

    private lateinit var imageListAdapter: ImageListAdapter


    override fun initializeViewModel() {
        imageListViewModel = viewModelFactory.create(ImageListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.image_gallery)
        initAdapter()
    }

    override fun observeViewModel() {
        observeToast(imageListViewModel.showToast)
        observe(imageListViewModel.loadingStatus, ::handleImageLoadingOrErrorState)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    override fun initViewBinding() {
        binding = LayoutImageListActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun handleImageLoadingOrErrorState(status: Resource<Any>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { imageListViewModel.showToastMessage(it) }
            }
        }
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvImageList.toGone()
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) View.GONE else View.VISIBLE
        binding.rvImageList.visibility = if (show) View.VISIBLE else View.GONE
        binding.pbLoading.toGone()
    }

    private fun initAdapter() {
        imageListAdapter = ImageListAdapter()
        binding.rvImageList.apply {
            layoutManager =
                LinearLayoutManager(this@ImageListActivity, RecyclerView.VERTICAL, false)
            adapter = imageListAdapter
        }
        imageListViewModel.imageData.observe(this, Observer {
            imageListAdapter.submitList(it)
        })
    }
}