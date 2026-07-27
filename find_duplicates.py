import os
import re

src_dir = "app/src/main/java/com/example/game"
duplicate_found = False

for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file.endswith(".java"):
            path = os.path.join(root, file)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            
            # Find all public/private/protected final method definitions
            # E.g., public final String getPlayerName() {
            # Let's match: public final/static/abstract/etc <Type> <methodName>(...) {
            matches = re.findall(r'(public|private|protected)\s+(?:final\s+|static\s+|synchronized\s+)*([\w<>\?,\s]+)\s+(\w+)\s*\(([^)]*)\)\s*\{', content)
            
            method_signatures = {}
            for modifier, return_type, name, args in matches:
                # Clean up whitespaces inside args
                args_clean = ",".join([a.strip().split()[-1] if len(a.strip().split()) > 0 else "" for a in args.split(",")])
                sig = f"{name}({args_clean})"
                if sig in method_signatures:
                    method_signatures[sig].append((modifier, return_type, args))
                else:
                    method_signatures[sig] = [(modifier, return_type, args)]
            
            duplicates = {sig: info for sig, info in method_signatures.items() if len(info) > 1}
            if duplicates:
                print(f"File: {path}")
                duplicate_found = True
                for sig, info in duplicates.items():
                    print(f"  Duplicate method: {sig}")
                    for mod, ret, args in info:
                        print(f"    - {mod} {ret} with args: ({args})")

if not duplicate_found:
    print("No duplicate method signatures found in Java files.")
