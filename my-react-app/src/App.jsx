import { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import './acces.css';
import Navbar from './Navbar';
import Login from './Login'; 
import Register from './Register'; 
import Logo from './assets/ALTF4HUB.png';

function App() {
  const [count, setCount] = useState(0);

  return (
    <Router>
      <Navbar />
      <main style={{ backgroundColor: '#0e0f13', height: '100vh', color: 'white', padding: '20px' }}>
        <Routes>
          {/* Define routes for your pages */}
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
    </Router>
  );
}

export default App;
