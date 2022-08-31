package pl.neoprog.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.stage
import ktx.app.KtxScreen
import ktx.graphics.use
import pl.neoprog.SocietyGame
import pl.neoprog.assets.TextureAtlasAssets
import pl.neoprog.assets.load

class Loading(private val game: SocietyGame) : KtxScreen {
    private val stage: Stage by lazy { stage(viewport = ScreenViewport()) }

    override fun show() {
        TextureAtlasAssets.values().forEach { game.assets.load(it) }
    }

    override fun render(delta: Float) {
        // continue loading our assets
        game.assets.update()

        stage.batch.use {
            if (game.assets.isFinished) {
// TODO show MainMenu screen.
                game.font.draw(it, "Tap anywhere to begin!", 100f, 100f)
            } else {
// TODO show spinner image.               
                game.font.draw(it, "Loading assets...", 100f, 100f)
            }
        }

        if (Gdx.input.isTouched && game.assets.isFinished) {
            game.addScreen(Game(game))
            game.setScreen<Game>()
            game.removeScreen<Loading>()
            dispose()
        }
    }
}