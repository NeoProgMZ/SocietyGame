package pl.neoprog.khexgrid.render

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import pl.neoprog.khexgrid.map.HexagonalMap
import pl.neoprog.khexgrid.map.HexagonalTile
import pl.neoprog.khexgrid.map.Terrain
import pl.neoprog.khexgrid.map.TerrainView

/**
 * @author Dan McCabe
 */
class TerrainLayer(
    private val map: HexagonalMap,
    private val hexagonalRenderer: HexagonalRenderer,
    private val terrainViews: Map<Terrain, TerrainView>
) : LayerAdapter() {
    override fun handleRender(batch: PolygonSpriteBatch, tiles: Collection<HexagonalTile>) {
        tiles
            .mapNotNull { tile ->
                terrainViews[map.getTerrain(tile.location)]?.texture?.let { tile.location to it }
            }
            .forEach { (coordinate, texture) ->
                hexagonalRenderer.renderTexture(batch, texture, coordinate)
            }
    }
}
