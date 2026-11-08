#!/usr/bin/env bash
# Installs the built APK on the emulator, opens it, and reports whether it stays running.
# Prints the crash log into the workflow log so problems can be diagnosed without a phone.
set -u
PKG=com.kingof9x.lomocam
APK=$(ls out/*.apk | head -1)
mkdir -p launch-test

echo "== Device: $(adb shell getprop ro.product.model) Android $(adb shell getprop ro.build.version.release) (SDK $(adb shell getprop ro.build.version.sdk))"
echo "== Installing $APK"
adb install -r -t "$APK" 2>&1 | tee launch-test/install.txt
adb shell pm list packages "$PKG" | grep -q "$PKG" || { echo "::error::APK did not install"; exit 1; }

adb logcat -c
echo "== Launching"
adb shell am start -W -n "$PKG/.MainActivity" 2>&1 | tee launch-test/am-start.txt
sleep 10

adb logcat -d -v time > launch-test/logcat.txt
adb logcat -d -b crash -v time > launch-test/crash.txt
adb exec-out screencap -p > launch-test/screen.png || true
adb shell dumpsys activity activities | grep -E "topResumedActivity|mResumedActivity" | tee launch-test/top.txt

echo "== Crash buffer"
cat launch-test/crash.txt
echo "== Log lines about LomoCam / runtime errors"
grep -E "AndroidRuntime|FATAL|$PKG|lomocam" launch-test/logcat.txt | tail -150

PID=$(adb shell pidof "$PKG" | tr -d '\r')
if [ -n "$PID" ] && grep -q "$PKG/.MainActivity" launch-test/top.txt && ! grep -q "FATAL EXCEPTION" launch-test/crash.txt; then
  echo "== PASS: LomoCam is running (pid $PID) and on screen"
  exit 0
fi
echo "::error::LomoCam did not stay open after launch (pid='$PID'). See crash log above."
exit 1
