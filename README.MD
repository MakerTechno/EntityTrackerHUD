# Entity Tracker (简体中文|[English](README_EN.MD))
这是一个用于在MC中跟踪实体的HUD模组。
### 工作原理
使用投影计算在2D屏幕上显示标志以表示实体位置。

详见[算法](ALGORITHM.MD)
### 特性
- 直接通过指示显示实体的位置
- 可自定义某实体类型的实体定位
- 可自定义HUD指针
### 作者
[@MakerTechno](https://github.com/MakerTechno)
### 协议
[LGPLv3](https://www.gnu.org/licenses/lgpl-3.0.html)
### 依赖
无
### 联动
[汇流来世 Confluence Of The Afterlife](https://github.com/MagicHarp/confluence)

## 本地构建
本项目作为单独项目构建时，需要在你的项目库中添加`gradle.local.properties`文件，并在文件中写入:
```properties
nativeBuild=true
```
本项目作为子项目构建时，请在主项目中定义moddev插件的版本号