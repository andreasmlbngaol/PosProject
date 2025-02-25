package di

import features.auth.views.AuthViewModel
import features.cashier.views.CashierViewModel
import features.home.views.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val modules = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CashierViewModel)
}

