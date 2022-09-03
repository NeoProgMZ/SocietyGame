package pl.neoprog.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import pl.neoprog.khexgrid.math.OffsetCoordinate
import pl.neoprog.khexgrid.math.OffsetCoordinateType
import pl.neoprog.khexgrid.render.HexagonalRenderer
import pl.neoprog.khexgrid.render.LayeredRenderer
import pl.neoprog.khexgrid.render.TerrainLayer
import pl.neoprog.khexgrid.render.TileOutlineLayer
import ktx.actors.stage
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import ktx.log.debug
import ktx.math.ImmutableVector2
import pl.neoprog.SocietyGame
import pl.neoprog.assets.TextureAtlasAssets
import pl.neoprog.assets.get
import pl.neoprog.khexgrid.map.*
import pl.neoprog.terrain.Grass
import pl.neoprog.terrain.Sand


class Game(private val game: SocietyGame) : KtxScreen, KtxInputAdapter {
    private val columns: Int = 100
    private val rows: Int = 100
    private val tileSideSize: Float = 128f
    private val stage: Stage by lazy {
        stage(
            viewport = ScalingViewport(
                Scaling.none,
                tileSideSize * columns,
                tileSideSize * rows,
                OrthographicCamera().apply { setToOrtho(false) }
            ),
            batch = PolygonSpriteBatch()
        )
    }
    private val mapTileSize: ImmutableVector2 = ImmutableVector2(tileSideSize, tileSideSize)
    private val terrainLayer: TerrainLayer
    private val tileOutlineLayer: TileOutlineLayer
    private val renderer: LayeredRenderer

    init {
        Gdx.input.inputProcessor = this

        val mapLayout = HexagonalLayout(
            HexagonalOrientation.FlatTop,
            ImmutableVector2(0f, 0f), // lower left corner is the start position
            mapTileSize
        )
        val terrainViews: MutableMap<Terrain, TerrainView> = mutableMapOf()

        val tiles = (0 until rows).flatMap { row ->
            (0 until columns).map { column ->
                val coordinate = OffsetCoordinate(
                    column,
                    row,
                    OffsetCoordinateType(mapLayout.orientation)
                ).toCubeCoordinate()
                val terrainView = getTerrain(column, row)
                terrainViews[terrainView.terrain] = terrainView
                coordinate to HexagonalTile(coordinate, terrainView.terrain)
            }
        }.toMap()

        val map = HexagonalMap(mapLayout, tiles)
        val hexRenderer = HexagonalRenderer(map, mapTileSize)
        terrainLayer = TerrainLayer(map, hexRenderer, terrainViews.toMap())
        tileOutlineLayer = TileOutlineLayer(hexRenderer, ShapeRenderer())
        // TODO needs vegetation layer, resources layer, improvements layer, city layer, units layer.
        renderer = LayeredRenderer(
            map, listOf(
                terrainLayer,
                tileOutlineLayer
            ), stage.camera as OrthographicCamera
        )
    }

    override fun render(delta: Float) {
        renderer.render(stage.batch as PolygonSpriteBatch)
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        // IDEA this runs on start once - can be entry point.
        stage.viewport.update(width, height, false)
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        val stageCamera = stage.camera as OrthographicCamera
        val newZoomVal = amountX + stageCamera.zoom
        debug { -> "ZOOM: $amountX + ${stageCamera.zoom} = $newZoomVal" }
        if (0 <= newZoomVal && newZoomVal < 10) {
            debug { -> "SCROLLED" }
            // FIXME this does nothing!
            // IDEA maybe because zoom is not used anywhere here?!
            (stage.viewport.camera as OrthographicCamera).zoom = newZoomVal
            stage.viewport.camera.update()
        }

        return true
    }

    private fun getTerrain(column: Int, row: Int): TerrainView {
        var terrain: Terrain = Grass()
        // TODO place for terrain selection algorithms
        if ((row % 2 != 0 && column % 2 != 0)
            || (row % 2 == 0 && column % 2 == 0)
        ) {
            terrain = Sand()
        }

        return TerrainView(
            terrain,
            game.assets[TextureAtlasAssets.Game].findRegion(terrain.id),
            Color.GREEN
        )
    }
}