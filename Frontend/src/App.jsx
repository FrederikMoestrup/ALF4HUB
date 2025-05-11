
import { Routes, Route } from 'react-router-dom';
import { useState } from 'react';
import './App.css'
import LeaveTeam from './components/LeaveTeam';
import './acces.css';
import Navbar from './Navbar';
import Login from './components/Login'; 
import Register from './components/Register';
import Logo from './assets/ALTF4HUB.png';

function App() {

  return (
    <>
    <Routes>
       <Route path="/LeaveTeam" element={<LeaveTeam />} />
    </Routes>

      <Navbar />
        <main style={{ backgroundColor: '#0e0f13', height: '100vh', color: 'white', padding: '20px' }}>
          <Routes>
            <Route path="/LeaveTeam" element={<LeaveTeam />} />
            <Route path="/" element={
            <div style={{textAlign: 'center'}}>
              <h1>Welcome to </h1>
              <img src={Logo} alt="ALTF4Hub Logo" className="logo-img" />
                </div>
              }  
            />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
          </Routes>
        </main>
    </>
  );
}

export default App;
