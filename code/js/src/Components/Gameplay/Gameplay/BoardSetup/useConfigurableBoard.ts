import {Cell} from "../../../../Domain/games/Cell"
import {useCallback, useState} from "react"
import {ConfigurableBoard} from "../../../../Domain/games/board/ConfigurableBoard"
import {Ship} from "../../../../Domain/games/ship/Ship"

/**
 * Hook to manage the configurable board state.
 *
 * @param size the size of the board
 * @param grid the grid of cells
 */
export default function useConfigurableBoard(size: number, grid: Cell[]) {
    const [board, setBoard] = useState(() => new ConfigurableBoard(size, grid))

    const placeShip = useCallback((ship: Ship) => {
        setBoard(board => board.placeShip(ship))
    }, [board])

    const removeShip = useCallback((ship: Ship) => {
        setBoard(board => board.removeShip(ship))
    }, [board])

    return {
        board,
        setBoard,
        placeShip,
        removeShip
    }
}
