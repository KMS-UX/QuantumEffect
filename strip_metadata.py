import os

def strip_metadata(content):
    pos = 0
    while True:
        pos = content.find("@Metadata", pos)
        if pos == -1:
            break
        start_paren = content.find("(", pos)
        if start_paren == -1:
            pos += 9
            continue
        count = 1
        i = start_paren + 1
        while i < len(content) and count > 0:
            if content[i] == '(':
                count += 1
            elif content[i] == ')':
                count -= 1
            i += 1
        if count == 0:
            content = content[:pos] + content[i:]
        else:
            pos += 9
    return content

src_dir = "app/src/main/java"
modified_count = 0

for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file.endswith(".java"):
            path = os.path.join(root, file)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            
            new_content = strip_metadata(content)
            new_content = new_content.replace("import kotlin.Metadata;", "")
            
            if new_content != content:
                with open(path, "w", encoding="utf-8") as f:
                    f.write(new_content)
                print(f"Stripped @Metadata from {path}")
                modified_count += 1

print(f"Completed. Stripped @Metadata from {modified_count} files.")
