import os
import shutil

src_root = "/tmp/decompiled/sources"
res_root = "/tmp/decompiled_res/resources"
dest_java_root = "app/src/main/java"
dest_res_root = "app/src/main/res"
dest_manifest = "app/src/main/AndroidManifest.xml"

# 1. Restore resources and Manifest
print("Restoring Manifest and Resources...")
if os.path.exists(res_root):
    # Copy manifest
    manifest_src = os.path.join(res_root, "AndroidManifest.xml")
    if os.path.exists(manifest_src):
        os.makedirs(os.path.dirname(dest_manifest), exist_ok=True)
        shutil.copy(manifest_src, dest_manifest)
        print("Copied AndroidManifest.xml")
    
    # Copy res folder
    res_src = os.path.join(res_root, "res")
    if os.path.exists(res_src):
        if os.path.exists(dest_res_root):
            shutil.rmtree(dest_res_root)
        shutil.copytree(res_src, dest_res_root)
        print("Copied resources folder to", dest_res_root)

# 2. Restore Java sources
print("\nRestoring Java sources...")
if os.path.exists(src_root):
    # Create target directories
    for pkg in ["api", "db", "models", "viewmodel", "ui"]:
        os.makedirs(os.path.join(dest_java_root, "com/example/game", pkg), exist_ok=True)
    os.makedirs(os.path.join(dest_java_root, "com/example"), exist_ok=True)

    # Excluded files list
    excluded_files = [
        "R.java", "BuildConfig.java", 
        "StarshipCommandView.java", "QuantumWarpView.java", "GameScreens.java"
    ]
    # Also exclude lambda classes related to the kept kotlin files
    def is_excluded(filename):
        if filename in excluded_files:
            return True
        if filename.startswith("GameScreens") or filename.startswith("StarshipCommandView") or filename.startswith("QuantumWarpView"):
            return True
        return False

    for root, dirs, files in os.walk(os.path.join(src_root, "com/example")):
        # Calculate relative path to com/example
        rel_path = os.path.relpath(root, src_root)
        dest_dir = os.path.join(dest_java_root, rel_path)
        os.makedirs(dest_dir, exist_ok=True)

        for f in files:
            if f.endswith(".java") and not is_excluded(f):
                src_file = os.path.join(root, f)
                dest_file = os.path.join(dest_dir, f)
                shutil.copy(src_file, dest_file)
                # print(f"Copied {f} to {rel_path}")

print("\nSource and resource restoration complete!")
