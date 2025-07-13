import React, { useState } from "react";

function LoginScreen({ onLogin }) {
  const [name, setName] = useState("");
  const [company, setCompany] = useState("");
  const [app, setApp] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name || !company || !app) return;

    const identity = {
      name,
      company,
      app
    };

    onLogin(identity);
  };

  return (
    <div style={styles.container}>
      <h2>Customer Support Login</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <input
          style={styles.input}
          type="text"
          placeholder="Your name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          style={styles.input}
          type="text"
          placeholder="Company name"
          value={company}
          onChange={(e) => setCompany(e.target.value)}
        />
        <input
          style={styles.input}
          type="text"
          placeholder="App name"
          value={app}
          onChange={(e) => setApp(e.target.value)}
        />
        <button style={styles.button} type="submit">Continue</button>
      </form>
    </div>
  );
}

const styles = {
  container: {
    marginTop: "100px",
    textAlign: "center",
  },
  form: {
    display: "inline-block",
    marginTop: "20px",
  },
  input: {
    display: "block",
    marginBottom: "10px",
    padding: "10px",
    fontSize: "16px",
    width: "250px",
  },
  button: {
    padding: "10px 20px",
    fontSize: "16px",
  },
};

export default LoginScreen;
