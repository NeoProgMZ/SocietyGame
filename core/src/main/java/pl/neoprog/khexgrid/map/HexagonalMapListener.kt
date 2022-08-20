package pl.neoprog.khexgrid.map

import pl.neoprog.khexgrid.math.CubeCoordinate

/**
 * @author Dan McCabe
 */
interface HexagonalMapListener {
    fun tilesChanged(map: HexagonalMap, locations: List<CubeCoordinate>)
}