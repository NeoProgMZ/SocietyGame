package pl.neoprog.terrain

import pl.neoprog.khexgrid.map.Terrain

class Grass: Terrain {
    override val id: String = "grass"
    override val isMoveObstacle: Boolean = true
    override val isViewObstacle: Boolean = true
    override val movementCost: Int = 1
}