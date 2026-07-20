# 八小时时钟 v1.1

点一下图标，自动把系统闹钟设到 8 小时之后（时长、闹钟个数、间隔都可改），设完即退，不显示任何界面。

原理：App 通过 Android 系统的 `AlarmClock.ACTION_SET_ALARM` 接口，请手机自带的时钟 App 设置闹钟。响铃由系统时钟负责，关机重启、清理后台都不受影响，App 本身无需任何运行时权限。

## 功能

- **单击图标**：无界面、无动画，直接弹出 Toast"闹钟已设置为 XX:XX"
- **长按图标**：弹出快捷菜单
  - **设置**：进入设置页，可改 3 项
    - 几小时后响（默认 8 小时）
    - 闹钟个数（默认 1 个，最多 10 个）
    - 相邻闹钟间隔（默认 5 分钟，仅在个数 > 1 时生效）
  - **快设 6 / 8 / 10 小时**：一键按指定时长设闹钟（个数和间隔沿用设置）
- 多个闹钟的命名形如"八小时时钟 1/3、2/3、3/3"，方便在时钟 App 里辨认

## 更新已有仓库（v1.0 → v1.1）

1. 下载并解压新版 zip
2. 仓库页面 → **Add file → Upload files**
3. 把解压出来的 `eight-hour-clock` 文件夹**整个拖进网页**（和第一次上传一样的操作）
4. 同名文件会被覆盖、新文件会被加入 → 点 **Commit changes**
5. Actions 自动重新打包（约 3~5 分钟）→ 绿勾后下载 APK
6. 手机上**直接安装覆盖旧版**即可，不用卸载

## 从零开始（完整步骤）

### 第 1 步：建仓库

1. 打开 https://github.com 并登录
2. 点右上角 **+** → **New repository**
3. Repository name 填 `eight-hour-clock`，选 **Public**
4. 不用勾选任何初始化选项，直接点 **Create repository**

### 第 2 步：上传工程文件

1. 在新建的仓库页面，点 **uploading an existing file**
2. 把本文件夹解压后的 `eight-hour-clock` 文件夹整个拖进网页
3. 点页面底部 **Commit changes**

> 如果 `.github` 文件夹没被传上去（网页上传经常漏掉点开头的文件夹），
> 需要用 Add file → Create new file 手动创建 `.github/workflows/build.yml`，
> 内容为 `.github` 里那份脚本的原文。

### 第 3 步：等云端打包（约 3~5 分钟）

1. 点仓库顶部的 **Actions** 标签页
2. 看到 **Build APK** 任务在跑（黄色圆点 = 进行中）
3. 变成**绿色对勾**就是成功；红色叉号点进去看报错

### 第 4 步：下载 APK 并安装

1. 点进成功的运行记录 → 页面最下方 **Artifacts** → 下载 `eight-hour-clock-apk`
2. 解压得到 `app-debug.apk`，用微信文件传输助手 / 数据线发到手机
3. 点击安装，提示"未知来源"时选择允许
4. 桌面出现 **八小时时钟** 图标（蓝底白色表盘，指针指着 8 点）

## 注意事项

- 每点一次会**新建**闹钟（个数由设置决定）。点多了可以在时钟 App 里批量删除
- 闹钟由你手机自带的时钟 App 响铃，不同品牌（小米/华为/OPPO 等）的时钟界面略有差异
- 个别品牌桌面在点图标瞬间可能有极轻微的一闪，这是系统桌面行为，无法完全消除
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
        ├── java/com/example/eighthourclock/
        │   ├── MainActivity.kt       # 单击：设闹钟 + Toast，无界面
        │   └── SettingsActivity.kt   # 设置页：时长/个数/间隔
        └── res/
            ├── layout/activity_settings.xml  # 设置页布局
            ├── values/strings.xml            # 文案
            ├── values/styles.xml             # 全透明无动画主题
            └── xml/shortcuts.xml             # 长按快捷菜单
```
