import * as React from "react";
import {useEffect} from "react";
import {EmbeddedSubEntity} from "../../../Services/media/siren/SubEntity";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import PageContent from "../../Utils/PageContent";
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import LoadingSpinner from "../../Utils/LoadingSpinner";
import GameCard from "./GameCard";
import {useNavigate} from "react-router-dom";
import {useNavigationState} from "../../../Utils/navigation/NavigationStateProvider";
import {Rels} from "../../../Utils/navigation/Rels";

/**
 * Lobby component.
 */
function Lobby() {

    const navigate = useNavigate();
    const navigationState = useNavigationState();
    const [games, setGames] = React.useState<EmbeddedSubEntity<GetGameOutputModel>[] | null>(null);
    const [gamesLoaded, setGamesLoaded] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);
    const [battleshipsService, setBattleshipsService] = useBattleshipsService()

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
                .filter(game => game.properties?.state.phase === "WAITING_FOR_PLAYERS");

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
            navigate(`/gameplay`);
        }

        joinGame();
    }

    return (
        <PageContent title="Lobby" error={error}>
            {
                gamesLoaded
                    ? games?.map(game =>
                        <GameCard key={game.properties?.id} game={game.properties!} onJoinGameRequest={() => {
                            handleJoinGame(game.getAction(Rels.JOIN_GAME));
                        }}/>
                    )
                    : <LoadingSpinner text={"Loading games..."}/>
            }
        </PageContent>
    );
}

export default Lobby;
