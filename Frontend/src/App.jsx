import { Routes, Route } from "react-router-dom";
import BlogPostFrontPage from "./pages/BlogPostFrontPage.jsx";
import CreateBlogPost from "./pages/CreateBlogPost.jsx";

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<BlogPostFrontPage />} />
        <Route path="/createblogpost" element={<CreateBlogPost />} />
      </Routes>
    </div>
  );
}

export default App;
