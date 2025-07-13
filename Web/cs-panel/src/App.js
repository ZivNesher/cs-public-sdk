import React, { useState } from "react";
import LoginScreen from "./LoginScreen";
import Dashboard from "./Dashboard";
import ChatView from "./ChatView";

function App() {
  const [identity, setIdentity] = useState(null);
  const [selectedUser, setSelectedUser] = useState(null);

  if (!identity) {
    return <LoginScreen onLogin={setIdentity} />;
  }

  if (selectedUser) {
    return (
      <ChatView
        userId={selectedUser}
        identity={identity}
        onBack={() => setSelectedUser(null)}
      />
    );
  }

  return (
    <Dashboard
      identity={identity}
      onSelectUser={setSelectedUser}
    />
  );
}

export default App;
