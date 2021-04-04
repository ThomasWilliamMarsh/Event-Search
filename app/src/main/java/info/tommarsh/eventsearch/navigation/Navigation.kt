package info.tommarsh.eventsearch.navigation

private const val DEEPLINK_PREFIX = "app://eventsearch.app/"

sealed class Screen(val route: String, val deeplink: String = DEEPLINK_PREFIX.plus(route)) {
    object Search : Screen("search")
    object Attraction : Screen("attraction/{id}") { fun route(id: String) = "attraction/$id"}
    object Category : Screen("category/{id}/{name}") { fun route(id: String, name:String) = "category/$id/$name"}
    object Settings : Screen("settings")
}