package com.flyconcept.datacountries


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flyconcept.datacountries.model.Country
import com.flyconcept.datacountries.model.network.CountriesService
import com.flyconcept.datacountries.viewmodel.ListViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {


    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCountriesSuccess() {
        val country = Country("countryName", "capital", "url")
        val countriesList = arrayListOf(country)

        testSingle = Single.just(countriesList)

        `when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.countries.value?.size)
        Assert.assertEquals(false, listViewModel.countryLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getCountriesFail() {
        testSingle = Single.error(Throwable())

        `when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(true, listViewModel.countryLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

//    @Before
//    fun setUpRxSchedulers() {
//        val immediate = object : Scheduler() {
//            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
//                return super.scheduleDirect(run, 0, unit)
//            }
//
//            override fun createWorker(): Worker {
//                return ExecutorScheduler.ExecutorWorker(Executor { it.run() },true, true)
//            }
//        }
//
//        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
//        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
//        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
//        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
//    }
}
class RxImmediateSchedulerRule  : TestRule {

    private val immediate = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() },false,true)
        }

        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
            // this prevents StackOverflowErrors when scheduling with a delay
            return super.scheduleDirect(run, 0, unit)
        }


    }


    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }

                try {
                    base?.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}