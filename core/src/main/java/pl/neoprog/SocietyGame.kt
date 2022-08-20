package pl.neoprog

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ktx.app.KtxGame
import ktx.app.KtxScreen
import pl.neoprog.screen.Loading
import pl.neoprog.screen.MainMenu

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class SocietyGame : KtxGame<KtxScreen>() {
    // use LibGDX's default Arial font
    val font by lazy { BitmapFont() }
    val assets = AssetManager()

    override fun create() {
        addScreen(Loading(this))
        setScreen<Loading>()
        super.create()
    }

    override fun dispose() {
        font.dispose()
        assets.dispose()
        super.dispose()
    }
}