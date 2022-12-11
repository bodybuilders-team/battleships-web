import * as React from "react";
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import Matchmake from "../Matchmake/Matchmake";
import CreateGame from "../CreateGame/CreateGame";
import PageContent from "../../Utils/PageContent";
import Lobby from "../Lobby/Lobby";
import {MenuButton} from "../../Utils/MenuButton";

/**
 * GameplayMenu component.
 */
function GameplayMenu() {
    const [matchmaking, setMatchmaking] = React.useState(false);
    const [creating, setCreating] = React.useState(false);
    const [lobby, setLobby] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);

    //TODO Add routing for each component
    if (matchmaking)
        return <Matchmake/>;
    else if (creating)
        return <CreateGame/>;
    else if (lobby)
        return <Lobby/>;

    return (
        <PageContent title={"Gameplay Menu"} error={error}>
            <MenuButton title={"Quick Play"} icon={<PlayArrowIcon/>} onClick={() => setMatchmaking(true)}/>
            <MenuButton title={"New Game"} icon={<AddIcon/>} onClick={() => setCreating(true)}/>
            <MenuButton title={"Search Game"} icon={<SearchIcon/>} onClick={() => setLobby(true)}/>
        </PageContent>
    );
}

export default GameplayMenu;
