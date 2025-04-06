package com.example.projectappmovil.navegation

open class AppScreens(val route: String) {
    object MainScreen : AppScreens("main_screen")
    object RegisterScreen : AppScreens("register_screen")
    object ForgotScreen : AppScreens("forgot_screen")
    object InicioScreen : AppScreens("inicio_screen")
    object CreateReportEmercyScreen : AppScreens("createReportEmercy_screen")
    object CreateReportScreen : AppScreens("createReport_screen")
    object MyReportsScreen : AppScreens("myReports_screen")
    object AllReportsScreen : AppScreens("allReports_screen")
    object ProfileScreen : AppScreens("profile_screen")
    object CommentsScreen : AppScreens("comments_screen/{idReport}"){
        fun createRoute(idReport: String) = "comments_screen/$idReport"
    }
    object InicioAdminScreen : AppScreens("inicioAdmin_screen")
    object ReportsAdminScreen : AppScreens("reportsAdmin_screen")
    object ProfileAdminScreen : AppScreens("profileAdmin_screen")
    object NotificationScreen : AppScreens("notification_screen")

}