import * as React from "react";
import {useEffect} from "react";
import Typography from "@mui/material/Typography";
import LoadingSpinner from "../../Utils/LoadingSpinner";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import {useNavigate} from "react-router-dom";
import PageContent from "../../Utils/PageContent";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {useNavigationState} from "../../../Utils/navigation/NavigationStateProvider";

const defaultGameConfig = require('../../../Assets/defaultGameConfig.json');

/**
 * Matchmake component.
 */
function Matchmake() {

    const navigate = useNavigate();
    const [matchmade, setMatchmade] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);
    const [battleshipsService, setBattleshipService] = useBattleshipsService()
    const navigationState = useNavigationState();

    useEffect(() => {
        let cancelled = false;
        const fetchMatchmake = async () => {
            const [err, res] = await to(
                battleshipsService.gamesService.matchmake(defaultGameConfig)
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            if (res?.properties === undefined)
                throw new Error("Properties are undefined");

            if (!res.properties.wasCreated) {
                setMatchmade(true);
                navigationState.setLinks(battleshipsService.links)
                navigate("/gameplay");
                return;
            }

            while (!matchmade && !cancelled) {
                const [err, res] = await to(
                    battleshipsService.gamesService.getGameState()
                );

                if (err) {
                    handleError(err, setError);
                    return;
                }

                if (res?.properties === undefined)
                    throw new Error("Entities are undefined");

                if (res.properties.phase === "WAITING_FOR_PLAYERS")
                    await new Promise(resolve => setTimeout(resolve, 1000));
                else {
                    setMatchmade(true);
                    navigationState.setLinks(battleshipsService.links)
                    navigate("/gameplay");
                }
            }
        }

        fetchMatchmake()

        return () => {
            cancelled = true
        }
    }, []);

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

export default Matchmake;
