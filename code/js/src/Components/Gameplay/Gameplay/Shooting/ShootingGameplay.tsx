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

/**
 * Properties for the ShootingGameplay component.
 *
 * @property game the game which is being played
 * @property onFinished the callback to be called when the game is finished
 */
interface ShootingGameplayProps {
    game: Game;
    onFinished: () => void;
}

/**
 * ShootingGameplay component.
 */
export default function ShootingGameplay({game, onFinished}: ShootingGameplayProps) {
    const battleshipsService = useBattleshipsService();
    const [error, setError] = useState<string | null>(null);
    const [myFleet, setMyFleet] = useState<Ship[]>();

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
            handleError(err, setError);
            return;
        }

        setMyFleet(parseFleet(res?.properties?.ships!));
    }

    useEffect(() => {
        fetchMyFleet();
    }, []);

    if (myFleet)
        return (
            <Shooting game={game} myFleet={myFleet} onFinished={onFinished} onTimeUp={() => {
                setTimeout(onFinished, 2000) // wait for the server to update the game
            }}/>
        );
    else
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Loading fleet..."}/>
            </PageContent>
        );
}
