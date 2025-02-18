package di

import features.auth.views.AuthViewModel
import features.cashier.views.CashierViewModel
import features.home.views.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val modules = module {
    singleOf(::TestClassImpl).bind<TestClass>()
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CashierViewModel)
}

interface TestClass {
    fun helloWorld(): String = "Hello World!"
}

class TestClassImpl : TestClass {
    override fun helloWorld(): String = "Hello World from TestClassImpl!"
}