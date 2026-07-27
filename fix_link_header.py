import os

files_to_fix = [
    "app/src/main/java/com/example/game/models/Quest.java",
    "app/src/main/java/com/example/game/models/DeployedStructure.java",
    "app/src/main/java/com/example/game/models/Weapon.java",
    "app/src/main/java/com/example/game/api/WebSource.java"
]

for path in files_to_fix:
    if os.path.exists(path):
        with open(path, "r", encoding="utf-8") as f:
            content = f.read()
        
        # Perform replacements
        new_content = content.replace("import io.ktor.http.LinkHeader;", "")
        new_content = new_content.replace("LinkHeader.Parameters.Type", '"type"')
        new_content = new_content.replace("LinkHeader.Parameters.Title", '"title"')
        
        if new_content != content:
            with open(path, "w", encoding="utf-8") as f:
                f.write(new_content)
            print(f"Successfully fixed LinkHeader references in {path}")
        else:
            print(f"No changes needed for {path}")
    else:
        print(f"File not found: {path}")
