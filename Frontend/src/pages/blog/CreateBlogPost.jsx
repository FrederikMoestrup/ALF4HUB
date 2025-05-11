import { useState } from "react";
import { NavLink } from "react-router-dom";
import PopUpMessage from "../../components/PopUpMessage";
import {
  GlobalStyle,
  Container,
  Navbar,
  HomeButton,
  ProfileButton,
  NavLinks,
  Content,
  FormWrapper,
  FormTitle,
  Form,
  Label,
  Input,
  Textarea,
  ButtonsContent,
  Button,
  RequiredText,
  Footer,
} from "../../styles/createBlogPostStyles";

function CreateBlogPost() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tags, setTags] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const [submitType, setSubmitType] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");
    setSuccess(false);

    if (!title || !content) {
      setError("Title and content are required!");
      setSuccess(false);
      return;
    }

    const endpoint = submitType === "draft" ? "/blogpost/draft" : "/blogpost";

    const payload = {
      userId: 1, // hardcoded until login works
      title,
      content,
      status: submitType.toUpperCase(),
    };

    try {
      const response = await fetch("http://localhost:7070/api" + endpoint, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        const errorData = await response.json();
        setError(errorData.warning);
        return;
      }

      setSuccess(true);
      setTitle("");
      setContent("");
      setTags("");
    } catch (err) {
      setError(err.message);
      setSuccess(false);
    }
  };

  const getSuccessMessage = () => {
    if (submitType === "draft") {
      return "Blog post saved as draft successfully!";
    } else if (submitType === "published") {
      return "Blog post created successfully!";
    }
    return "";
  };

  return (
    <>
      <GlobalStyle />
      <Container>
        <Navbar>
          <HomeButton>
            <a href="/">Home</a>
          </HomeButton>

          <NavLinks>
            <NavLink to="/teams">Teams</NavLink>
            <NavLink to="/tournaments">Tournaments</NavLink>
            <NavLink to="/blogposts">Blog</NavLink>
          </NavLinks>

          <ProfileButton>
            <a href="/">Profile</a>
          </ProfileButton>
        </Navbar>

        <Content>
          <FormWrapper>
            <h2>Create a New Blog Post</h2>

            <Form onSubmit={handleSubmit}>
              <Label>Title*</Label>
              <Input
                type="text"
                placeholder="Write your title here"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />

              <Label>Content*</Label>
              <Textarea
                placeholder="Write your blog post here... Share your thoughts, insights, or experiences in detail but be mindful of one another."
                value={content}
                onChange={(e) => setContent(e.target.value)}
                required
              />

              <Label>Tags (optional)</Label>
              <Input
                type="text"
                placeholder="e.g., React, Tournament, Gaming"
                value={tags}
                onChange={(e) => setTags(e.target.value)}
              />

              <ButtonsContent>
                <Button type="submit" onClick={() => setSubmitType("draft")}>
                  Save as Draft
                </Button>
                <Button
                  type="submit"
                  onClick={() => setSubmitType("published")}
                >
                  Create Post
                </Button>
              </ButtonsContent>
              <RequiredText>
                *Title and content are required fields and must not be left
                blank.
              </RequiredText>
            </Form>
          </FormWrapper>
        </Content>

        <Footer>
          <p>
            © Altf4hub | Firskovvej 18 2800 Lyngby | CVR-nr. 10101010 | Tlf: 36
            15 45 04 | Mail: turnering@altf4hub.dk
          </p>
        </Footer>
      </Container>

      <PopUpMessage
        isOpen={error !== "" || success}
        message={error || getSuccessMessage()}
        onClose={() => {
          setError("");
          setSuccess(false);
        }}
        type={error ? "error" : "success"}
      />
    </>
  );
}

export default CreateBlogPost;
