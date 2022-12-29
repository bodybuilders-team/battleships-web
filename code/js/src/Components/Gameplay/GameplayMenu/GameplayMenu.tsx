import * as React from "react"
import PageContent from "../../Shared/PageContent"
import MenuButton from "../../Shared/MenuButton"
import {AddRounded, PlayArrowRounded, SearchRounded} from "@mui/icons-material"
import {useNavigate} from "react-router-dom"
import {Uris} from "../../../Utils/navigation/Uris"
import MATCHMAKE = Uris.MATCHMAKE;
import CREATE_GAME = Uris.CREATE_GAME;
import LOBBY = Uris.LOBBY;

/**
 * GameplayMenu component.
 */
export default function GameplayMenu() {
    const navigate = useNavigate()

    return (
        <PageContent title={"Gameplay Menu"}>
            <MenuButton
                title={"Quick Play"}
                icon={<PlayArrowRounded/>}
                onClick={() => navigate(MATCHMAKE, {relative: "path"})}
            />
            <MenuButton
                title={"New Game"}
                icon={<AddRounded/>}
                onClick={() => navigate(CREATE_GAME)}
            />
            <MenuButton
                title={"Search Game"}
                icon={<SearchRounded/>}
                onClick={() => navigate(LOBBY)}
            />
        </PageContent>
    )
}
