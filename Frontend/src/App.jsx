import { Routes, Route } from "react-router-dom";
import BlogPostFrontPage from "./pages/BlogPostFrontPage.jsx";
import CreateBlogPost from "./pages/CreateBlogPost.jsx";
import Drafts from "./pages/Drafts.jsx";

function App() {
  return (
    <div>
      <Routes>
        <Route path="/blogposts" element={<BlogPostFrontPage />} />
        <Route path="/createblogpost" element={<CreateBlogPost />} />
        <Route path="/drafts" element={<Drafts />} />
      </Routes>
    </div>
  );
}

export default App;
