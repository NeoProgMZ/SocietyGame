package pl.neoprog.khexgrid.map

/**
 * @author Dan McCabe
 */
interface Terrain {
    val id: String
    val isMoveObstacle: Boolean
    val isViewObstacle: Boolean
    val movementCost: Int
}