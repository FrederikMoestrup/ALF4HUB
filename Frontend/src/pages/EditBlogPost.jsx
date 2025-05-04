import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

const EditBlogPost = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [blogPost, setBlogPost] = useState({
    title: '',
    content: '',
    status: 'DRAFT',
  });

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetch(`http://localhost:7070/api/v1/blog_post/${id}`, {
      credentials: 'include',
    })
      .then(res => {
        if (!res.ok) throw new Error('Failed to load blog post');
        return res.json();
      })
      .then(data => {
        setBlogPost({
          title: data.title,
          content: data.content,
          status: data.status,
        });
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, [id]);

  const handleChange = e => {
    const { name, value } = e.target;
    setBlogPost(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async e => {
    e.preventDefault();

    try {
      const res = await fetch(`http://localhost:7070/api/v1/blog_post/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(blogPost),
      });

      if (!res.ok) {
        const errData = await res.text();
        throw new Error(errData || 'Update failed');
      }

      navigate('/blogposts');
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <form onSubmit={handleSubmit}>
      <h2>Edit Blog Post</h2>

      <label>Title:</label>
      <input
        type="text"
        name="title"
        value={blogPost.title}
        onChange={handleChange}
        required
      />

      <label>Content:</label>
      <textarea
        name="content"
        rows={10}
        value={blogPost.content}
        onChange={handleChange}
        required
      />

      <label>Status:</label>
      <select name="status" value={blogPost.status} onChange={handleChange}>
        <option value="DRAFT">DRAFT</option>
        <option value="READY">READY</option>
        <option value="PUBLISHED">PUBLISHED</option>
      </select>

      <button type="submit">Update</button>
    </form>
  );
};

export default EditBlogPost;
