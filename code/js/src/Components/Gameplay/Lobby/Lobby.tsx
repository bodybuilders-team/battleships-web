import * as React from "react";
import {useEffect, useState} from "react";
import {EmbeddedSubEntity} from "../../../Services/media/siren/SubEntity";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import PageContent from "../../Shared/PageContent";
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import LoadingSpinner from "../../Shared/LoadingSpinner";
import GameCard from "./GameCard";
import {useNavigate} from "react-router-dom";
import {useNavigationState} from "../../../Utils/navigation/NavigationState";
import {Rels} from "../../../Utils/navigation/Rels";
import {useSession} from "../../../Utils/Session";
import Typography from "@mui/material/Typography";

/**
 * Lobby component.
 */
export default function Lobby() {
    const navigate = useNavigate();
    const navigationState = useNavigationState();

    const battleshipsService = useBattleshipsService();
    const session = useSession();

    const [error, setError] = useState<string | null>(null);
    const [games, setGames] = useState<EmbeddedSubEntity<GetGameOutputModel>[] | null>(null);
    const [gamesLoaded, setGamesLoaded] = useState(false);

    useEffect(() => {
        if (gamesLoaded)
            return;

        const fetchGames = async () => {
            const [err, res] = await to(battleshipsService.gamesService.getGames());

            if (err) {
                handleError(err, setError);
                return;
            }

            const filteredGames = res
                .getEmbeddedSubEntities<GetGameOutputModel>()
                .filter(game =>
                    game.properties?.state.phase === "WAITING_FOR_PLAYERS" &&
                    game.properties?.creator !== session?.username
                );

            setGames(filteredGames);

            setGamesLoaded(true);
        }

        fetchGames();
    });

    function handleJoinGame(joinGameLink: string) {
        async function joinGame() {
            const [err, res] = await to(battleshipsService.gamesService.joinGame(joinGameLink));

            if (err) {
                handleError(err, setError);
                return;
            }

            navigationState.setLinks(battleshipsService.links)
            navigate(`/game/${res.properties!.gameId}`);
        }

        joinGame();
    }

    return (
        <PageContent title="Lobby" error={error}>
            {
                gamesLoaded
                    ? games?.length === 0
                        ? <Typography variant="h5" component="div" sx={{flexGrow: 1}}>
                            No games available
                        </Typography>
                        : games?.map(game =>
                            <GameCard key={game.properties?.id} game={game.properties!} onJoinGameRequest={() => {
                                handleJoinGame(game.getAction(Rels.JOIN_GAME));
                            }}/>
                        )
                    : <LoadingSpinner text={"Loading games..."}/>
            }
        </PageContent>
    );
}
