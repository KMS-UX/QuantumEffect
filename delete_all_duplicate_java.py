import os
import re

java_src_dir = "app/src/main/java"
kt_classes = set()

class_regex = re.compile(r'\b(?:class|object|interface|enum class)\s+([A-Za-z0-9_]+)\b')

# 1. Gather all declarations from all Kotlin files in the project
for root, dirs, files in os.walk(java_src_dir):
    for f in files:
        if f.endswith(".kt"):
            path = os.path.join(root, f)
            with open(path, "r", encoding="utf-8") as file:
                content = file.read()
                for match in class_regex.finditer(content):
                    kt_classes.add(match.group(1))

print(f"Total Kotlin declarations found: {len(kt_classes)}")

# 2. Find and delete duplicate Java files
deleted_count = 0
for root, dirs, files in os.walk(java_src_dir):
    for f in files:
        if f.endswith(".java"):
            name_sans_ext = f[:-5]
            should_delete = False
            if name_sans_ext in kt_classes:
                should_delete = True
            else:
                for kt_c in kt_classes:
                    if name_sans_ext.startswith(kt_c + "$") or name_sans_ext == kt_c:
                        should_delete = True
                        break
            
            if should_delete:
                path = os.path.join(root, f)
                os.remove(path)
                print(f"Deleted duplicate Java file across project: {path}")
                deleted_count += 1

print(f"Completed. Deleted {deleted_count} duplicate Java files across project.")
