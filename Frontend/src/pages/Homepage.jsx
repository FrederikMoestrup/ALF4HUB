import Logo from "../assets/ALTF4Hub.png";

const Homepage = () => {
  return (
    <div style={{ textAlign: "center" }}>
      <h1>Welcome to </h1>
      <img src={Logo} alt="ALTF4Hub Logo" className="logo-img" />
    </div>
  );
};

export default Homepage;
