#!/usr/bin/env bash
set -e

echo "=============================="
echo " Building NeoForge Mod JAR"
echo "=============================="

# Ensure dist exists
mkdir -p dist

# Build NeoForge jar
./gradlew :neoforge:clean
./gradlew :neoforge:remapJar

# Find the remapped jar automatically
JAR=$(ls neoforge/build/libs/*.jar | grep -v "sources\|javadoc" | head -n 1)

if [ -z "$JAR" ]; then
  echo "❌ No jar found!"
  exit 1
fi

# Copy & rename
cp "$JAR" dist/HackClient-1.21.1.jar

echo
echo "=============================="
echo " NeoForge JAR built!"
echo "=============================="
echo " Output: dist/HackClient-1.21.1.jar"
echo

ls -lh dist/
