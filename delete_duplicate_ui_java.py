import os
import re

ui_dir = "app/src/main/java/com/example/game/ui"
kt_classes = set()

# 1. Parse class, object, interface names from all .kt files in ui directory
class_regex = re.compile(r'\b(?:class|object|interface|enum class)\s+([A-Za-z0-9_]+)\b')

for f in os.listdir(ui_dir):
    if f.endswith(".kt"):
        path = os.path.join(ui_dir, f)
        with open(path, "r", encoding="utf-8") as file:
            content = file.read()
            # Find all class names
            for match in class_regex.finditer(content):
                kt_classes.add(match.group(1))

print(f"Found {len(kt_classes)} classes/objects declared in Kotlin files.")
print("Some of these:", sorted(list(kt_classes))[:20])

# 2. Check each .java file in the ui directory.
# If its filename (sans .java) is in kt_classes, or if it is a secondary/lambda class of those, delete it.
deleted_count = 0
for f in os.listdir(ui_dir):
    if f.endswith(".java"):
        name_sans_ext = f[:-5] # remove '.java'
        # Check if the class is declared in any .kt file
        should_delete = False
        if name_sans_ext in kt_classes:
            should_delete = True
        else:
            # Check if it starts with any of the Kotlin classes followed by $ (e.g. lambda classes)
            for kt_c in kt_classes:
                if name_sans_ext.startswith(kt_c + "$") or name_sans_ext == kt_c:
                    should_delete = True
                    break
        
        if should_delete:
            path = os.path.join(ui_dir, f)
            os.remove(path)
            print(f"Deleted duplicate Java file: {path}")
            deleted_count += 1

print(f"Successfully deleted {deleted_count} duplicate Java files.")
