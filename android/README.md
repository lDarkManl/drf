# DRF Android Client

A minimal Android app (Kotlin) to connect to your Django REST backend.

## Open in Android Studio

1. Open Android Studio → "Open" → select the `android/` folder.
2. Let Gradle sync. If prompted, allow Android Studio to upgrade the Android Gradle Plugin.
3. Run the `app` configuration on the Android Emulator or a device.

## Backend URL

- The app points to `http://10.0.2.2:8000/` which maps to `localhost:8000` of your PC when using the Android Emulator.
- Start your Django server from the project root:

```bash
python manage.py runserver 0.0.0.0:8000
```

- If you run the app on a physical device, replace the base URL in `android/app/src/main/java/com/example/drfclient/ApiClient.kt` with your machine IP, e.g. `http://192.168.1.10:8000/`.

## What it does

- Launch the app → tap "Fetch from DRF" → it performs a GET request to `/` on your backend and shows the raw response.
- Adjust the endpoint in `ApiService.kt` to target your API (e.g. `/api/items/`).

## Notes

- Internet permission is enabled in `AndroidManifest.xml`.
- ViewBinding is enabled; UI layout is in `res/layout/activity_main.xml`.

