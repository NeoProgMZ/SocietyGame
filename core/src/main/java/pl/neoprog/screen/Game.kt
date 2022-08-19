package pl.neoprog.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.darkgravity.khexgrid.map.*
import com.darkgravity.khexgrid.math.OffsetCoordinate
import com.darkgravity.khexgrid.math.OffsetCoordinateType
import com.darkgravity.khexgrid.render.HexagonalRenderer
import com.darkgravity.khexgrid.render.LayeredRenderer
import com.darkgravity.khexgrid.render.TerrainLayer
import com.darkgravity.khexgrid.render.TileOutlineLayer
import ktx.actors.stage
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.math.ImmutableVector2
import pl.neoprog.terrain.Grass


class Game(val game: KtxGame<KtxScreen>) : KtxScreen {
    private val stage: Stage by lazy { stage(viewport = ScreenViewport()) }
    private val columns: Int = 10;
    private val rows: Int = 10;

    init {
        var mapLayout = HexagonalLayout(
            HexagonalOrientation.FlatTop,
            ImmutableVector2(0f, 0f),
            ImmutableVector2(32f, 32f)
        )

        val tiles = (0 until rows).flatMap { row ->
            (0 until columns).map { column ->
                val coordinate = OffsetCoordinate(column, row, OffsetCoordinateType(mapLayout.orientation)).toCubeCoordinate()
                coordinate to HexagonalTile(coordinate, getTerrain(column, row))
            }
        }.toMap()

        val map = HexagonalMap(mapLayout, tiles)
        val hexRenderer = HexagonalRenderer(map, ImmutableVector2(32f, 32f))
        val renderer = LayeredRenderer(map, listOf(
            TerrainLayer(map, hexRenderer, terrainViews),
            TileOutlineLayer(hexRenderer, shapeRenderer)
        ), stage.camera as OrthographicCamera)
        renderer.render(stage.batch as PolygonSpriteBatch)
    }

    override fun render(delta: Float) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    private fun getTerrain(column: Int, row: Int): Terrain {
        return Grass()
    }
}