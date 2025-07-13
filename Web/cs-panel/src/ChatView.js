import React, { useEffect, useState } from "react";

function ChatView({ userId, identity, onBack }) {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");

  const backendUrl = "http://localhost:5000";
  const refreshInterval = 3000;

  const loadMessages = () => {
    const { company, app } = identity;
    const url = `${backendUrl}/messages/${userId}?company=${encodeURIComponent(company)}&app=${encodeURIComponent(app)}`;

    fetch(url)
      .then(res => res.json())
      .then(data => setMessages(data || []))
      .catch(err => console.error("Failed to fetch messages", err));
  };

  const sendMessage = () => {
    if (!input.trim()) return;

    const sender = `${identity.name} from ${identity.company}`;
    const body = {
      userId,
      company: identity.company,
      app: identity.app,
      sender,
      text: input,
    };

    fetch(`${backendUrl}/messages`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    })
      .then((res) => {
        if (res.ok) {
          setInput("");
          loadMessages();
        }
      })
      .catch(err => console.error("Failed to send message", err));
  };

  useEffect(() => {
    loadMessages();
    const interval = setInterval(loadMessages, refreshInterval);
    return () => clearInterval(interval);
  }, [userId, identity]);

  return (
    <div style={styles.container}>
      <button onClick={onBack} style={styles.backButton}>← Back</button>
      <h2>Chat with <span style={{ color: "#1976D2" }}>{userId}</span></h2>

      <div style={styles.chatBox}>
        {messages.map((msg, index) => (
          <div
          key={index}
          style={{
            ...styles.message,
            alignSelf: msg.sender === "client" ? "flex-start" : "flex-end",
            backgroundColor: msg.sender === "client" ? "#eeeeee" : "#cce5ff",
          }}
        >
          <strong>{msg.sender}</strong><br />
          {msg.text}
        </div>
        
        ))}
      </div>

      <div style={styles.inputBox}>
        <input
          style={styles.input}
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="Type a reply..."
        />
        <button onClick={sendMessage} style={styles.sendButton}>Send</button>
      </div>
    </div>
  );
}

const styles = {
    container: {
      padding: "20px",
      maxWidth: "640px",
      margin: "0 auto",
      display: "flex",
      flexDirection: "column",
      fontFamily: "sans-serif",
    },
    backButton: {
      marginBottom: "15px",
      fontSize: "16px",
      cursor: "pointer",
      padding: "6px 14px",
      backgroundColor: "#f2f2f2",
      border: "1px solid #ccc",
      borderRadius: "5px"
    },
    chatBox: {
      border: "1px solid #ddd",
      backgroundColor: "#fafafa",
      padding: "12px",
      flex: "1",
      overflowY: "auto",
      display: "flex",
      flexDirection: "column",
      gap: "10px",
      height: "400px",
      marginBottom: "10px",
      borderRadius: "6px",
    },
    message: {
      padding: "10px 14px",
      borderRadius: "20px",
      maxWidth: "70%",
      whiteSpace: "pre-wrap",
      fontSize: "15px",
      lineHeight: "1.4",
      boxShadow: "0 1px 3px rgba(0,0,0,0.1)",
    },
    inputBox: {
      display: "flex",
      gap: "10px",
      paddingTop: "10px",
      borderTop: "1px solid #ddd"
    },
    input: {
      flex: 1,
      padding: "10px",
      fontSize: "16px",
      borderRadius: "6px",
      border: "1px solid #ccc",
    },
    sendButton: {
      padding: "10px 20px",
      fontSize: "16px",
      backgroundColor: "#1976D2",
      color: "#fff",
      border: "none",
      borderRadius: "6px",
      cursor: "pointer"
    },
  };  

export default ChatView;
