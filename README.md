# EasyCS Chat SDK

**EasyCS Chat SDK** is a complete customer-support solution for Android applications, offering a floating chat bubble for users, a real-time backend, and a modern web dashboard for support associates.

---

## CS Associate UI
<img width="1276" height="826" alt="Screenshot 2025-07-13 at 14 21 48" src="https://github.com/user-attachments/assets/fa67b475-479d-4c0a-bbc0-a58755a27bc8" />
<img width="1276" height="826" alt="Screenshot 2025-07-13 at 14 22 01" src="https://github.com/user-attachments/assets/13b64ac4-25bb-424c-859f-ac36301269c1" />
<img width="1276" height="821" alt="Screenshot 2025-07-13 at 14 21 41" src="https://github.com/user-attachments/assets/5a7ea069-9cb6-4844-b986-8033f7853a71" />

## Demo App UI  
*The bubble floats and can be dragged by the user.*

<img width="322" height="707" alt="Screenshot 2025-07-13 at 14 29 30" src="https://github.com/user-attachments/assets/e153daf5-a951-4510-9f6a-7e62b316183a" />
<img width="327" height="705" alt="Screenshot 2025-07-13 at 14 29 36" src="https://github.com/user-attachments/assets/32887495-6596-4030-bb2f-6038cf6d9017" />


## What’s Included?

- **Android SDK** (in `APP/`)
- **Backend API** (`API/easycs-backend/`, Flask + MongoDB)
- **Web Dashboard** (`WEB/`, React)

---

## Features

### Android SDK (`APP`)
- Draggable, always-on-top chat bubble
- User prompted for unique name on first use
- Real-time chat via REST API
- Routes messages by company and app name
- Clean auto-scrolling chat UI

### Backend API (`easycs-backend`)
- Flask-based REST API
- MongoDB Atlas or local MongoDB support
- Tracks conversations per user/app/company
- Clean architecture with `.env` configuration

### Web Dashboard (`WEB`)
- React-based UI
- Login with company name and CS agent email
- View/respond to open user conversations
- Shows agent identity (e.g., “Ziv from Mastercard”)

---

## Project Structure

```
easycs-chat-sdk/
│
├── WEB/              # React web dashboard for support agents
├── APP/    # Android demo app using the EasyCS SDK
├── API/     # Flask REST API backend with MongoDB integration
└── README.md           # Project documentation
```

---

## Getting Started

### 1. Backend API (`easycs-backend`)

```bash
cd easycs-backend/
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
```

Create a `.env` file:
```env
MONGO_URI=mongodb+srv://<username>:<password>@cluster.mongodb.net/?retryWrites=true&w=majority&appName=easycs
DB_NAME=easycs
```

Run the server:
```bash
python run.py
```

---

### 2. Android App (`APP`)

Open the project in Android Studio.

Inside `MainActivity.java`:
```java
EasyCSChatSDK.init(this, userName); // Ensure userName is unique
EasyCSChatSDK.showBubble();
```

Make sure in `ChatActivity.java`:
```java
String backendUrl = "http://10.0.2.2:5000"; // Use proper IP if testing on real device
```

---

### 3. Web Dashboard (`WEB`)

```bash
cd WEB/cs-panel
npm install
npm start
```

Open your browser at:
```
http://localhost:3000
```

---

## API Overview

```http
POST /messages
# → Send message from user or CS

GET /messages/<userId>?company=...&app=...
# → Fetch all messages for a user within a company/app

GET /users?company=...&app=...
# → List all active userIds for a company/app
```

---

## How It Works

1. User opens app → prompted for name
2. Bubble appears → user sends message
3. Message is stored in MongoDB with `userId`, `company`, `app`
4. CS logs into dashboard → sees chats in real time
5. Replies go back through the API to the app instantly

---

## Roadmap

- [ ] Push notifications (FCM/WebSockets)
- [ ] File upload & image preview
- [ ] Typing indicators
- [ ] Online/offline status for CS
- [ ] Chat rating after session ends

---

## License

MIT License.  
See [LICENSE](LICENSE) for full terms.

---

## Contribution

We welcome all contributions!  
Fork this repo, create a new branch, and open a PR when ready.

---
