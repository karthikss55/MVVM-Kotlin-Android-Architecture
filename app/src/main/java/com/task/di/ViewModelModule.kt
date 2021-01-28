package com.task.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.ui.ViewModelFactory
import com.task.ui.component.gallery.ImageListViewModel
import com.task.ui.component.recipes.RecipesListViewModel
import com.task.ui.component.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Karthik S S
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecipesListViewModel::class)
    abstract fun bindUserViewModel(viewModel: RecipesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ImageListViewModel::class)
    internal abstract fun bindImageListViewModel(viewModel: ImageListViewModel): ViewModel
}
