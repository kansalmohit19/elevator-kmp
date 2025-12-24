# Elevator KMP (Elevator System Simulation UI)

A **Kotlin Multiplatform (KMP)** sample project demonstrating an **Elevator System Simulation** with a **Jetpack Compose UI** on Android.

This project visualizes:

-   Multiple elevators
-   Real-time elevator movement (up / down / idle)
-   Request queue handling
-   Floor selection via a keypad-style UI

The core elevator logic is maintained in a separate repository, [`elevator-common`](https://github.com/kansalmohit19/elevator-common), which is linked to this project via **Git submodules**. The UI layer is built using **Jetpack Compose**.

---

## 📱 Demo

<img src="videos/presentation.gif" alt="Elevator Demo" height="600">

---

## 📱 Features

-   🚀 Multiple elevators with individual states
-   ⬆️⬇️ Direction-aware movement (Up / Down)
-   🧠 Centralized queue-based elevator dispatching
-   🎛️ Interactive floor buttons
-   🎨 Material 3 theming with custom colors
-   ♻️ Shared business logic via KMP

---

## 🧩 Project Structure

```text
.
├── androidApp/
│   ├── MainActivity.kt
│   └── ui/
│       ├── AppColors.kt
│       ├── Theme.kt
│       └── ...
│
├── submodules/elevator-common   ← Git Submodule
│   ├── Elevator.kt
│   ├── ElevatorUseCase.kt
│   └── ...
│
│
└── README.md
```