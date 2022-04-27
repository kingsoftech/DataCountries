package com.flyconcept.datacountries

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.flyconcept.datacountries.model.network.CountriesService
import com.flyconcept.datacountries.viewmodel.ListViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import org.mockito.InjectMocks
import org.mockito.Mock
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {
    @get:Rule
    @Mock
    lateinit var countryServices:CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()
    var rule =InstantTaskExecutorRule()

    @Before
    fun setUpRxSchedulers(){
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return  ExecutorScheduler.ExecutorWorker(
                    {
                        it.run()
                    }
                )

            }

        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{scheduler-> immediate}
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler-> immediate }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler-> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler-> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler-> immediate }
    }
}