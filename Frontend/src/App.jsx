import { Routes, Route, Link } from 'react-router-dom'
import Home from './pagesTeamH/Home'
import CreateTournament from "./pagesTeamH/CreateTournament.jsx";

function App() {
    return (
        <>
            <nav>
                <Link to="/">Home</Link> | <Link to="/tournament">Tournament</Link>
            </nav>

            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/tournament" element={<CreateTournament />} />
            </Routes>
        </>
    )
}

export default App
