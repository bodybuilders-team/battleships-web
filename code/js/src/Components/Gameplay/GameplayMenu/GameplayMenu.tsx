import * as React from "react";
import PageContent from "../../Shared/PageContent";
import MenuButton from "../../Shared/MenuButton";
import {AddRounded, PlayArrowRounded, SearchRounded} from "@mui/icons-material";
import {useNavigate} from "react-router-dom";

/**
 * GameplayMenu component.
 */
export default function GameplayMenu() {
    const navigate = useNavigate();

    return (
        <PageContent title={"Gameplay Menu"}>
            <MenuButton
                title={"Quick Play"}
                icon={<PlayArrowRounded/>}
                onClick={() => navigate("/matchmake")}
            />
            <MenuButton
                title={"New Game"}
                icon={<AddRounded/>}
                onClick={() => navigate("/create-game")}
            />
            <MenuButton
                title={"Lobby"}
                icon={<SearchRounded/>}
                onClick={() => navigate("/lobby")}
            />
        </PageContent>
    );
}
