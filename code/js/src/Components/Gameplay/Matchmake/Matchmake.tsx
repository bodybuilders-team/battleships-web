import * as React from "react";
import {useEffect, useState} from "react";
import Typography from "@mui/material/Typography";
import LoadingSpinner from "../../Shared/LoadingSpinner";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import {useNavigate} from "react-router-dom";
import PageContent from "../../Shared/PageContent";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {useNavigationState} from "../../../Utils/navigation/NavigationState";
import {useInterval} from "../../Shared/useInterval";

const defaultGameConfig = require('../../../Assets/defaultGameConfig.json');
const POLLING_DELAY = 1000;

/**
 * Matchmake component.
 */
export default function Matchmake() {
    const navigate = useNavigate();
    const navigationState = useNavigationState();

    const battleshipsService = useBattleshipsService();

    const [error, setError] = useState<string | null>(null);
    const [matchmade, setMatchmade] = useState(false);
    const [isWaitingForOpponent, setWaitingForOpponent] = useState<boolean>(false);
    const [gameId, setGameId] = useState<number | null>(null);

    useEffect(() => {
        let cancelled = false;

        async function fetchMatchmake() {
            const [err, res] = await to(
                battleshipsService.gamesService.matchmake(defaultGameConfig)
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            const gameId = res.properties!.gameId;
            setGameId(gameId);

            navigationState.setLinks(battleshipsService.links);
            if (!res.properties!.wasCreated) {
                setMatchmade(true);
                navigate(`/game/${gameId}`);
                return;
            }

            setWaitingForOpponent(true);
        }

        fetchMatchmake();

        return () => {
            cancelled = true
        };
    }, []);

    useInterval(async () => {
        if (!isWaitingForOpponent)
            return true;

        const [err, res] = await to(
            battleshipsService.gamesService.getGameState()
        );

        if (err) {
            handleError(err, setError);
            return true;
        }

        if (res.properties!.phase !== "WAITING_FOR_PLAYERS") {
            setMatchmade(true);
            setWaitingForOpponent(false);
            navigationState.setLinks(battleshipsService.links);
            navigate(`/game/${gameId}`);
            return true;
        }

        return false;
    }, POLLING_DELAY, [isWaitingForOpponent]);

    return (
        <PageContent title={"Matchmake"} error={error}>
            {
                matchmade
                    ? <Typography variant="h6">Matchmade!</Typography>
                    : <LoadingSpinner text={"Matchmaking..."}/>
            }
        </PageContent>
    );
}
