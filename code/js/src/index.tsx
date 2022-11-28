import * as React from 'react';
import {createRoot} from 'react-dom/client';
import './index.css';
import App from './App';
import {BrowserRouter as Router} from 'react-router-dom';

const root = createRoot(document.getElementById('root') as HTMLElement);

root.render(
    <React.StrictMode>
        <Router>
            <App/>
        </Router>
    </React.StrictMode>
);
