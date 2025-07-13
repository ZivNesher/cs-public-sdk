import React, { useEffect, useState } from "react";

function Dashboard({ identity, onSelectUser }) {
  const [userIds, setUserIds] = useState([]);

  useEffect(() => {
    if (!identity) return;

    const { company, app } = identity;
    const url = `http://localhost:5000/users?company=${encodeURIComponent(company)}&app=${encodeURIComponent(app)}`;

    fetch(url)
      .then(res => res.json())
      .then(data => {
        if (Array.isArray(data)) {
          setUserIds(data);
        } else {
          console.error("Unexpected data format:", data);
        }
      })
      .catch(err => console.error("Failed to load users", err));
  }, [identity]);

  return (
    <div style={styles.container}>
      <h2>Conversations for {identity.company} / {identity.app}</h2>
      {userIds.length === 0 ? (
        <p>No users yet.</p>
      ) : (
        <ul style={styles.list}>
          {userIds.map((id) => (
            <li key={id} style={styles.item}>
              <button onClick={() => onSelectUser(id)} style={styles.button}>
                Chat with {id}
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

const styles = {
  container: {
    padding: "20px",
  },
  list: {
    listStyleType: "none",
    padding: 0,
  },
  item: {
    marginBottom: "10px",
  },
  button: {
    padding: "10px",
    fontSize: "16px",
    cursor: "pointer",
  },
};

export default Dashboard;
