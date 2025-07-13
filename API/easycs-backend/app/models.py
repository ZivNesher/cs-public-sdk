import datetime

def create_user(user_id):
    return {
        "userId": user_id,
        "createdAt": datetime.datetime.utcnow()
    }

def create_message(user_id, text, sender="client"):
    return {
        "userId": user_id,
        "sender": sender,  # "client" or "cs"
        "text": text,
        "timestamp": datetime.datetime.utcnow()
    }
