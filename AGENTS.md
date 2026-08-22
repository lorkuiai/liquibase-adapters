# Repository Guidelines

## Scope

- This repository contains thin Liquibase SPI adapters. Keep database-specific
  behavior in adapter modules; do not fork or modify Liquibase core.
- Preserve the distinction between SQL dialect behavior and physical metadata
  behavior. A compatibility mode may reuse a Liquibase dialect while requiring
  dedicated metadata snapshot support.
- Keep support scoped to the documented KingbaseES V8 compatibility modes.
  Do not claim support for an untested database version, driver, or mode.

## Development

- Use Java 17 and follow the existing package, naming, and formatting style.
- Prefer Liquibase extension points and service-provider registrations over
  reflection, patches to third-party libraries, or application-side workarounds.
- Add a focused test for every behavior change. Use unit tests for adapter
  selection and SPI registration, and V8 Testcontainers integration tests for
  JDBC metadata, update, rollback, and snapshot behavior.
- Keep changelog fixtures minimal and include explicit rollback where relevant.
- Avoid unrelated refactors and do not commit local tooling directories such as
  `.codex/`, build output, credentials, or generated artifacts.

## Verification

- Run `./gradlew :liquibase-kingbase:test` for unit changes.
- Run `./gradlew :liquibase-kingbase:integrationTest` when changing database
  detection, SQL generation, JDBC metadata, snapshots, or changelog behavior.
- Before committing, run `git diff --check` and review the staged diff.

## Git

- Create a focused branch from the intended base branch. Use concise
  Conventional Commit messages, for example `fix(kingbase): ...`.
- Keep commits coherent. Squash exploratory or redundant commits before opening
  a pull request.
- Never include local-only files or unrelated changes in a commit.

## Pull Requests

- Use `.github/pull_request_template.md` exactly. PR descriptions contain only
  `## Summary` and `## Changes`.
- Write the PR title, summary, and changes in English.
- The summary is one short paragraph covering the goal, outcome, and reviewer
  impact. List 3-7 concrete, reviewable changes under `Changes`.
- Open dependent pull requests against their feature branch; open independent
  pull requests against `develop`.
