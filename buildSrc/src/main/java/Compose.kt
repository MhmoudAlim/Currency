/**
 * Created by Mahmoud Alim on 22/03/2022.
 */
object Compose {
    const val composeVersion = "1.1.1"
    const val material = "androidx.compose.material:material:$composeVersion"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
    const val compiler = "androidx.compose.compiler:compiler:$composeVersion"

    private const val activityComposeVersion = "1.4.0"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
    const val composeKtx = "androidx.activity:activity-ktx:$activityComposeVersion"

    private const val lifecycleVersion = "2.4.1"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"

}
