import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import { createBrowserRouter, RouterProvider } from 'react-router'; 
import TestPage from './pages/TestPage.jsx'; 


const router = createBrowserRouter([
  {
    path: "/",
    element: <TestPage />, 
  },
]);


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
