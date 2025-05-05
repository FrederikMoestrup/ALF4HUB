import { Routes, Route } from 'react-router-dom';
import './App.css'

import LeaveTeam from './components/LeaveTeam';

function App() {

  return (
    <>
    <Routes>
       <Route path="/LeaveTeam" element={<LeaveTeam />} />
    </Routes>
    </>
  )
}

export default App
