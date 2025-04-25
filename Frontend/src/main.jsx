import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import BlogPostFrontPage from './pages/BlogPostFrontPage.jsx'
import CreateBlogPost from './pages/CreateBlogPost.jsx'
//import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
   {/* <App />*/}
   {/* <BlogPostFrontPage /> */}
   <CreateBlogPost />
  </StrictMode>,
)
