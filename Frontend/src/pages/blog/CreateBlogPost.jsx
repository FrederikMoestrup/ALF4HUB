import { useState } from "react";
import PopUpMessage from "../../components/PopUpMessage";
import { 
  Container,
  TitleWrapper,
  Title,
  FormWrapper,
  Form,
  Label,
  Input,
  Textarea,
  ButtonsContent,
  Button,
  RequiredText,
 } from "./styles/createBlogPostStyles";
import GlobalStyle from "../../styles/GlobalStyles";

function CreateBlogPost() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
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
      <GlobalStyle/>
      <Container>
        <TitleWrapper>
          <Title>Create A New Blog Post</Title>
        </TitleWrapper>
        <FormWrapper>
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

            <ButtonsContent>
              <Button
                type="submit"
                onClick={() => setSubmitType("draft")}
              > Save as Draft
              </Button>
              <Button
                type="submit"
                onClick={() => setSubmitType("published")}
              > Post
              </Button>
            </ButtonsContent>
          </Form>
          <RequiredText>
                * Title and content are required fields and must not be left
                blank.
              </RequiredText>
        </FormWrapper>
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