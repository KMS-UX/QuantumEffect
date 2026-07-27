import os
import subprocess

src_dir = "app/src/main/java"
kt_files = []

# Discover all .kt files
for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file.endswith(".kt"):
            kt_files.append(os.path.join(root, file))

print(f"Discovered {len(kt_files)} Kotlin files to temporarily rename.")

try:
    # Rename to .bak
    for path in kt_files:
        os.rename(path, path + ".bak")
    print("Renamed all Kotlin files to .kt.bak")
    
    # Run gradle compilation for Java
    print("Running gradle :app:compileDebugJavaWithJavac...")
    result = subprocess.run(
        ["gradle", ":app:compileDebugJavaWithJavac"],
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        text=True
    )
    print("\n--- GRADLE OUTPUT ---")
    print(result.stdout)
    print("---------------------\n")
    
finally:
    # Rename back to .kt
    for path in kt_files:
        bak_path = path + ".bak"
        if os.path.exists(bak_path):
            os.rename(bak_path, path)
    print("Renamed all Kotlin files back to .kt")
