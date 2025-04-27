import styled, { createGlobalStyle } from 'styled-components';
/*import { useState } from 'react-router-dom';*/

/* Should fill the entire screen, doesn't right now. Needs to be fixed */
const GlobalStyle = createGlobalStyle`
  html, body, #root{
    margin: 0;
    padding: 0;
    height: 100%;
    box-sizing: border-box;
    font-family: 'Segoe UI', sans-serif;
    color: pink;
  }
`;

const Container = styled.div`
    display: flex;
    flex-direction: column;
    min-height: 100vh;
`;

/* Top navbar */
const Navbar = styled.nav`
  position: relative;
  background-color: #ebecf8;
  border-bottom: 1px solid #cccccc;
  padding: 1rem 2rem;
  display: flex;
  justify-content: center;   /* Center NavLinks in the middle */
  align-items: center;
`;

const HomeButton = styled.div`
  position: absolute;
  left: 2rem;

  a {
    outline: none;
    color: black;
    font-size: 1rem;
    padding: 8px 10px;
    font-weight: bold;
    border-radius: 4px;
    transition: background-color 0.3s ease, color 0.3s ease;
    text-decoration: none;

    &:hover {
      background-color: white;
      color: #000;
    }
  }
`;

const ProfileButton = styled(HomeButton)`
  right: 2rem;  /* Position on the opposite corner */
  left: unset;  /* Make sure left is not applied */
`;

const NavLinks = styled.div`
  display: flex;
  gap: 2rem;

  a {
    text-decoration: none;
    outline: none;
    color: black;
    font-size: 1rem;
    padding: 8px 10px;
    font-weight: bold;
    border-radius: 4px;
    transition: background-color 0.3s ease, color 0.3s ease;

    &:hover {
      background-color: white;
      color: #000;
    }
  }
`;

 /* Main part of the page */
const Content = styled.main`
  flex: 1;
  display: flex;
  justify-content: center;  /* Center the form horizontally */
  align-items: flex-start; 
  padding: 2rem;
  gap: 2rem;
  background-color: #ebecf8;
`;

/*const BackButton = styled.button`
  position: absolute;
  top: 2rem;
  left: 2rem;
  background: none;
  border: none;
  font-size: 2rem;
  color: black;
  cursor: pointer;

  &:hover {
    opacity: 0.7;
  }
`;*/

// Styled components for the form
const FormWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

const FormTitle = styled.h2`
  text-align: center;
  margin-bottom: 1rem;
  color: black;
  margin-top: -0.3rem;
  `;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  background-color: #ebecf8;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  max-width: 800px;  
  width: 100%;
`;

const Label = styled.label`
  color: black;
  font-weight: bold;
  text-align: left;
  margin-bottom: -1rem;
`;

const Input = styled.input`
  padding: 0.75rem;
  font-size: 1rem;
  border-radius: 4px;
  border: 1px solid rgb(0, 42, 255);
`;

const Textarea = styled.textarea`
  padding: 0.75rem;
  font-size: 1rem;
  border-radius: 4px;
  border: 1px solid rgb(0, 42, 255);
  height: 200px;
`;

const Button = styled.button`
  padding: 0.75rem;
  font-size: 1rem;
  background-color: transparent;
  border: 1px solid rgb(0, 42, 255);
  color: black;
  border-radius: 4px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
  transition: transform 0.3s, box-shadow 0.3s;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;

  &:hover {
    background-color: rgb(0, 42, 255);
  }
`;

const RequiredText = styled.p`
  font-size: 0.875rem;
  color: black;
  align-self: flex-start;
`;

const Footer = styled.footer`
  background-color: #ebecf8;
  color: #333;
  padding: 1rem 2rem;
  flex-shrink: 0;
`;

function CreateBlogPost() {
   /*Doesn't work, since BrowserRouter is not yet set up in the main.jsx file
   
   const navigate = useNavigate();

    const goBack = () => {
        navigate(-1);  // This will take the user to the previous page
    };

    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [tags, setTags] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);

    // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    if (!title || !content) {
      setError('Title and content are required!');
      setSuccess(false);
    } else {
      setError('');
      setSuccess(true);
      console.log('Blog post created:', { title, content, tags });
      // Here you would send the data to the backend or store it
    }
  };*/

    return (
        <>
        <GlobalStyle />
        <Container>
            <Navbar>
            <HomeButton>
                <a href="/">Home</a>
            </HomeButton>

            {/* a href should be changed with NavLink to the respective pages, when these are ready */}
            <NavLinks>
                <a href="/teams">Teams</a>
                <a href="/tournaments">Tournaments</a>
                <a href="/blog">Blog</a>
            </NavLinks>

            <ProfileButton>
                <a href="/">Profile</a>
            </ProfileButton>
            </Navbar>
            <Content>
                <FormWrapper>
                {/*<BackButton onClick={goBack}>←</BackButton>
                <h2>Create a New Blog Post</h2>
                    <Form onSubmit={handleSubmit}>
                        <Input
                        type="text"
                        placeholder="Title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        />
                        <Textarea
                        placeholder="Content"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        />
                        <Input
                        type="text"
                        placeholder="Tags (optional)"
                        value={tags}
                        onChange={(e) => setTags(e.target.value)}
                        />
                        {error && <p style={{ color: 'red' }}>{error}</p>}
                        {success && <p style={{ color: 'green' }}>Blog post created successfully!</p>}
                        <Button type="submit">Create Post</Button>
                    </Form>*/}
                    <FormTitle>Create a new blog post</FormTitle>
                    <Form>
                        <Label>Title*</Label>
                        <Input
                        type="text"
                        placeholder="Write your title here"
                        />
                        <Label>Content*</Label>
                        <Textarea
                        type="text"
                        placeholder="Write your blog post here... Share your thoughts, insights, or experiences in detail but be mindful of one another."
                        />
                        
                        <Button type="submit">Create post</Button>
                        <RequiredText>*Title and content are required fields and most not be left blank.</RequiredText>
                    </Form>
                </FormWrapper>
            </Content>

            <Footer>
                <p>© Altf4hub | Firskovvej 18 2800 Lyngby | CVR-nr. 10101010 | Tlf: 36 15 45 04 | Mail: turnering@altf4hub.dk</p>
            </Footer>
        </Container>
        </>
    );
}

export default CreateBlogPost;