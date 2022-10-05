package pt.isel.daw.battleships.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for the home page.
 */
@RestController
@RequestMapping("/home")
class HomeController {

    /**
     * Returns the home page.
     *
     * @return the home page
     */
    @GetMapping(produces = ["application/json"])
    fun home(): String {
        return """
            {
                "name": "Battleships",
                "version": "0.0.1",
                "description": "Battleships is a game where you have to sink all the ships of your opponent.",
                "authors": [
                    {
                        "name": "André Jesus",
                        "email": "andre.jesus.pilar@gmail.com",
                        "github": "https://github.com/andre-j3sus"
                    },
                    {
                        "name": "André Páscoa",
                        "email": "andre@pascoa.org",
                        "github": "https://github.com/devandrepascoa"
                    },
                    {
                        "name": "Nyckollas Brandão",
                        "email": "nyckbrandao1236@gmail.com",
                        "github": "https://github.com/nyckoka"
                    }
                ],
                "repository": {
                    "type": "git",
                    "url": "https://github.com/isel-leic-daw/2022-daw-leic51d-g03"
                },
                "links": [
                    {
                        "rel": "self",
                        "href": "/home",
                        "method": "GET",
                        "requiresAuth": false
                    },
                    {
                        "rel": "register",
                        "href": "/users",
                        "method": "POST",
                        "requiresAuth": false
                    },
                    {
                        "rel": "login",
                        "href": "/users/login",
                        "method": "POST",
                        "requiresAuth": false
                    },
                    {
                        "rel": "games",
                        "href": "/games",
                        "method": "GET",
                        "requiresAuth": true
                    }
                ]
            }
        """.trimIndent()
    }
}
