# Entity Tracker (English|[简体中文](README.MD))
This is a simple HUD for tracking entities in Minecraft.
### How it works
Using a set of projection, it displays entities' positions in 2D with customized icon.

See [ALGORITHM (Chinese only)](ALGORITHM.MD) for details.
### Features
- Display entities' positions in 2D
- Specific entities tracking
- Customizable HUD pointers
### Author
[@MakerTechno](https://github.com/MakerTechno)
### License
[LGPLv3](https://www.gnu.org/licenses/lgpl-3.0.html)
### Dependencies
None
### Collaboration
[Confluence Of The Afterlife](https://github.com/MagicHarp/confluence)

## Local Build
When build as single project, please add a file `gradle.local.properties` in your local repo, and add this line in it:
```properties
nativeBuild=true
```
When build as submodule，please define moddev plugin version in base project.