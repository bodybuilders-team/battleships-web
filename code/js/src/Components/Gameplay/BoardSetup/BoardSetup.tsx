import * as React from "react";
import BoardView from "../Shared/Board/BoardView";
import {ConfigurableBoard} from "../../../Domain/games/board/ConfigurableBoard";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import {Card, CardActions, CardContent, Divider} from "@mui/material";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import ShipView from "../Shared/Ship/ShipView";
import {ShipType} from "../../../Domain/games/ship/ShipType";
import {Orientation} from "../../../Domain/games/ship/Orientation";
import {generateEmptyMatrix, generateRandomMatrix} from "../../../Domain/games/board/Board";


/**
 * BoardSetup component.
 *
 * @param boardSize the size of the board
 * @param ships the list of ships to be placed
 */
function BoardSetup({boardSize, ships}: { boardSize: number, ships: ShipType[] }) {

    const [board, setBoard] = React.useState<ConfigurableBoard>(new ConfigurableBoard(boardSize, generateEmptyMatrix(boardSize)));
    const [unplacedShips, setUnplacedShips] = React.useState<ShipType[]>(ships);

    return (
        <Box component="main">
            <Container maxWidth="lg">
                <Typography sx={{mb: 3}} variant="h4">Board Setup</Typography>
                <Grid container spacing={3}>
                    <Grid item lg={4} md={6} xs={12}>
                        <Card>
                            <CardContent>
                                <Box
                                    sx={{
                                        alignItems: 'center',
                                        display: 'flex',
                                        flexDirection: 'row'
                                    }}
                                >
                                    {
                                        unplacedShips.map(ship => (
                                            <ShipView
                                                type={ship}
                                                orientation={Orientation.VERTICAL}
                                                key={ship.shipName}
                                            />
                                        ))
                                    }
                                </Box>
                            </CardContent>
                            <Divider/>
                            <CardActions>
                                <Button color="primary" fullWidth onClick={() => {
                                    setBoard(new ConfigurableBoard(boardSize, generateRandomMatrix(boardSize, ships)));
                                    setUnplacedShips([]);
                                }}>
                                    Random Board
                                </Button>
                                <Button color="primary" fullWidth onClick={() => {
                                    if (board.fleet.length === ships.length) {
                                        // TODO: do something with the board
                                    } else {
                                        alert("You must place all the ships!");
                                    }
                                }}>
                                    Confirm Board
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                    <Grid item lg={8} md={6} xs={12}>
                        <BoardView size={boardSize} grid={[]}/>
                    </Grid>
                </Grid>
            </Container>
        </Box>
    )
        ;
}

export default BoardSetup;
