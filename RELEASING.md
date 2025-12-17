# Releasing Volley Java SDK

This guide explains how to release a new version of the Volley Java SDK.

## Prerequisites

1. Ensure all tests pass:
   ```bash
   mvn test
   ```

2. Ensure the code compiles:
   ```bash
   mvn clean compile
   ```

3. Update the version in `pom.xml`:
   ```xml
   <version>1.0.0</version>
   ```

4. Update the version in `Version.java`:
   ```java
   public static final String VERSION = "1.0.0";
   ```

## Release Steps

### Option 1: GitHub Releases (Recommended for now)

This is the simplest approach if you're not publishing to Maven Central yet.

#### 1. Build the JAR

```bash
mvn clean package
```

This creates `target/volley-java-1.0.0.jar`

#### 2. Create a Git Tag

Tag the release with a semantic version (e.g., `v1.0.0`):

```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
git push origin main  # or master
```

**Important**: The tag name must start with `v` and match the version in `pom.xml` (without the `v` prefix).

#### 3. Create GitHub Release

1. Go to your GitHub repository → Releases → "Draft a new release"
2. Select the tag (e.g., `v1.0.0`)
3. Upload the JAR file: `target/volley-java-1.0.0.jar`
4. Add release notes
5. Publish

#### 4. Users Can Install

Users can download the JAR from the GitHub release page and add it to their classpath, or use it as a local Maven dependency.

### Option 2: Maven Central (For future)

To publish to Maven Central (e.g., via Sonatype OSSRH), you'll need:

1. **Sign up for Sonatype OSSRH** and get approval for your groupId
2. **Configure GPG signing** for artifacts
3. **Add distribution management** to `pom.xml`
4. **Use Maven Release Plugin** or manually deploy

This is more complex and requires:
- Sonatype OSSRH account
- GPG key setup
- Additional Maven configuration

For now, GitHub Releases is sufficient.

## Versioning

Follow [Semantic Versioning](https://semver.org/):
- **MAJOR** version for incompatible API changes
- **MINOR** version for backwards-compatible functionality additions
- **PATCH** version for backwards-compatible bug fixes

## Example Release Workflow

```bash
# 1. Update versions
# Edit pom.xml: <version>1.0.0</version>
# Edit Version.java: public static final String VERSION = "1.0.0";

# 2. Test
mvn clean test

# 3. Build
mvn clean package

# 4. Commit changes
git add pom.xml src/main/java/com/volleyhooks/volley/Version.java
git commit -m "Bump version to 1.0.0"

# 5. Create and push tag
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
git push origin main

# 6. Create GitHub Release
# - Go to GitHub → Releases → Draft new release
# - Select tag v1.0.0
# - Upload target/volley-java-1.0.0.jar
# - Add release notes
# - Publish
```

## Notes

- The version in `pom.xml` is the source of truth for Maven
- The version in `Version.java` is for runtime access
- Keep both in sync
- For GitHub Releases, upload the JAR file manually
- For Maven Central, you'll need additional setup (see Option 2)

