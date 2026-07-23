#!/usr/bin/env python3
"""Keep flutter_secure_storage/pubspec.yaml's platform package constraints in
sync with .release-please-manifest.json.

Release Please only bumps a package's own pubspec.yaml `version:` field; it
has no notion that flutter_secure_storage/pubspec.yaml pins that same package
with a `^` dependency constraint. On a breaking (major) bump this leaves the
melos workspace unresolvable (`pub get` fails) until someone manually widens
the constraint. This script is run by
.github/workflows/release-please-sync-constraints.yml on every Release
Please PR to apply that update automatically.
"""

from __future__ import annotations

import json
import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
MANIFEST_PATH = REPO_ROOT / ".release-please-manifest.json"
PUBSPEC_PATH = REPO_ROOT / "flutter_secure_storage" / "pubspec.yaml"


def main() -> int:
    manifest = json.loads(MANIFEST_PATH.read_text())
    pubspec = PUBSPEC_PATH.read_text()

    changed = False
    for package, version in manifest.items():
        if package == "flutter_secure_storage":
            continue  # not a dependency of itself

        pattern = re.compile(
            rf"^(  {re.escape(package)}: \^)([0-9A-Za-z.+-]+)$", re.MULTILINE
        )

        def replace(match: re.Match) -> str:
            nonlocal changed
            if match.group(2) != version:
                changed = True
            return f"{match.group(1)}{version}"

        pubspec = pattern.sub(replace, pubspec)

    if changed:
        PUBSPEC_PATH.write_text(pubspec)
        print("Updated flutter_secure_storage/pubspec.yaml constraints.")
    else:
        print("Constraints already in sync.")

    return 0


if __name__ == "__main__":
    sys.exit(main())
