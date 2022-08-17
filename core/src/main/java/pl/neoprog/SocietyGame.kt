package pl.neoprog

import ktx.app.KtxGame
import ktx.app.KtxScreen
import pl.neoprog.screen.MainMenu

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class SocietyGame : KtxGame<KtxScreen>() {
    override fun create() {
        addScreen(MainMenu(this))
        setScreen<MainMenu>()
        super.create()
    }
}