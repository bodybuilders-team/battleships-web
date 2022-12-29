import * as React from "react"
import PageContent from "../Shared/PageContent"
import Box from "@mui/material/Box"
import IconButton from "@mui/material/IconButton"
import Typography from "@mui/material/Typography"

/**
 * About component.
 */
export default function About() {
    return (
        <PageContent title={"Authors"}>
            {
                authors.map((author, index) => (
                    <Box
                        key={index}
                        sx={{
                            display: "flex",
                            flexDirection: "row",
                            alignItems: "center",
                            width: "100%",
                            paddingBottom: "40px",
                        }}
                    >
                        <img
                            src={author.image}
                            alt={author.name}
                            style={{
                                width: "100px",
                                height: "100px",
                                borderRadius: "50%",
                                marginRight: "20px"
                            }}
                        />
                        <Box sx={{
                            display: "flex",
                            flexDirection: "column",
                            alignItems: "flex-start",
                            width: "100%",
                        }}>
                            <h2>{author.name}</h2>
                            <Box sx={{
                                display: "flex",
                                flexDirection: "row",
                                alignItems: "center",
                                width: "100%",
                            }}>
                                <IconButton
                                    href={author.githubLink}
                                    target="_blank"
                                    rel="noreferrer"
                                >
                                    <img
                                        src="https://img.icons8.com/ios-filled/50/000000/github.png"
                                        alt="Github"
                                        style={{
                                            width: "50px",
                                            height: "50px",
                                        }}
                                    />
                                </IconButton>
                                <IconButton
                                    href={"mailto:" + author.email}
                                    target="_blank"
                                    rel="noreferrer"
                                >
                                    <img
                                        src="https://img.icons8.com/ios-filled/50/000000/email.png"
                                        alt="Email"
                                        style={{
                                            width: "50px",
                                            height: "50px",
                                        }}
                                    />
                                </IconButton>
                            </Box>
                        </Box>
                    </Box>
                ))
            }
            <Typography variant="h6" component="div" gutterBottom>
                The project is open source and can be found on <a href={githubRepo}>GitHub</a>
            </Typography>
        </PageContent>
    )
}

/**
 * Information about an author.
 *
 * @property number the student number of the author
 * @property name the first and last name of the author
 * @property githubLink the github profile link of the author
 * @property email the email contact of the author
 * @property image the image of the author
 */
interface AuthorInfo {
    number: string
    name: string
    githubLink: string
    email: string
    image: string
}

const authors: AuthorInfo[] = [
    {
        number: "48089",
        name: "André Páscoa",
        githubLink: "https://github.com/devandrepascoa",
        email: "48089@alunos.isel.pt",
        image: "https://avatars.githubusercontent.com/u/10800929?v=4"
    },
    {
        number: "48280",
        name: "André Jesus",
        githubLink: "https://github.com/andre-j3sus",
        email: "48280@alunos.isel.pt",
        image: "https://avatars.githubusercontent.com/u/74548852?v=4"
    },
    {
        number: "48287",
        name: "Nyckollas Brandão",
        githubLink: "https://github.com/Nyckoka",
        email: "48287@alunos.isel.pt",
        image: "https://avatars.githubusercontent.com/u/12070060?v=4"
    }
]

const githubRepo = "https://github.com/isel-leic-daw/2022-daw-leic51d-g03"
