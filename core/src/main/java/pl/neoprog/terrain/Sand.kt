package pl.neoprog.terrain

import pl.neoprog.khexgrid.map.Terrain

class Sand: Terrain {
    override val id: String = "sand"
    override val isMoveObstacle: Boolean = true
    override val isViewObstacle: Boolean = true
    override val movementCost: Int = 1
}