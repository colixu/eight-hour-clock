# 八小时时钟

点一下图标，自动把系统闹钟设到 8 小时之后，设完即退。

原理：App 通过 Android 系统的 `AlarmClock.ACTION_SET_ALARM` 接口，请手机自带的时钟 App 设置闹钟。响铃由系统时钟负责，关机重启、清理后台都不受影响，App 本身无需任何运行时权限。

## 打包 APK 的步骤（全程网页，不用装任何软件）

### 第 1 步：建仓库

1. 打开 https://github.com 并登录
2. 点右上角 **+** → **New repository**
3. Repository name 填 `eight-hour-clock`，选 **Public**（私有仓库也行）
4. 不用勾选任何初始化选项，直接点 **Create repository**

### 第 2 步：上传工程文件

1. 在新建的仓库页面，点 **uploading an existing file**（或 Add file → Upload files）
2. 把本文件夹解压后的**全部内容**（包括 `.github` 文件夹、`app` 文件夹和所有 `.gradle` 文件）一起拖进网页
3. 等所有文件列出来后，点页面底部 **Commit changes**

> 注意：`.github` 文件夹必须上传成功，否则不会自动打包。
> 上传完成后仓库里应该能看到 `.github`、`app`、`build.gradle`、`settings.gradle` 等条目。

### 第 3 步：等云端打包（约 3~5 分钟）

1. 上传成功后，点仓库顶部的 **Actions** 标签页
2. 如果页面中间有提示按钮（如 "I understand my workflows..."），点一下启用
3. 会看到一个名为 **Build APK** 的任务正在跑（黄色圆点 = 进行中）
4. 变成**绿色对勾**就是成功了；红色叉号就是失败，点进去把红色报错截图发给我

### 第 4 步：下载 APK

1. 点进那次成功的运行记录
2. 拉到页面最下方 **Artifacts** 区域
3. 下载 `eight-hour-clock-apk`（是个 zip，解压后里面有 `app-debug.apk`）

### 第 5 步：装到手机上

1. 用微信文件传输助手 / 数据线 / 邮件把 `app-debug.apk` 发到手机
2. 点击安装，手机提示"未知来源"时选择允许（这是自己打包的正常提示）
3. 桌面出现 **八小时时钟** 图标（蓝底白色表盘，指针指着 8 点）

## 使用方法

点一下图标 → 屏幕下方弹出"闹钟已设置为 22:30" → 结束。

可以打开手机的时钟 App 确认闹钟已建好。

## 注意事项

- 每点一次会**新建**一个闹钟。连点三次就会有三个闹钟，多余的可以在时钟 App 里删掉
- 闹钟由你手机自带的时钟 App 响铃，不同品牌（小米/华为/OPPO 等）的时钟界面略有差异
- 支持 Android 8.0 及以上（2017 年后的手机基本都行）

## 目录结构说明

```
├── .github/workflows/build.yml   # 云端自动打包脚本（GitHub Actions）
├── build.gradle                  # 顶层构建配置
├── settings.gradle               # 工程设置
├── gradle.properties             # Gradle 参数
└── app/
    ├── build.gradle              # App 模块配置（包名、版本、最低系统）
    └── src/main/
        ├── AndroidManifest.xml   # 应用清单
        ├── java/com/example/eighthourclock/MainActivity.kt  # 核心代码
        └── res/                  # 应用名称、图标资源
```
