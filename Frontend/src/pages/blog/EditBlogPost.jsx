import { useEffect, useState } from "react";
import PopUpMessage from "../../components/PopUpMessage";
import apiFacade  from "../../util/apiFacade"
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
import { useLocation } from "react-router-dom";

const EditBlogPost = () => {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);
    const [submitType, setSubmitType] = useState("");
    const [censoredDraft, setCensoredDraft] = useState(null);
    const location = useLocation();
    const draft = location.state?.draft;

    useEffect(() => {
      if (draft) {
        setTitle(draft.title);
        setContent(draft.content);
      }
    }, [draft]);

  if (!draft) { return <p>No draft found.</p>; }

    const changeToCensoredText = async () => {
      const token = localStorage.getItem("token");
      const currentUserId = await apiFacade.getUserId();

      const payload = {
        userId: currentUserId,
        title,
        content,
        status: submitType.toUpperCase(),
      };

      try {
        const response = await fetch("http://localhost:7070/api/blogpost/draft/validate", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
            body: JSON.stringify(payload),
        });
  
        if (!response.ok) {
          const errorData = await response.json();
          setError(errorData.warning);
          return;
        }

        const data = await response.json();
        if (data.title != title || data.content != content) {
          setTitle(data.title);
          setContent(data.content);
          if (data.title != draft.title && data.content != draft.content) {
            setCensoredDraft(data);
          }
          setError("Blog post contains inappropriate content. Please edit it. All inappropriate content will be replaced with '#'.");
          return;
        }

      } catch (err) {
        setError(err.message);
      }
    }
  
    const handleSubmit = async (e) => {
      e.preventDefault();
  
      const token = localStorage.getItem("token");
      const currentUserId = await apiFacade.getUserId();
  
      if (!token || !currentUserId) {
        setError("You must be logged in.");
        return;
      }
  
      setError("");
      setSuccess(false);
  
      if (!title || !content) {
        setError("Title and content are required!");
        setSuccess(false);
        return;
      }
  
      const endpoint = submitType === "draft" ? "http://localhost:7070/api/blogpost/draft/update/" + draft.id : "http://localhost:7070/api/blogpost/draft/publish/" + draft.id;
  
      const payload = {
        userId: currentUserId,
        title,
        content,
        status: submitType.toUpperCase(),
      };

      if (censoredDraft != null) {
      if (title === censoredDraft.title && content === censoredDraft.content) {
        setError("No changes made to the draft.");
        setSuccess(false);
        return;
      }
      }

      try {
        const response = await fetch(endpoint, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        });
  
        if (!response.ok) {
          const errorData = await response.json();
          setError(errorData.warning);
          changeToCensoredText();
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
      <GlobalStyle />
            <Container>
              <TitleWrapper>
                <Title>Editing Draft: {draft.title}</Title>
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
                    <Button type="submit" onClick={() => setSubmitType("draft")}>
                      {" "}
                      Save as Draft
                    </Button>
                    <Button type="submit" onClick={() => setSubmitType("published")}>
                      {" "}
                      Post
                    </Button>
                  </ButtonsContent>
                </Form>
                <RequiredText>
                  * Title and content are required fields and must not be left blank.
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
};

export default EditBlogPost;
