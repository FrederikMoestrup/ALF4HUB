import styled from "styled-components";

export const Container = styled.div`
  height: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;

  @media (min-width: 1024px) {
    max-width: 800px;
    margin: 0 auto;
  }
`;

export const TitleWrapper = styled.div`
  display: flex;
  justify-content: center; 
  align-items: center;      
  flex-direction: column;
  width: 100%;
`;

export const Title = styled.h2`
  font-size: 19px;
  text-align: center;
`;

export const FormWrapper = styled.div`
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 250px;
  height: 400px;
  background-color: #12141b;
  border-radius: 8px;
  transition: all 0.3s ease;

  @media (min-width: 480px) {
    width: 300px;
    height: 450px;
  }

  @media (min-width: 768px) {
    width: 400px;
    height: 500px;
  }

  @media (min-width: 1024px) {
    width: 500px;
    height: 550px;
  }
`;

export const Form = styled.form`
  flex: 1;
  width: 80%;
  margin-top: 10px;
  display: flex;
  flex-direction: column;
`;

export const Label = styled.label`
  font-weight: bold;
  text-align: left;
`;

export const Input = styled.input`
  padding: 0.5rem;
  font-size: 1rem;
  border-radius: 4px;
  width: 100%;
  box-sizing: border-box;
  margin-bottom: 10px;

  @media (min-width: 768px) {
    margin-bottom: 30px;
  }
`;

export const Textarea = styled.textarea`
  padding: 0.5rem;
  font-size: 1rem;
  border-radius: 4px;
  height: 180px;
  box-sizing: border-box;
  resize: none
`;

export const ButtonsContent = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  gap: 20px;

  @media (min-width: 480px) {
    gap: 30px;
  }

  @media (min-width: 768px) {
    gap: 50px;
  }
`;

export const Button = styled.button`
  margin-top: 10px;
  width: 100px;
  height: 35px;
  font-size: 0.7em;
  background-color: #273b40;
  color: white;
  border-radius: 4px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
  transition: transform 0.3s, box-shadow 0.3s;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;

  @media (min-width: 768px) {
    margin-top: 35px;
    width: 150px;
    height: 60px;
    font-size: 1em;
  }
`;

export const RequiredText = styled.p`
  margin-top: 8px;
  margin-bottom: 5px;
  font-size: 0.5rem;

  @media (min-width: 768px) {
    font-size: 0.7rem;
    margin-bottom: 10px;
  }

  @media (min-width: 1024px) {
    font-size: 0.9rem;
    margin-bottom: 15px;
  }
`;