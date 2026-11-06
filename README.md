# LomoCam

The digital Lomo cam: an Android stills camera that recreates the physical features of
Lomography 35mm cameras (MC-A, Half Frame, Fisheye No.2, ActionSampler, SuperSampler,
Oktomat, Pop 9) as layers you can mix freely.

Built for one device only: Google Pixel 10 Pro on the latest stable Android.

## Installing a build on the Pixel

Every push is built by GitHub Actions ("Build APK" workflow).

1. On the Pixel, open the repo's **Releases** page and pick the newest `LomoCam build N`.
2. Tap the `.apk` file to download it, then open it and tap **Install**
   (the first time, Android asks you to allow installs from your browser).
3. Later builds install over the previous one, as long as the signing secrets below are set.

The same APK is also attached to each workflow run under **Artifacts** (zipped).

## Signing (one-time setup)

CI signs with a fixed release key so updates install over each other. It reads four
repository secrets (Settings → Secrets and variables → Actions):

| Secret | Contents |
| --- | --- |
| `LOMOCAM_KEYSTORE_BASE64` | the keystore file, base64-encoded |
| `LOMOCAM_KEYSTORE_PASSWORD` | keystore password |
| `LOMOCAM_KEY_ALIAS` | key alias |
| `LOMOCAM_KEY_PASSWORD` | key password |

Without them the build still succeeds but is signed with a throwaway debug key.
**Back up the keystore.** If it's lost, new builds can't update the installed app
(you'd have to uninstall, losing app data).

## Fallback: build locally

Open the folder in Android Studio, plug in the Pixel with USB debugging on, and press Run.
