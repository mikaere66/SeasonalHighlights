
import androidx.compose.ui.window.ComposeUIViewController
import com.michaelrmossman.seasonal.di.KoinInitializer
import com.michaelrmossman.seasonal.screens.MainScreen

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) {
    MainScreen()
}