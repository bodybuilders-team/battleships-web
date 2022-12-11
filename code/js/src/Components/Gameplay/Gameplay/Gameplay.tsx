import * as React from "react";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput";
import LoadingSpinner from "../../Utils/LoadingSpinner";
import PageContent from "../../Utils/PageContent";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {Rels} from "../../../Utils/navigation/Rels";
import {ShipType} from "../../../Domain/games/ship/ShipType";
import ShootingGameplay from "./ShootingGameplay";
import {useNavigationState} from "../../../Utils/navigation/NavigationStateProvider";
import BoardSetupGameplay from "../BoardSetup/BoardSetupGameplay";


export class GameConfig {
    readonly gridSize: number;
    readonly shotsPerRound: number;
    readonly maxTimePerRound: number;
    readonly maxTimeForLayoutPhase: number;
    readonly shipTypes: ReadonlyMap<ShipType, number>;

    constructor(gameConfigModel: GameConfigModel) {
        this.gridSize = gameConfigModel.gridSize;
        this.shotsPerRound = gameConfigModel.shotsPerRound;
        this.maxTimePerRound = gameConfigModel.maxTimePerRound;
        this.maxTimeForLayoutPhase = gameConfigModel.maxTimeForLayoutPhase;
        this.shipTypes = GameConfig.shipTypesModelToMap(gameConfigModel.shipTypes);
    }

    toGameConfigModel(): GameConfigModel {
        return {
            gridSize: this.gridSize,
            shotsPerRound: this.shotsPerRound,
            maxTimePerRound: this.maxTimePerRound,
            maxTimeForLayoutPhase: this.maxTimeForLayoutPhase,
            shipTypes: GameConfig.mapToShipTypesModel(this.shipTypes)
        }
    }

    static shipTypesModelToMap(shipTypes: ShipTypeModel[]) {
        const map = new Map<ShipType, number>();
        shipTypes.forEach(shipType => map.set(shipType, shipType.quantity));
        return map;
    }

    static mapToShipTypesModel(shipTypes: ReadonlyMap<ShipType, number>) {
        const shipTypesModel: ShipTypeModel[] = [];
        shipTypes.forEach((quantity, shipType) => {
            shipTypesModel.push({...shipType, quantity});
        });
        return shipTypesModel;
    }
}

export class GameState {
    readonly phase: "WAITING_FOR_PLAYERS" | "DEPLOYING_FLEETS" | "IN_PROGRESS" | "FINISHED";
    readonly phaseEndTime: number;
    readonly round: number | null;
    readonly turn: string | null;
    readonly winner: string | null;

    constructor(gameStateModel: GameStateModel) {
        this.phase = gameStateModel.phase;
        this.phaseEndTime = gameStateModel.phaseEndTime;
        this.round = gameStateModel.round;
        this.turn = gameStateModel.turn;
        this.winner = gameStateModel.winner;
    }
}

export class Player {
    readonly username: string;
    readonly points: number;

    constructor(playerModel: PlayerModel) {
        this.username = playerModel.username;
        this.points = playerModel.points;
    }

    toPlayerModel(): PlayerModel {
        return {
            username: this.username,
            points: this.points
        }
    }
}

export class Game {
    readonly id: string
    readonly name: string;
    readonly creator: string;
    readonly config: GameConfig;
    readonly state: GameState;
    readonly players: ReadonlyArray<Player>

    constructor(gameModel: GetGameOutputModel) {
        this.id = gameModel.id;
        this.name = gameModel.name;
        this.creator = gameModel.creator;
        this.config = new GameConfig(gameModel.config);
        this.state = new GameState(gameModel.state);
        this.players = gameModel.players.map(player => new Player(player));
    }

    static toGameModel(game: Game): GetGameOutputModel {
        return {
            id: game.id,
            name: game.name,
            creator: game.creator,
            config: game.config.toGameConfigModel(),
            state: {
                phase: game.state.phase,
                phaseEndTime: game.state.phaseEndTime,
                round: game.state.round,
                turn: game.state.turn,
                winner: game.state.winner
            },
            players: game.players.map(player => player.toPlayerModel())
        }
    }
}

/**
 * Play component.
 */
function Gameplay() {
    const navigate = useNavigate();

    const [game, setGame] = React.useState<Game | null>(null);
    const [error, setError] = React.useState<string | null>(null);
    const battleshipsService = useBattleshipsService()
    const navigationState = useNavigationState()

    useEffect(() => {
        const fetchGame = async () => {
            if (!battleshipsService.links.get(Rels.GAME)) {
                navigate("/");
                return
            }

            const [err, res] = await to(
                battleshipsService.gamesService.getGame()
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            if (res?.properties === undefined)
                throw new Error("Properties are undefined");

            navigationState.setLinks(battleshipsService.links);
            const newGame = new Game(res.properties as GetGameOutputModel);
            console.log(newGame)
            setGame(newGame);
        }
        fetchGame();
    }, []);

    if (game?.state.phase === "DEPLOYING_FLEETS")
        return (
            <BoardSetupGameplay gameConfig={game.config} onBoardSetupPhaseFinished={(newGameState) => {
                setGame({...game, state: newGameState})
            }}/>
        )
    else if (game?.state.phase === "IN_PROGRESS")
        return <ShootingGameplay game={game}/>
    else if (game?.state.phase === "FINISHED")
        return <div>Game finished</div>;
    else
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Loading game..."}/>
            </PageContent>
        );
}

export default Gameplay;
