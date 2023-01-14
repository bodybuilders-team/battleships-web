import Shooting from "./Shooting"
import * as React from "react"
import {useEffect, useState} from "react"
import {Ship} from "../../../../Domain/games/ship/Ship"
import {useBattleshipsService} from "../../../../Services/NavigationBattleshipsService"
import {handleError} from "../../../../Services/utils/fetchSiren"
import {UndeployedShipModel} from "../../../../Services/services/games/models/players/ship/UndeployedShipModel"
import {Orientation} from "../../../../Domain/games/ship/Orientation"
import {Coordinate} from "../../../../Domain/games/Coordinate"
import {ShipType} from "../../../../Domain/games/ship/ShipType"
import {throwError} from "../../../../Services/utils/errorUtils"
import PageContent from "../../../Shared/PageContent"
import LoadingSpinner from "../../../Shared/LoadingSpinner"
import {Game} from "../../../../Domain/games/game/Game"
import FetchedEndGamePopup from "../../Shared/EndGame/FetchedEndGamePopup"
import {Problem} from "../../../../Services/media/Problem"
import {ProblemTypes} from "../../../../Utils/types/problemTypes"
import {abortableTo} from "../../../../Utils/componentManagement/abortableUtils"
import {useMountedSignal} from "../../../../Utils/componentManagement/useMounted"
import {MyBoard} from "../../../../Domain/games/board/MyBoard"
import {OpponentBoard} from "../../../../Domain/games/board/OpponentBoard"
import {FiredShot} from "../../../../Domain/games/shot/FiredShot"
import {useNavigate} from "react-router-dom"

/**
 * Properties for the ShootingGameplay component.
 *
 * @property game the game which is being played
 */
interface ShootingGameplayProps {
    game: Game
}

/**
 * ShootingGameplay component.
 */
export default function ShootingGameplay({game}: ShootingGameplayProps) {
    const battleshipsService = useBattleshipsService()
    const [error, setError] = useState<string | null>(null)
    const [showEndGamePopup, setShowEndGamePopup] = useState(false)

    const [myFleet, setMyFleet] = useState<Ship[]>()
    const [myShots, setMyShots] = useState<FiredShot[]>()
    const [opponentFleet, setOpponentFleet] = useState<Ship[]>()
    const [opponentShots, setOpponentShots] = useState<FiredShot[]>()
    const navigate = useNavigate()
    const mountedSignal = useMountedSignal()

    useEffect(() => {
        fetchMyFleet()
        fetchMyShots()
        fetchOpponentFleet()
        fetchOpponentShots()
    }, [])

    /**
     * Fetches the opponent's fleet.
     */
    async function fetchOpponentShots() {
        const [err, res] = await abortableTo(battleshipsService.playersService.getOpponentShots(mountedSignal))

        if (err) {
            if (err instanceof Problem && err.type === ProblemTypes.INVALID_PHASE) {
                setShowEndGamePopup(true)
                return
            }
            handleError(err, setError, navigate)
            return
        }

        setOpponentShots(parseShots(res?.properties?.shots!))
    }


    /**
     * Fetches the shots.
     */
    async function fetchMyShots() {
        const [err, res] = await abortableTo(battleshipsService.playersService.getMyShots(mountedSignal))

        if (err) {
            if (err instanceof Problem && err.type === ProblemTypes.INVALID_PHASE) {
                setShowEndGamePopup(true)
                return
            }
            handleError(err, setError, navigate)
            return
        }

        setMyShots(parseShots(res?.properties?.shots!))
    }

    /**
     * Fetches the fleet.
     */
    async function fetchMyFleet() {
        const [err, res] = await abortableTo(battleshipsService.playersService.getMyFleet(mountedSignal))

        if (err) {
            if (err instanceof Problem && err.type === ProblemTypes.INVALID_PHASE) {
                setShowEndGamePopup(true)
                return
            }
            handleError(err, setError, navigate)
            return
        }

        setMyFleet(parseFleet(res?.properties?.ships!))
    }

    /**
     * Fetches the opponent fleet.
     */
    async function fetchOpponentFleet() {
        const [err, res] = await abortableTo(battleshipsService.playersService.getOpponentFleet(mountedSignal))

        if (err) {
            if (err instanceof Problem && err.type === ProblemTypes.INVALID_PHASE) {
                setShowEndGamePopup(true)
                return
            }
            handleError(err, setError, navigate)
            return
        }

        setOpponentFleet(parseFleet(res?.properties?.ships!))
    }

    /**
     * Parses the fired shots.
     * @param firedShotModels the fired shot models
     *
     * @returns the fired shots
     */
    function parseShots(firedShotModels: FiredShotModel[]): FiredShot[] {
        return firedShotModels.map(firedShotModel => new FiredShot(firedShotModel, game.config.shipTypes))
    }

    /**
     * Parses the fleet from the deployed ship models.
     *
     * @param deployedShipModels the deployed ship models
     * @return the fleet
     */
    function parseFleet(deployedShipModels: DeployedShipModel[]): Ship[] {
        return deployedShipModels.map((ship: UndeployedShipModel) => {
            const shipType = Array.from(game.config.shipTypes.keys()).find(
                (shipType: ShipType) => shipType.shipName === ship.type
            ) ?? throwError("Ship type not found")

            return new Ship(
                shipType,
                Coordinate.fromCoordinateModel(ship.coordinate),
                Orientation.parse(ship.orientation),
            )
        })
    }

    if (myFleet && myShots && opponentFleet && opponentShots) {
        return (
            <>
                <Shooting
                    game={game}
                    initialMyBoard={MyBoard.fromFleet(game.config.gridSize, myFleet)
                        .shoot(opponentShots.map(shot => shot.coordinate))
                    }
                    initialOpponentBoard={OpponentBoard.fromFleet(game.config.gridSize, opponentFleet)
                        .updateWith(myShots)}
                    onFinished={() => {
                        setShowEndGamePopup(true)
                    }}
                    onTimeUp={() => {
                        setShowEndGamePopup(true)
                    }}/>
                <FetchedEndGamePopup open={showEndGamePopup} onError={(err) => {
                    handleError(err, setError, navigate)
                }}/>
            </>
        )
    } else {
        return (
            <>
                <PageContent error={error}>
                    <LoadingSpinner text={"Loading game state..."}/>
                </PageContent>
                <FetchedEndGamePopup open={showEndGamePopup} onError={(err) => {
                    handleError(err, setError, navigate)
                }}/>
            </>
        )
    }
}
