package pl.neoprog.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import pl.neoprog.khexgrid.math.OffsetCoordinate
import pl.neoprog.khexgrid.math.OffsetCoordinateType
import pl.neoprog.khexgrid.render.HexagonalRenderer
import pl.neoprog.khexgrid.render.LayeredRenderer
import pl.neoprog.khexgrid.render.TerrainLayer
import pl.neoprog.khexgrid.render.TileOutlineLayer
import ktx.actors.stage
import ktx.app.KtxScreen
import ktx.math.ImmutableVector2
import pl.neoprog.SocietyGame
import pl.neoprog.assets.TextureAtlasAssets
import pl.neoprog.assets.get
import pl.neoprog.khexgrid.map.*
import pl.neoprog.terrain.Grass
import ktx.log.*


class Game(private val game: SocietyGame) : KtxScreen {
    private val stage: Stage by lazy { stage(viewport = ScreenViewport(), batch = PolygonSpriteBatch()) }
    private val columns: Int = 10
    private val rows: Int = 10
    private val mapTileSize: ImmutableVector2 = ImmutableVector2(128f, 128f) 
    private val terrainLayer: TerrainLayer
    private val tileOutlinerLayer: TileOutlineLayer
    private val renderer: LayeredRenderer

    init {
        val mapLayout = HexagonalLayout(
            HexagonalOrientation.FlatTop,
            mapTileSize, // lower left corner is the start position
            mapTileSize
        )
        // FIXME basic terrains and their views should be a closed list. After deciding what terrain to use select it from list.
        val debugTerrain = getTerrain(0, 0)
        val terrainView = TerrainView(
            debugTerrain,
            game.assets[TextureAtlasAssets.Game].findRegion("grass"),
            Color.GREEN
        )
        val terrainViews: Map<Terrain, TerrainView> = mapOf(debugTerrain to terrainView)

        val tiles = (0 until rows).flatMap { row ->
            (0 until columns).map { column ->
                // TODO place for terrain selection algorithms
                val coordinate = OffsetCoordinate(column, row, OffsetCoordinateType(mapLayout.orientation)).toCubeCoordinate()
                coordinate to HexagonalTile(coordinate, debugTerrain)
            }
        }.toMap()

        val map = HexagonalMap(mapLayout, tiles)
        val hexRenderer = HexagonalRenderer(map, mapTileSize)
        terrainLayer = TerrainLayer(map, hexRenderer, terrainViews)
        tileOutlinerLayer = TileOutlineLayer(hexRenderer, ShapeRenderer())
        renderer = LayeredRenderer(map, listOf(
            terrainLayer,
            tileOutlinerLayer
        ), stage.camera as OrthographicCamera)
    }

    override fun render(delta: Float) {
        renderer.render(stage.batch as PolygonSpriteBatch)
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        // IDEA this runs on start once - can be entry point.
        stage.viewport.update(width, height, true)
    }

    private fun getTerrain(column: Int, row: Int): Terrain {
        return Grass()
    }
}