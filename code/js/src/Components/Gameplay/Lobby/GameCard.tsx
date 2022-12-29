import * as React from "react"
import {useState} from "react"
import {styled} from "@mui/material/styles"
import Card from "@mui/material/Card"
import CardHeader from "@mui/material/CardHeader"
import CardContent from "@mui/material/CardContent"
import Collapse from "@mui/material/Collapse"
import IconButton, {IconButtonProps} from "@mui/material/IconButton"
import Typography from "@mui/material/Typography"
import ExpandMoreIcon from "@mui/icons-material/ExpandMore"
import {PlayArrow} from "@mui/icons-material"
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput"


/**
 * Properties for the GameCard component.
 *
 * @property game the game to display
 * @property onJoinGameRequest the callback to call when the user wants to join the game
 */
interface GameCardProps {
    game: GetGameOutputModel
    onJoinGameRequest: () => void
}

/**
 * Game card component.
 */
export default function GameCard({game, onJoinGameRequest}: GameCardProps) {
    const [expanded, setExpanded] = useState(false)
    const handleExpandClick = () => {
        setExpanded(!expanded)
    }

    return (
        <Card sx={{maxWidth: 345}}>
            <CardHeader
                action={
                    <div>
                        <ExpandMore
                            expand={expanded}
                            onClick={handleExpandClick}
                            aria-expanded={expanded}
                            aria-label="show more"
                        >
                            <ExpandMoreIcon/>
                        </ExpandMore>
                        <IconButton aria-label="settings" onClick={onJoinGameRequest}>
                            <PlayArrow/>
                        </IconButton>
                    </div>
                }
                title={game.name}
                subheader={"Created by " + game.creator}
            />
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <CardContent sx={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "start"
                }}>
                    < Typography>Grid Size: {game.config.gridSize}</Typography>
                    <Typography>Board Layout Time: {game.config.maxTimeForLayoutPhase}s</Typography>
                    <Typography>Shots per Round: {game.config.shotsPerRound}</Typography>
                    <Typography>Time per Round: {game.config.maxTimePerRound}s</Typography>
                </CardContent>
            </Collapse>
        </Card>
    )
}

/**
 * Properties for the ExpandMore component.
 *
 * @property expand expanded state
 */
interface ExpandMoreProps extends IconButtonProps {
    expand: boolean
}

const ExpandMore = styled((props: ExpandMoreProps) => {
    const {expand, ...other} = props
    return <IconButton {...other} />
})(({theme, expand}) => ({
    transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
        duration: theme.transitions.duration.shortest
    })
}))
