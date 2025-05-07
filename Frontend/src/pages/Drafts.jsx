import { use, useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
    GlobalStyle, Container, Navbar, HomeButton, ProfileButton, NavLinks, Content, FormWrapper,
    FormTitle, Form, Label, Input, Textarea, ButtonsContent, Button, RequiredText, Footer,
  } from "../styles/createBlogPostStyles";
  import { BlogCard, ButtonContainer, BlogContainer, Section, SectionTitle, BlogSectionLeft, BlogSectionRight,
  } from "../styles/blogPostFrontPageStyles";

function Drafts() {
    const [drafts, setDrafts] = useState([]);

    const exampleBlogPosts = [
        {
          title: "Introduction to React",
          content: "React is a popular JavaScript library for building user interfaces. In this post, we'll explore the basics of React, including components, props, and state.",
          tags: "react,javascript,frontend",
          error: "",
          success: true,
          submitType: "create"
        },
        {
          title: "Mastering CSS Grid",
          content: "CSS Grid is a powerful layout system. We'll cover how to create responsive grid layouts and common use cases.",
          tags: "css,grid,layout",
          error: "",
          success: true,
          submitType: "create"
        },
        {
          title: "Understanding useEffect in React",
          content: "useEffect is essential for handling side effects in functional components. Learn how to use it correctly with dependencies.",
          tags: "react,hooks,useEffect",
          error: "",
          success: true,
          submitType: "create"
        },
        {
          title: "Node.js vs Deno: What’s the Difference?",
          content: "Both Node.js and Deno are JavaScript runtimes, but they have different philosophies and security models. Here's a comparison.",
          tags: "nodejs,deno,javascript",
          error: "",
          success: true,
          submitType: "create"
        },
        {
          title: "Async/Await Explained Simply",
          content: "Async/await makes asynchronous code easier to write and understand. This guide breaks it down for beginners.",
          tags: "javascript,async,promises",
          error: "",
          success: true,
          submitType: "create"
        },
        {
          title: "Top 5 VS Code Extensions for Web Developers",
          content: "VS Code is highly customizable. Here are 5 extensions every web developer should consider installing.",
          tags: "vscode,tools,productivity",
          error: "",
          success: true,
          submitType: "create"
        }
      ];

      const fetchDrafts = async () => {
        try {
            // const response = await fetch("http://localhost:7070/api/blogpost/draft", {
            const response = await fetch("http://localhost:7070/api/blogpost", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
            });
            if (!response.ok) {
                throw new Error("Failed to fetch blog posts");
            }
            const data = await response.json();
            setDrafts(data);
        } catch (error) {
            console.error("Error:", error);
        }
    }

    useEffect(() => {
        fetchDrafts(); // currently fails "on purpose"
        setDrafts(exampleBlogPosts);
    }, []);

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
            <BlogContainer>
                        <BlogSectionLeft>
                          <SectionTitle>Your unpublished drafts</SectionTitle>
                          {drafts.length < 1 ? <p Style="color: #3b3b3b;">No recent posts yet.</p> : null}
                          <ul Style="list-style-type: none; padding: 0;">
                          {
                            drafts.map((draft, index) => (
                              <div>
                            <li key={index} Style="border-bottom: 1px solid #9b9b9b; padding: 10px;">
                              <h2 Style="color: #3b3b3b;">{draft.title}</h2>
                              <p Style="color: #3b3b3b;">Tags: {draft.tags}</p>
                              <p Style="color: #3b3b3b;">{draft.content}</p>
                              </li>
                              </div>
                            ))}
                            </ul>
                        </BlogSectionLeft>
                      </BlogContainer>
            </FormWrapper>
           </Content>
   
           <Footer>
             <p>
               © Altf4hub | Firskovvej 18 2800 Lyngby | CVR-nr. 10101010 | Tlf: 36
               15 45 04 | Mail: turnering@altf4hub.dk
             </p>
           </Footer>
         </Container >
   
         {/* <PopUpMessage
           isOpen={error !== "" || success}
           message={error || getSuccessMessage()}
           onClose={() => {
             setError("");
             setSuccess(false);
           }}
           type={error ? "error" : "success"}
         /> */}
       </>
  );
}

export default Drafts;