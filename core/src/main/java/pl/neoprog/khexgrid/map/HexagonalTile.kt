package pl.neoprog.khexgrid.map

import pl.neoprog.khexgrid.math.CubeCoordinate

/**
 * @author Dan McCabe
 */
data class HexagonalTile(val location: CubeCoordinate, val terrain: Terrain) {
    val isMoveObstacle: Boolean get() = terrain.isMoveObstacle
    val isViewObstacle: Boolean get() = terrain.isViewObstacle
    val movementCost: Int get() = terrain.movementCost
}
