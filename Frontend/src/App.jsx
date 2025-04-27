import { Routes, Route } from 'react-router-dom';
import { useState } from 'react'
import './App.css'

import Tournament from "./components/Tournament";

function App() {

  return (
    <>
    <Routes>
       <Route path="/Tournament" element={<Tournament />} />
    </Routes>
    </>
  )
}

export default App
