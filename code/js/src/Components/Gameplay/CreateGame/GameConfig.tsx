import Box from "@mui/material/Box"
import TextField from "@mui/material/TextField"
import GameConfigSlider from "./GameConfigSlider"
import {defaultBoardSize, maxBoardSize, minBoardSize} from "../../../Domain/games/board/Board"
import ShipSelector from "./ShipSelector"
import Button from "@mui/material/Button"
import {Add} from "@mui/icons-material"
import PageContent from "../../Shared/PageContent"
import * as React from "react"
import {ShipType} from "../../../Domain/games/ship/ShipType"

const defaultShotsPerTurn = 1
const minShotsPerTurn = 1
const maxShotsPerTurn = 5

const defaultMaxTimePerTurn = 1
const minMaxTimePerTurn = 10
const maxMaxTimePerTurn = 120
const stepMaxTimePerTurn = 10

const defaultMaxTimeForLayoutPhase = 60
const minMaxTimeForLayoutPhase = 30
const maxMaxTimeForLayoutPhase = 120
const stepMaxTimeForLayoutPhase = 10

/**
 * Properties for GameConfig component.
 *
 * @property setGameName callback to set the game name
 * @property gridSize the grid size
 * @property setGridSize callback to set the grid size
 * @property maxTimePerRound the maximum time per round
 * @property setMaxTimePerRound callback to set the maximum time per round
 * @property shotsPerRound the shots per round
 * @property setShotsPerRound callback to set the shots per round
 * @property maxTimeForLayoutPhase the maximum time for the layout phase
 * @property setMaxTimeForLayoutPhase callback to set the maximum time for the layout phase
 * @property shipTypes the ship types
 * @property setShipTypes callback to set the ship types
 * @property handleCreateGame callback to handle the creation of a game
 * @property error the error
 */
interface GameConfigProps {
    setGameName: (gameName: string) => void
    gridSize: number
    setGridSize: (gridSize: number) => void
    maxTimePerRound: number
    setMaxTimePerRound: (maxTimePerRound: number) => void
    shotsPerRound: number
    setShotsPerRound: (shotsPerRound: number) => void
    maxTimeForLayoutPhase: number
    setMaxTimeForLayoutPhase: (maxTimeForLayoutPhase: number) => void
    shipTypes: Map<ShipType, number>
    setShipTypes: (shipTypes: Map<ShipType, number>) => void
    handleCreateGame: () => void
    error: string | null
}

/**
 * GameConfig component.
 */
function GameConfig(
    {
        setGameName,
        gridSize,
        setGridSize,
        maxTimePerRound,
        setMaxTimePerRound,
        shotsPerRound,
        setShotsPerRound,
        maxTimeForLayoutPhase,
        setMaxTimeForLayoutPhase,
        shipTypes,
        setShipTypes,
        handleCreateGame,
        error
    }: GameConfigProps
) {
    return (
        <PageContent title={"Game Configuration"} error={error}>
            <Box sx={{
                mt: 1,
                alignItems: 'center',
                width: 400,
            }}>
                <TextField
                    margin="normal"
                    fullWidth
                    label="Game Name"
                    name="gameName"
                    onChange={(event) => {
                        setGameName(event.target.value)
                    }}
                />

                <GameConfigSlider
                    id={"board-size-slider"}
                    label={`Grid Size ${gridSize}x${gridSize}`}
                    defaultValue={defaultBoardSize}
                    step={1}
                    min={minBoardSize}
                    max={maxBoardSize}
                    onValueChange={(value) => {
                        setGridSize(value)

                        Array.from(shipTypes.keys()).forEach(shipType => {
                            shipTypes.set(shipType, 1)
                        })
                        setShipTypes(new Map(shipTypes))
                    }}
                />

                <GameConfigSlider
                    id={"shots-per-turn-slider"}
                    label={`Shots per turn ${shotsPerRound}`}
                    defaultValue={defaultShotsPerTurn}
                    step={1}
                    min={minShotsPerTurn}
                    max={maxShotsPerTurn}
                    onValueChange={setShotsPerRound}
                />

                <GameConfigSlider
                    id={"time-per-turn-slider"}
                    label={`Time per turn ${maxTimePerRound} seconds`}
                    defaultValue={defaultMaxTimePerTurn}
                    step={stepMaxTimePerTurn}
                    min={minMaxTimePerTurn}
                    max={maxMaxTimePerTurn}
                    onValueChange={setMaxTimePerRound}
                />

                <GameConfigSlider
                    id={"time-for-board-configuration-slider"}
                    label={`Time for board configuration ${maxTimeForLayoutPhase} seconds`}
                    defaultValue={defaultMaxTimeForLayoutPhase}
                    step={stepMaxTimeForLayoutPhase}
                    min={minMaxTimeForLayoutPhase}
                    max={maxMaxTimeForLayoutPhase}
                    onValueChange={setMaxTimeForLayoutPhase}
                />

                <ShipSelector
                    shipTypes={shipTypes}
                    setShipTypes={setShipTypes}
                    gridSize={gridSize}
                />

                <Button
                    fullWidth
                    size="large"
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                    startIcon={<Add/>}
                    color="primary"
                    onClick={() => {
                        handleCreateGame()
                    }}
                >
                    Create Game
                </Button>
            </Box>
        </PageContent>
    )
}

export default GameConfig
