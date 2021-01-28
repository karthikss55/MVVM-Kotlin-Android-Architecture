package com.task.di

import com.task.ui.component.gallery.ImageListActivity
import com.task.ui.component.recipes.RecipesListActivity
import com.task.ui.component.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {
    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeHomeActivity(): RecipesListActivity

    @ContributesAndroidInjector()
    abstract fun contributeImageListActivity(): ImageListActivity
}
