rootProject.apply {
    name = "Common"
    buildFileName = "build.gradle.kts"
}

include(":app")
include(":common")
include(":common_mvvm")
include(":common_data_ui")
include(":common_data_network")
include(":common_utility_permissions")
include(":common_utility_network_callback")
include(":common_utility_resources")
include(":common_utility_views")
include(":common_utility")
include(":common_persistence")
include(":common_repository_retrofit")
include(":common_views_recycler_view")
include(":common_views_dialogs")
include(":common_views_mvvm")
