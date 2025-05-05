import { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Navbar from './Navbar';
import Login from './Login'; // import Login component
import Register from './Register'; // import Register component

function App() {
  const [count, setCount] = useState(0);

  return (
    <Router>
      <Navbar />
      <main style={{ backgroundColor: '#0e0f13', height: '100vh', color: 'white', padding: '20px' }}>
        <Routes>
          {/* Define routes for your pages */}
          <Route path="/" element={<h1>Welcome to the site</h1>} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Routes>
      </main>
    </Router>
  );
}

export default App;
