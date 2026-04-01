# 🚀 Chat App (Android - Jetpack Compose)

A modern real-time chat application built using **Jetpack Compose** with Firebase integration. This app supports user authentication, real-time messaging, and push notifications.

---

## ✨ Features

* 🔐 **Firebase Authentication**
  User login with email & password

* 💬 **Realtime Chat**
  Send and receive messages instantly using Firebase Realtime Database

* 📩 **Push Notifications**
  Integrated Firebase Cloud Messaging (FCM) for message alerts

* 👤 **User List**
  Displays all registered users (excluding current user)

* 🎨 **Modern UI**
  Built using Jetpack Compose

* 🔄 **Live Updates**
  Messages update in real-time without refresh

---

## 🛠️ Tech Stack

* Kotlin
* Jetpack Compose
* Firebase Authentication
* Firebase Realtime Database
* Firebase Cloud Messaging (FCM)
* MVVM Architecture
* Hilt (Dependency Injection)

---

## 📱 Screens

### 🔑 Login Screen

* User enters name, email, and password
* Authenticated using Firebase

### 👥 User List Screen

* Displays all users
* Click user to start chat

### 💬 Chat Screen

* Real-time messaging
* Left/right message bubbles
* Timestamp display

---

## 🔔 Push Notification

* Sends notification when a new message is received
* Uses Firebase Cloud Messaging (FCM)
* Token stored in Firebase Database

---

## 📂 Firebase Realtime Database Structure

```json
{
  "users": {
    "uid_1": {
      "name": "Gomathi",
      "email": "admin@gmail.com",
      "fcmToken": "user_fcm_token"
    },
    "uid_2": {
      "name": "Test User",
      "email": "test@gmail.com",
      "fcmToken": "user_fcm_token"
    }
  },
  "chats": {
    "chatId_user1_user2": {
      "messageId_1": {
        "text": "Hi",
        "userId": "uid_1",
        "time": "01:39 PM"
      },
      "messageId_2": {
        "text": "Hello",
        "userId": "uid_2",
        "time": "01:41 PM"
      }
    }
  }
}
```

> Note: Message ownership is determined dynamically by comparing `userId` with the currently logged-in user.

---

## ⚙️ Setup Instructions

1. Clone the repository
2. Add your `google-services.json` file
3. Enable the following in Firebase Console:

   * Firebase Authentication
   * Realtime Database
   * Cloud Messaging
4. Run the project

---

## 🚀 Future Improvements

* ✅ Typing indicator
* ✅ Read receipts (✔✔)
* ✅ Image/File sharing
* ✅ Group chat

---

## 🙌 Author

**Gomathi Selvakumar**
Android Developer

---

## ⭐ Support

If you like this project, please give it a ⭐ on GitHub!
