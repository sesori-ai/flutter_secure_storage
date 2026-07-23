# Contributing

Thanks for considering contributing to flutter_secure_storage!

## Getting started

This is a [Melos](https://melos.invertase.dev/) monorepo. After cloning, bootstrap once before doing any other work:

```bash
dart pub global activate melos
melos bootstrap
```

`melos bootstrap` links all packages together via pubspec overrides so that local changes to one package are immediately visible across the others without publishing.

## Pull requests

* Open feature/fix PRs against `develop`.
* PR titles must follow [Conventional Commits](https://www.conventionalcommits.org/) (e.g. `feat: ...`, `fix: ...`, `chore: ...`) — this is enforced by CI.
* Run `melos analyze` and `melos format --output none --set-exit-if-changed` before pushing; both run in CI.
* Add or update tests for the packages you touch where applicable.

## Versioning

Each package in this repo (`flutter_secure_storage`, `flutter_secure_storage_platform_interface`, `flutter_secure_storage_darwin`, `flutter_secure_storage_linux`, `flutter_secure_storage_web`, `flutter_secure_storage_windows`) is versioned independently. If your PR changes a package's public behavior:

1. Bump the version in that package's `pubspec.yaml`.
2. Add a changelog entry at the top of that package's `CHANGELOG.md`.
3. If you bumped a platform package (darwin/linux/web/windows), also check whether the `^` constraint on it in `flutter_secure_storage/pubspec.yaml` needs updating.
