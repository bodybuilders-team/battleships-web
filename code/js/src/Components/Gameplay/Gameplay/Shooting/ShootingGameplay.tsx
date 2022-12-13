import Shooting from "./Shooting";
import * as React from "react";
import {useEffect, useState} from "react";
import to from "../../../../Utils/await-to";
import {Ship} from "../../../../Domain/games/ship/Ship";
import {useBattleshipsService} from "../../../../Services/NavigationBattleshipsService";
import {handleError} from "../../../../Services/utils/fetchSiren";
import {UndeployedShipModel} from "../../../../Services/services/games/models/players/ship/UndeployedShipModel";
import {Orientation} from "../../../../Domain/games/ship/Orientation";
import {Coordinate} from "../../../../Domain/games/Coordinate";
import {ShipType} from "../../../../Domain/games/ship/ShipType";
import {throwError} from "../../../../Services/utils/errorUtils";
import PageContent from "../../../Shared/PageContent";
import LoadingSpinner from "../../../Shared/LoadingSpinner";
import {Game} from "../../../../Domain/games/game/Game";
import FetchedEndGamePopup from "../../Shared/FetchedEndGamePopup";
import {Problem} from "../../../../Services/media/Problem";
import {ProblemTypes} from "../../../../Utils/types/problemTypes";

/**
 * Properties for the ShootingGameplay component.
 *
 * @property game the game which is being played
 * @property onFinished the callback to be called when the game is finished
 */
interface ShootingGameplayProps {
    game: Game;
}

/**
 * ShootingGameplay component.
 */
export default function ShootingGameplay({game}: ShootingGameplayProps) {
    const battleshipsService = useBattleshipsService();
    const [error, setError] = useState<string | null>(null);
    const [showEndGamePopup, setShowEndGamePopup] = useState(false);
    const [myFleet, setMyFleet] = useState<Ship[]>();

    useEffect(() => {
        fetchMyFleet();
    }, []);

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
            ) ?? throwError("Ship type not found");

            return new Ship(
                shipType,
                Coordinate.fromCoordinateModel(ship.coordinate),
                Orientation.parse(ship.orientation),
            );
        });
    }

    /**
     * Fetches the fleet.
     */
    async function fetchMyFleet() {
        const [err, res] = await to(battleshipsService.playersService.getMyFleet());

        if (err) {
            if (err instanceof Problem && err.type === ProblemTypes.INVALID_PHASE) {
                setShowEndGamePopup(true);
                return;
            }
            handleError(err, setError);
            return;
        }

        setMyFleet(parseFleet(res?.properties?.ships!));
    }


    if (myFleet)
        return (
            <>
                <Shooting game={game} myFleet={myFleet} onFinished={() => {
                    setShowEndGamePopup(true);
                }} onTimeUp={() => {
                    setShowEndGamePopup(true);
                }}/>
                <FetchedEndGamePopup open={showEndGamePopup} onError={(err) => {
                    handleError(err, setError);
                }}/>
            </>
        );
    else
        return (
            <>
                <PageContent error={error}>
                    <LoadingSpinner text={"Loading game state..."}/>
                </PageContent>
                <FetchedEndGamePopup open={showEndGamePopup} onError={(err) => {
                    handleError(err, setError);
                }}/>
            </>
        );
}
