package pl.neoprog.khexgrid.map

/**
 * @author Dan McCabe
 */
interface TerrainProvider {
    operator fun get(id: String): Terrain
}