package pl.neoprog.lwjgl3

import kotlin.jvm.JvmStatic
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import pl.neoprog.SocietyGame
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

/** Launches the desktop (LWJGL3) application.  */
object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private fun createApplication(): Lwjgl3Application {
        return Lwjgl3Application(SocietyGame(), defaultConfiguration)
    }

    //// Limits FPS to the refresh rate of the currently active monitor.
    //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
    //// useful for testing performance, but can also be very stressful to some hardware.
    //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle("Society")
            configuration.useVsync(true)
            //// Limits FPS to the refresh rate of the currently active monitor.
            configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate)
            //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
            //// useful for testing performance, but can also be very stressful to some hardware.
            //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
            configuration.setWindowedMode(800, 600)
            configuration.setWindowIcon(
                "libgdx128.png",
                "libgdx64.png",
                "libgdx32.png",
                "libgdx16.png"
            )
            return configuration
        }
}