import * as React from "react";
import {useEffect} from "react";
import {EmbeddedSubEntity} from "../../../Services/utils/siren/SubEntity";
import to from "../../../Utils/await-to";
import * as gamesService from '../../../Services/games/GamesService';
import {handleError} from "../../../Services/utils/fetchSiren";
import PageContent from "../../Utils/PageContent";
import {GetGameOutputModel} from "../../../Services/games/models/games/getGame/GetGameOutput";

/**
 * Lobby component.
 */
function Lobby() {

    const [lobby, setLobby] = React.useState<GetGameOutputModel[]>([]);
    const [error, setError] = React.useState<string | null>(null);

    useEffect(() => {
        if (!lobby || lobby.length === 0) {
            const fetchGames = async () => {
                const [err, res] = await to(gamesService.getGames("http://localhost:8080/users"));

                if (err) {
                    handleError(err, setError);
                    return;
                }

                if (res?.entities === undefined)
                    throw new Error("Entities are undefined");

                const games = res.entities.map(entity =>
                    (entity as EmbeddedSubEntity<GetGameOutputModel>).properties as GetGameOutputModel
                );
                setLobby(games);
            }

            fetchGames();
        }
    });

    return (
        <PageContent title="Lobby" error={error}>
            {/*TODO: To be implemented*/}
        </PageContent>
    );
}

export default Lobby;
