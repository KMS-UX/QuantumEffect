import os
import re

src_dir = "app/src/main/java/com/example/game"
duplicate_found = False

# Pattern to find Java fields
# E.g., private static volatile GameDatabase INSTANCE;
# Or public static final Companion INSTANCE = ...;
field_pattern = r'(?:public|private|protected|internal)\s+(?:static\s+|volatile\s+|final\s+|transient\s+)*([\w<>\?,\s]+)\s+(\w+)\s*(?:=|;)'

for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file.endswith(".java"):
            path = os.path.join(root, file)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            
            # Simple line by line check for variable declarations
            fields = {}
            for line_no, line in enumerate(content.splitlines(), 1):
                # Ignore method definitions (which end in { or contain () etc.)
                if "(" in line or ")" in line:
                    continue
                match = re.search(r'\b(public|private|protected)\b.*?\b(\w+)\s*(?:=|;)', line)
                if match:
                    field_name = match.group(2)
                    # Exclude keywords
                    if field_name in ["class", "interface", "enum", "void", "return", "throw", "import", "package"]:
                        continue
                    if field_name in fields:
                        fields[field_name].append(line_no)
                    else:
                        fields[field_name] = [line_no]
            
            duplicates = {name: lines for name, lines in fields.items() if len(lines) > 1}
            # Filter out non-duplicates (e.g. nested classes can have fields with same name)
            # For simplicity, we print if duplicates exist on different lines.
            if duplicates:
                print(f"File: {path}")
                duplicate_found = True
                for name, lines in duplicates.items():
                    print(f"  Field '{name}' declared multiple times on lines: {lines}")

if not duplicate_found:
    print("No duplicate field declarations found in Java files.")
