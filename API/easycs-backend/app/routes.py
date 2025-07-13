from flask import Blueprint, request, jsonify, g
from datetime import datetime

chat_bp = Blueprint("chat", __name__)

@chat_bp.route("/messages", methods=["POST"])
def post_message():
    data = request.json
    user_id = data.get("userId")
    sender = data.get("sender")
    text = data.get("text")
    company = data.get("company")
    app_name = data.get("app")

    if not (user_id and sender and text and company and app_name):
        return jsonify({"error": "Missing fields"}), 400

    message = {
        "sender": sender,
        "text": text,
        "timestamp": datetime.utcnow().isoformat()
    }

    g.db["messages"].update_one(
        {"userId": user_id, "company": company, "app": app_name},
        {"$push": {"chat": message}, "$setOnInsert": {"company": company, "app": app_name}},
        upsert=True
    )

    return jsonify({"message": "Message sent"}), 200


@chat_bp.route("/users", methods=["GET"])
def list_users():
    company = request.args.get("company")
    app_name = request.args.get("app")

    if not company or not app_name:
        return jsonify({"error": "Missing company or app"}), 400

    user_ids = g.db["messages"].find(
        {"company": company, "app": app_name},
        {"userId": 1}
    )
    return jsonify([doc["userId"] for doc in user_ids])



@chat_bp.route("/messages/<user_id>", methods=["GET"])
def get_messages(user_id):
    company = request.args.get("company")
    app_name = request.args.get("app")

    if not company or not app_name:
        return jsonify({"error": "Missing company or app"}), 400

    user_chat = g.db["messages"].find_one({
        "userId": user_id,
        "company": company,
        "app": app_name
    })

    if not user_chat or "chat" not in user_chat:
        return jsonify([])

    return jsonify(user_chat["chat"])

