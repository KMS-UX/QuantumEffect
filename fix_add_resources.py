import os
import re

src_dir = "app/src/main/java"
pattern = re.compile(r'viewModel\.addResources\(\s*credits\s*=\s*(.+?),\s*nanites\s*=\s*(.+?),\s*xp\s*=\s*(.+?)\)')

modified_count = 0

for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file.endswith(".kt") or file.endswith(".java"):
            path = os.path.join(root, file)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            
            new_content = pattern.sub(r'viewModel.addResources(\1, \2, \3)', content)
            
            if new_content != content:
                with open(path, "w", encoding="utf-8") as f:
                    f.write(new_content)
                print(f"Fixed addResources calls in {path}")
                modified_count += 1

print(f"Completed. Fixed {modified_count} files.")
