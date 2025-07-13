# EasyCS API Documentation

Base URL: `https://<your-backend-domain>`

---

## POST /user

Register a new user or check if a name is already taken.

**Request body:**
```json
{
  "name": "ziv",
  "application": "MyApp",
  "company": "Mastercard"
}
```

**Response:**
- `200 OK` — name is accepted and user is registered
- `409 Conflict` — name already exists

---

## POST /message

Send a message from a user or a CS representative.

**Request body:**
```json
{
  "sender": "user",                      // or "cs"
  "name": "ziv",
  "application": "MyApp",
  "company": "Mastercard",
  "content": "Hi, I need help."
}
```

**Response:**
- `200 OK` — message was saved
- `400 Bad Request` — missing or invalid fields

---

## GET /messages/<application>/<company>

Retrieve all messages for a specific app and company.

**Example:**
```
GET /messages/MyApp/Mastercard
```

**Response:**
```json
[
  {
    "sender": "user",
    "name": "ziv",
    "content": "Hi, I need help.",
    "timestamp": "2025-07-13T10:45:00Z"
  },
  {
    "sender": "cs",
    "name": "Ziv from Mastercard",
    "content": "How can I assist you?",
    "timestamp": "2025-07-13T10:46:12Z"
  }
]
```

---

## Notes

- All requests and responses use `application/json` content type.
- Message routing is based on both `application` and `company` values.
- Timestamps are in ISO 8601 format.
