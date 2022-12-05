import * as React from "react";
import {useEffect} from "react";
import {EmbeddedSubEntity} from "../../../Services/utils/siren/SubEntity";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import PageContent from "../../Utils/PageContent";
import {GetGameOutputModel} from "../../../Services/games/models/games/getGame/GetGameOutput";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {Rels} from "../../../Services/utils/Rels";

/**
 * Lobby component.
 */
function Lobby() {

    const [lobby, setLobby] = React.useState<GetGameOutputModel[]>([]);
    const [error, setError] = React.useState<string | null>(null);
    const [battleshipService, setBattleshipService] = useBattleshipsService()

    useEffect(() => {
        const fetchGames = async () => {
            const [err, res] = await to(battleshipService.gamesService.getGames());

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
    });

    return (
        <PageContent title="Lobby" error={error}>
            {/*TODO: To be implemented*/}
        </PageContent>
    );
}

export default Lobby;
