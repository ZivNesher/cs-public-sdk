from flask import Flask, g
from flask_cors import CORS
from dotenv import load_dotenv
from pymongo import MongoClient
import os

from .config import Config

def create_app():
    # Load environment variables from .env
    load_dotenv()

    # Create the Flask app
    app = Flask(__name__)
    CORS(app)

    # Connect to MongoDB using URI from Config
    mongo_client = MongoClient(Config.MONGO_URI)
    db = mongo_client[Config.DB_NAME]
    app.db = db

    # Make the DB accessible in each request via `g`
    @app.before_request
    def before_request():
        g.db = db

    # ✅ Register routes AFTER app is created
    from .routes import chat_bp
    app.register_blueprint(chat_bp)

    return app
