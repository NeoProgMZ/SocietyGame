package pl.neoprog.khexgrid.render

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import pl.neoprog.khexgrid.map.HexagonalTile

/**
 * @author Dan McCabe
 */
interface Layer {
    fun render(batch: PolygonSpriteBatch, tiles: Collection<HexagonalTile>)
}
