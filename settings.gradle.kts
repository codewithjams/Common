rootProject.apply {
	name = "Common"
	buildFileName = "build.gradle.kts"
}

include(":app")
include(":common_mvvm")
include(":common_mvi")
include(":common_utility_network_callback")
include(":common_utility_resources")
include(":common_utility_views")
include(":common_utility")
include(":common_persistence")
include(":common_rest_retrofit")
include(":common_views_recycler_view")
include(":common_views_dialogs")
include(":common_views_mvvm")
include(":common_views_core")
include(":common_viewModel_dagger")
include(":common_views_mvi")
include(":common_data_transformer")
include(":common_persistence_data_store")
include(":common_persistence_shared_preferences")
include(":common_data_transformer_moshi")
include(":common_repository")
