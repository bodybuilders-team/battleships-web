import {Game} from "../../../../Domain/games/game/Game"
import {useSession} from "../../../../Utils/Session"
import EndGamePopup, {EndGameCause, WinningPlayer} from "./EndGamePopup"
import * as React from "react"

/**
 * Properties for the GameFinished component.
 */
interface GameFinishedProps {
    game: Game
}

/**
 * GameFinished component.
 */
export default function GameFinished({game}: GameFinishedProps) {
    const session = useSession()

    const username = session!.username

    const player = game.getPlayer(username)!
    const opponent = game.getOpponent(username)!

    return <EndGamePopup
        open={game?.state.phase === "FINISHED"}
        winningPlayer={
            game.state.winner == null ? WinningPlayer.NONE :
                game.state.winner === session?.username!
                    ? WinningPlayer.YOU : WinningPlayer.OPPONENT
        }
        cause={
            (() => {
                switch (game.state.endCause) {
                    case "DESTRUCTION":
                        return EndGameCause.DESTRUCTION
                    case "TIMEOUT":
                        return EndGameCause.TIMEOUT
                    default:
                        return EndGameCause.RESIGNATION
                }
            })()
        }
        playerInfo={{
            name: player.username,
            points: player.points
        }}
        opponentInfo={{
            name: opponent.username,
            points: opponent.points
        }}
    />
}
