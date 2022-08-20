package pl.neoprog.khexgrid.render

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import pl.neoprog.khexgrid.map.HexagonalMap
import pl.neoprog.khexgrid.map.HexagonalTile
import pl.neoprog.khexgrid.math.CubeCoordinate
import pl.neoprog.khexgrid.math.OffsetCoordinateType
import ktx.math.ImmutableVector2

/**
 * @author Dan McCabe
 */
class TilePositionLayer(private val map: HexagonalMap, private val font: BitmapFont) : LayerAdapter() {

    private val textPositions = map.tiles.keys.associateWith { coordinate ->
        val layout = calculateTextLayout(coordinate)
        val pixel = map.toPixel(coordinate)
        ImmutableVector2(pixel.x - layout.width / 2, pixel.y + layout.height / 2)
    }

    override fun handleRender(batch: PolygonSpriteBatch, tiles: Collection<HexagonalTile>) {
        tiles.forEach {
            val position = textPositions[it.location] ?: ImmutableVector2.ZERO
            font.draw(batch, coordinateText(it.location), position.x, position.y)
        }
    }

    private fun coordinateText(cubeCoordinate: CubeCoordinate): String {
        val offsetCoordinate = cubeCoordinate.toOffsetCoordinate(OffsetCoordinateType(map.orientation))
        return "${offsetCoordinate.x},${offsetCoordinate.y}"
    }

    private fun calculateTextLayout(coordinate: CubeCoordinate): GlyphLayout =
        GlyphLayout(font, coordinateText(coordinate))
}
