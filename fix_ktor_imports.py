import os

src_dir = "app/src/main/java"
modified_files = []

for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file.endswith(".java"):
            path = os.path.join(root, file)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            
            new_content = content
            new_content = new_content.replace("import io.ktor.http.ContentDisposition;", "")
            new_content = new_content.replace("import io.ktor.http.LinkHeader;", "")
            new_content = new_content.replace("ContentDisposition.Parameters.Size", '"size"')
            new_content = new_content.replace("LinkHeader.Parameters.Type", '"type"')
            new_content = new_content.replace("LinkHeader.Parameters.Title", '"title"')
            
            if new_content != content:
                with open(path, "w", encoding="utf-8") as f:
                    f.write(new_content)
                print(f"Fixed Ktor dependencies in {path}")
                modified_files.append(path)

print(f"Completed. Modified {len(modified_files)} files.")
