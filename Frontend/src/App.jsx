import { Routes, Route } from 'react-router-dom';
import { useState } from 'react';
import './App.css'
import './acces.css';

import LeaveTeam from './components/LeaveTeam';
import Navbar from './Navbar';
import Login from './components/Login'; 
import Register from './components/Register';
import Logo from './assets/ALTF4HUB.png';

import BlogPostFrontPage from "./pages/BlogPostFrontPage.jsx";
import CreateBlogPost from "./pages/CreateBlogPost.jsx";
import Drafts from "./pages/Drafts.jsx";

function App() {

  return (
    <>
      <Navbar />
      <main style={{ backgroundColor: '#0e0f13', height: '100vh', color: 'white', padding: '20px' }}>
        <Routes>
          <Route path="/" element={
          <div style={{textAlign: 'center'}}>
            <h1>Welcome to </h1>
            <img src={Logo} alt="ALTF4Hub Logo" className="logo-img" />
              </div>
            }  
          />
          <Route path="/LeaveTeam" element={<LeaveTeam />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/blogposts" element={<BlogPostFrontPage />} />
          <Route path="/createblogpost" element={<CreateBlogPost />} />
          <Route path="/drafts" element={<Drafts />} />
        </Routes>
      </main>
    </>
  );
}

export default App;
