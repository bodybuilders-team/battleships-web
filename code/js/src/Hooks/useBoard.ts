import {Cell} from "../Domain/games/Cell";
import * as React from "react";
import {useState} from "react";
import {ConfigurableBoard} from "../Domain/games/board/ConfigurableBoard";
import {Ship} from "../Domain/games/ship/Ship";


export function useConfigurableBoard(size: number, grid: Cell[]) {
    const [board, setBoard] = useState(() => new ConfigurableBoard(size, grid));

    const placeShip = React.useCallback((ship: Ship) => {
        setBoard(board => board.placeShip(ship));
    }, [board])

    const removeShip = React.useCallback((ship: Ship) => {
        setBoard(board => board.removeShip(ship));
    }, [board])

    return {
        board,
        setBoard,
        placeShip,
        removeShip
    }
}