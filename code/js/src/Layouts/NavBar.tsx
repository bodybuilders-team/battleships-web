import * as React from 'react'
import {useState} from 'react'
import AppBar from '@mui/material/AppBar'
import Box from '@mui/material/Box'
import Toolbar from '@mui/material/Toolbar'
import IconButton from '@mui/material/IconButton'
import Typography from '@mui/material/Typography'
import Menu from '@mui/material/Menu'
import MenuIcon from '@mui/icons-material/Menu'
import Container from '@mui/material/Container'
import Avatar from '@mui/material/Avatar'
import Button from '@mui/material/Button'
import Tooltip from '@mui/material/Tooltip'
import MenuItem from '@mui/material/MenuItem'
import DirectionsBoatFilledRoundedIcon from '@mui/icons-material/DirectionsBoatFilledRounded'
import {useNavigate} from 'react-router-dom'
import {useLoggedIn, useSession, useSessionManager} from "../Utils/Session"
import {useBattleshipsService} from "../Services/NavigationBattleshipsService"
import {abortableTo} from "../Utils/componentManagement/abortableUtils"

const pages = [
    {name: 'Login', href: '/login', auth: false},
    {name: 'Register', href: '/register', auth: false},
    {name: 'Play', href: '/gameplay-menu', auth: true},
    {name: 'Ranking', href: '/ranking'},
    {name: 'About', href: '/about'},
]

/**
 * NavBar component.
 */
export default function NavBar() {
    const navigate = useNavigate()
    const loggedIn = useLoggedIn()
    const sessionManager = useSessionManager()
    const session = useSession()
    const battleshipsService = useBattleshipsService()

    const settings = [
        {
            name: 'Profile',
            callback: () => navigate('/profile'),
            auth: true
        },
        {
            name: 'Logout',
            callback: async () => {
                if (!session) {
                    navigate('/')
                    return
                }

                await abortableTo(
                    battleshipsService.usersService.logout()
                )

                sessionManager.clearSession()

                navigate('/')
            },
            auth: true
        }
    ]

    const [anchorElNav, setAnchorElNav] = useState<null | HTMLElement>(null)
    const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null)

    const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorElNav(event.currentTarget)
    }
    const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorElUser(event.currentTarget)
    }

    const handleCloseNavMenu = () => setAnchorElNav(null)
    const handleCloseUserMenu = () => setAnchorElUser(null)

    return (
        <AppBar position="static">
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <DirectionsBoatFilledRoundedIcon sx={{display: {xs: 'none', md: 'flex'}, mr: 1}}/>
                    <Typography
                        variant="h6"
                        noWrap
                        component="a"
                        sx={{
                            mr: 2,
                            display: {xs: 'none', md: 'flex'},
                            fontFamily: 'monospace',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: 'inherit',
                            textDecoration: 'none',
                            cursor: 'pointer'
                        }}
                        onClick={() => navigate('/')}
                    >
                        Battleships
                    </Typography>

                    <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'none'}}}>
                        <IconButton
                            size="large"
                            aria-label="account of current user"
                            aria-controls="menu-appbar"
                            aria-haspopup="true"
                            onClick={handleOpenNavMenu}
                            color="inherit"
                        >
                            <MenuIcon/>
                        </IconButton>
                        <Menu
                            id="menu-appbar"
                            anchorEl={anchorElNav}
                            anchorOrigin={{
                                vertical: 'bottom',
                                horizontal: 'left',
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: 'top',
                                horizontal: 'left',
                            }}
                            open={Boolean(anchorElNav)}
                            onClose={handleCloseNavMenu}
                            sx={{display: {xs: 'block', md: 'none'},}}
                        >
                            {pages.map((page) => (
                                (page.auth && loggedIn || !page.auth && !loggedIn || page.auth === undefined) &&
                                <MenuItem key={page.name} onClick={() => {
                                    handleCloseNavMenu()
                                    navigate(page.href)
                                }}>
                                    <Typography textAlign="center">{page.name}</Typography>
                                </MenuItem>
                            ))}
                        </Menu>
                    </Box>
                    <DirectionsBoatFilledRoundedIcon sx={{display: {xs: 'flex', md: 'none'}, mr: 1}}/>
                    <Typography
                        variant="h5"
                        noWrap
                        component="a"
                        sx={{
                            mr: 2,
                            display: {xs: 'flex', md: 'none'},
                            flexGrow: 1,
                            fontFamily: 'monospace',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: 'inherit',
                            textDecoration: 'none',
                            cursor: 'pointer'
                        }}
                        onClick={() => navigate('/')}
                    >
                        Battleships
                    </Typography>
                    <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
                        {pages.map((page) => (
                            (page.auth && loggedIn || !page.auth && !loggedIn || page.auth === undefined) &&
                            <Button
                                key={page.name}
                                onClick={() => {
                                    handleCloseNavMenu()
                                    navigate(page.href)
                                }}
                                sx={{my: 2, color: 'white', display: 'block'}}
                            >
                                {page.name}
                            </Button>
                        ))}
                    </Box>

                    {/* TODO: <IconButton
                        sx={{ml: 1}}
                        onClick={colorMode.toggleColorMode}
                        color="inherit"
                    >
                        {theme.palette.mode === 'dark' ? <Brightness7Icon/> : <Brightness4Icon/>}
                    </IconButton>*/}

                    {
                        loggedIn
                            ? <Box sx={{flexGrow: 0}}>
                                <Tooltip title="Open settings">
                                    <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
                                        <Avatar alt="User Avatar" src=""/>
                                    </IconButton>
                                </Tooltip>
                                <Menu
                                    sx={{mt: '45px'}}
                                    id="menu-appbar"
                                    anchorEl={anchorElUser}
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    open={Boolean(anchorElUser)}
                                    onClose={handleCloseUserMenu}
                                >
                                    {settings.map((setting) => (
                                        (setting.auth && loggedIn || !setting.auth && !loggedIn || setting.auth === undefined) &&
                                        <MenuItem key={setting.name} onClick={() => {
                                            handleCloseUserMenu()
                                            setting.callback()
                                        }}>
                                            <Typography textAlign="center">{setting.name}</Typography>
                                        </MenuItem>
                                    ))}
                                </Menu>
                            </Box>
                            : <></>
                    }
                </Toolbar>
            </Container>
        </AppBar>
    )
}
