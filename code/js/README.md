# Battleships - Frontend Documentation

> This is the frontend documentation for the Battleships project.

## Table of Contents

* [Introduction](#introduction)
* [Code Organization](#code-organization)
* [API Connectivity](#api-connectivity)
* [Authentication and Session Management](#authentication-and-session-management)
* [Component Management](#component-management)
* [Conclusions - Critical Evaluation](#conclusions---critical-evaluation)

---

## Introduction

The frontend is a single page application built using React and Material-UI.
It is a responsive web application that can be used on any device.

This application is a client for the Battleships API, which is documented [here](../../docs/battleships-api-doc.md).
For more information about the backend, please refer to the [backend documentation](../jvm/README.md).

---

## Code Organization

The frontend code is organized in the following way:

* `js`
    * `public` - Contains the `index.html` file, the `favicon.ico` file and the `manifest.json` file;
    * `src`
        * `Assets` - Contains the images and other assets used in the application;
        * `Components` - Contains the React components and pages used in the application;
        * `Domain` - Contains the domain classes used in the application;
        * `Layout` - Contains the layout components used in the application, such as the `Navbar` and the `Footer`;
        * `Services` - Contains the services used in the application; this layer is responsible for the communication
          with the API;
        * `Utils` - Contains the utility classes used in the application;
        * `App.js` - The main component of the application;
        * `index.js` - The entry point of the application;

In the `js` folder, there are other files used for the development of the application, like the `package.json` file,
the `tsconfig.json` file and the `webpack.config.js` file.

---

## API Connectivity

The API connectivity is done by the service layer.

The media types used in the communication with the API are the following:

* `application/json` - Used in the request bodies;
* `application/problem+json` - Used in the response bodies when an error occurs;
* `application/vnd.siren+json` - Used in the response bodies when the request is successful.

There are two implementations of the service layer (one using the other):

* `BattleshipsServive` - The main implementation of the service layer, which the methods receive the link of the request
  as an argument;
* `NavigationBattleshipsService` - The implementation of the service layer that abstracts the link of the request, so
  the methods don't receive the link as an argument; this is the implementation used in the application, and each method
  calls the respective method in the `BattleshipsService` implementation.

To make the requests, the `fetch` API is used. A `fetchSiren` function was implemented to make the requests to the API
and to parse the response body to a `Siren` object or to a `Problem` object.

For each request method (GET, POST, PUT, DELETE), a function that calls the `fetchSiren` function was implemented, to
simplify the code.

The `NavigationState` interface was implemented to store the state of the navigation in the application. This state is
used to store the links of the user journey in the application. The `NavigationStateContext` was implemented to store
the `NavigationState` object in the React context, so it can be accessed from any component in the application, using
the `useNavigationState` hook.

---

## Authentication and Session Management

The user authentication is done in the `Login` or `Register` pages.

The `Session` interface was implemented to store the session information in the application. This session is managed
by the `SessionManagerContext`. The `Auth` component provides the `SessionManagerContext` to the children components,
so they can access the session information using the `useSessionManager` hook, and access the `Session` object using
the `useSession` hook.

The `useLoggedIn` hook was implemented to check if the user is logged in.

The user session is stored in the browser's local storage.

---

## Component Management

The `AbortSignal` interface was used to cancel side effects in the components. This signal is obtained from the
`useMountedSignal` hook, which returns a signal that is aborted when the component is unmounted.
This signal is after passed to the service layer, so the requests can be cancelled when the component is unmounted.

The `AbortedError` class was implemented to represent the error that is thrown when the request is cancelled.
This class extends the `Error` class, and is based on the Kotlin coroutines cancellation mechanism.

Some utility functions using abort signals were implemented:

* `setAbortableTimeout` - Sets a timeout that is aborted when the signal is aborted;
* `delay` - Delays the execution of the function for the specified amount of time;
* `abortableTo` - Executes a promise and aborts it if the signal is aborted.

---

## Conclusions - Critical Evaluation

In conclusion, we can say that the frontend was a success. We think that the application is resanobly responsive and the
user experience is good. The code is well organized and the components are well-structured.

All the requirements were implemented, and the application is fully functional.

The main challenges of the second phase of the project were:

* The cleanup of the component unmounting;
* Managing the navigation state;
* Implement the ship dragging and dropping.

Even though we had these difficulties, we think that we managed to overcome them thanks to our team work and the
information we got from the teacher in the classes.

We think that the frontend is a good example of a React application, and we hope that it can be useful for other people
that want to learn React.

In the future, we think that we can improve the frontend by implementing the following features:

* Add server-side events to the `GameState` polling;
* Allow the user to create custom ships;
* Allow the user to loggin using the Google account;
* Add the possibility to create private games.
