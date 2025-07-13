# EasyCS Chat SDK

<img width="1276" height="826" alt="Screenshot 2025-07-13 at 14 21 48" src="https://github.com/user-attachments/assets/fa67b475-479d-4c0a-bbc0-a58755a27bc8" />
<img width="1276" height="826" alt="Screenshot 2025-07-13 at 14 22 01" src="https://github.com/user-attachments/assets/13b64ac4-25bb-424c-859f-ac36301269c1" />
<img width="1276" height="821" alt="Screenshot 2025-07-13 at 14 21 41" src="https://github.com/user-attachments/assets/5a7ea069-9cb6-4844-b986-8033f7853a71" />


<img width="322" height="707" alt="Screenshot 2025-07-13 at 14 29 30" src="https://github.com/user-attachments/assets/e153daf5-a951-4510-9f6a-7e62b316183a" />
<img width="327" height="705" alt="Screenshot 2025-07-13 at 14 29 36" src="https://github.com/user-attachments/assets/32887495-6596-4030-bb2f-6038cf6d9017" />



**EasyCS Chat SDK** is a complete customer support solution for Android applications. It provides a floating in-app chat bubble that allows users to initiate real-time conversations with customer support agents. The system includes:

- **Android SDK** (via `EasyCSSampleApp`)
- **Backend API** (`easycs-backend`, Flask + MongoDB)
- **Web Dashboard** for support agents (`CSweb`, React)

---

## Features

### Android SDK (via `EasyCSSampleApp`)
- Displays a floating support chat bubble
- Initiates and maintains real-time chat sessions
- Sends and receives messages through a REST API
- User name validation on first use
- Auto-scroll to latest message

### Backend API (`easycs-backend`)
- Flask-based RESTful service
- MongoDB Atlas integration
- Handles messages, users, and routing by `companyName` and `applicationName`
- Deployed on a cloud provider (e.g., Koyeb, Vercel)

### Web Dashboard (`CSweb`)
- Login with company name and email
- View all open conversations for the company
- Real-time message display and reply interface
- Message sender is clearly identified (e.g., "Ziv from Mastercard")

---

## Project Structure

```
easycs-chat-sdk/
│
├── CSweb/              # React web dashboard for customer support agents
│
├── EasyCSSampleApp/    # Android demo app using the EasyCS SDK
│
├── easycs-backend/     # Flask REST API backend with MongoDB integration
│
└── README.md           # Project documentation
```

---

## Getting Started

### 1. Android App (`EasyCSSampleApp`)

#### Add the SDK to your app (if split in future as a separate module)

If the SDK is published via JitPack:

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.ZivNesher:easycs-chat-sdk:main-SNAPSHOT'
}
```

#### Initialize in `MainActivity.java`:

```java
EasyCSChatBubble.initialize(
    this,
    "YourAppName",
    "YourCompanyName"
);
```

---

### 2. Backend API (`easycs-backend`)

#### Setup

```bash
cd easycs-backend/
pip install -r requirements.txt
```

#### Run locally

```bash
python app.py
```

Make sure to configure your `.env` file with the MongoDB URI and other environment variables.

---

### 3. Web Dashboard (`CSweb`)

#### Setup

```bash
cd CSweb/
npm install
npm start
```

- Opens at `http://localhost:3000`
- CS agents are prompted for company name and email on login

---

## API Overview

- `POST /message`: send message from user or CS
- `GET /messages/<app>/<company>`: fetch all messages for a given application and company
- `POST /user`: create new user with unique name validation
- Full documentation available in `easycs-backend/docs` (optional)

---

## How It Works

1. User clicks the EasyCS chat bubble in your Android app
2. Enters a display name (must be unique)
3. Messages are sent to the backend and stored in MongoDB
4. CS agents see incoming messages in the web dashboard
5. Real-time 2-way communication is established

---

## Roadmap

- Push notification support
- File attachments
- Typing indicators
- CS availability status
- Support session ratings

---

## License

This project is licensed under the MIT License.  
See the [LICENSE](LICENSE) file for details.

---

## Contribution

We welcome contributions, issues, and pull requests. Please fork the repository and submit your changes via a pull request.
