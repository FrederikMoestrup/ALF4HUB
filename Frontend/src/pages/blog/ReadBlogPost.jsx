import { useEffect, useState } from "react";
import { useParams, NavLink } from "react-router-dom";

const ReadBlogPost = () => {
  const { postId } = useParams();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:7070/api/blogpost/${postId}`)
            .then((res) => {
                if (!res.ok) throw new Error("Failed to fetch blog post");
                return res.json();
            })
            .then((data) => {
                setPost(data);
            })
            .catch((err) => {
                setError(err.message);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [postId]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;
  if (!post) return  <p>Post not found.</p>;

  return (
    <div style={{ padding: "20px" }}>
      <NavLink to="/blog/posts">
        <button>← Back to Blogs Frontpage</button>
      </NavLink>
      <h1>{post.title}</h1>
      <small>Posted on {post.createdAt}</small>
      <p style={{ marginTop: "20px" }}>{post.content}</p>
    </div>
  );
};

export default ReadBlogPost;
