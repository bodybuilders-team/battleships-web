import {ShipType} from "../ship/ShipType";

/**
 * A game configuration.
 *
 * @property gridSize the size of the grid
 * @property shotsPerRound the number of shots per round
 * @property maxTimePerRound the maximum time per shot
 * @property maxTimeForLayoutPhase the maximum time for the layout phase
 * @property ships the ships to be used in the game
 */
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

    /**
     * Converts the GameConfig to a GameConfigModel.
     *
     * @return the GameConfigModel
     */
    toGameConfigModel(): GameConfigModel {
        return {
            gridSize: this.gridSize,
            shotsPerRound: this.shotsPerRound,
            maxTimePerRound: this.maxTimePerRound,
            maxTimeForLayoutPhase: this.maxTimeForLayoutPhase,
            shipTypes: GameConfig.mapToShipTypesModel(this.shipTypes)
        };
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