import os
import re

src_dir = "app/src/main/java/com/example/game"
modified_count = 0

# Pattern to capture the component number (group 2) and the method name (group 3)
pattern = r'(/\*\s*renamed from:\s*component(\d+),\s*reason:\s*from getter\s*\*/\s+(?:public|private|protected)\s+(?:final\s+|static\s+|synchronized\s+)*[\w<>\?,\s]+\s+)(\w+)(\s*\()'

for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file.endswith(".java"):
            path = os.path.join(root, file)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            
            # Using \1 (prefix), \2 (component number), \4 (opening parenthesis)
            new_content, count = re.subn(pattern, r'\1component\2\4', content)
            if count > 0:
                with open(path, "w", encoding="utf-8") as f:
                    f.write(new_content)
                print(f"Fixed {count} component getters in {path}")
                modified_count += 1

print(f"Completed. Fixed {modified_count} files.")
