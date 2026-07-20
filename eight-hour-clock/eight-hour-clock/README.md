# 八小时时钟 v1.3

点一下（图标或桌面按钮），自动把系统闹钟设到 N 小时之后，设完即退。

原理：通过安卓系统的 `AlarmClock.ACTION_SET_ALARM` 接口，请手机自带的时钟 App 设置闹钟。响铃由系统时钟负责，关机重启、清理后台都不受影响，无需任何运行时权限。

## v1.3 变化

- **移除**了自带闹钟引擎和"一键删除"（系统闹钟无法被 App 删除，此路不通，回归纯粹的系统闹钟方案）
- **保留**安卓 12+ 透明启动画面：点图标不再看到"应用展开"的界面
- **新增桌面小组件**：一个 1x1 的"设闹钟"按钮，点它**不启动任何界面**（广播直接处理），是百分之百零动画的用法

## 功能一览

- **单击图标**：无界面，直接弹 Toast"闹钟已设置为 XX:XX"（个别品牌桌面可能有系统级图标动画，属系统行为）
- **桌面小组件按钮**：零界面、零动画，点完只弹 Toast（推荐用法）
- **长按图标**：快捷菜单 —— 快设 6 / 8 / 10 小时 + 设置
- **设置页**：几小时后响（默认 8）、闹钟个数（默认 1）、相邻间隔分钟（默认 5）
  - "把一键设闹钟按钮放到桌面"：一键添加小组件
  - "打开系统时钟管理闹钟"：跳转系统闹钟列表，方便手动清理（本应用建的闹钟都带"八小时时钟"前缀）

## 更新已有仓库（→ v1.3）

1. 下载并解压新版 zip
2. 仓库页面 → **Add file → Upload files** → 把 `eight-hour-clock` 文件夹整个拖进网页 → **Commit changes**（同名覆盖、新增加入）
3. 等 Actions 绿勾后下载 APK，手机直接安装覆盖旧版
4. （建议）在仓库里**手动删除 3 个旧版残留文件**（不删也能正常编译运行，只是死代码）：
   - `eight-hour-clock/app/src/main/java/com/example/eighthourclock/AlarmEngine.kt`
   - `eight-hour-clock/app/src/main/java/com/example/eighthourclock/AlarmReceiver.kt`
   - `eight-hour-clock/app/src/main/java/com/example/eighthourclock/BootReceiver.kt`

   删除方法：点进文件 → 右上角 ⋯ → **Delete file** → Commit changes

## 从零开始（完整步骤）

1. **建仓库**：GitHub → 右上角 + → New repository → 名字 `eight-hour-clock` → Public → Create
2. **传文件**：仓库页 → uploading an existing file → 把解压后的 `eight-hour-clock` 文件夹拖入 → Commit changes
   - 若 `.github` 文件夹漏传：Add file → Create new file → 文件名填 `.github/workflows/build.yml`，内容照抄 zip 里同名文件
3. **等打包**：Actions 标签 → Build APK 变绿勾（约 3~5 分钟）
4. **装手机**：运行记录页底部 Artifacts → 下载 zip → 解压出 `app-debug.apk` → 发到手机安装

## 桌面小组件添加方法

方式一（推荐）：长按图标 → 设置 → 点"把一键设闹钟按钮放到桌面"→ 系统弹窗确认

方式二（手动）：长按桌面空白处 → 小组件/插件 → 找到"八小时时钟"→ 拖到桌面

## 注意事项

- 每点一次会**新建**闹钟（个数由设置决定），多余的可在系统时钟 App 里删（本应用闹钟带"八小时时钟"前缀，好认）
- 安卓系统不提供删除系统闹钟的接口，任何 App 都无法代删，只能手动清理
- 支持 Android 8.0 及以上

## 目录结构说明

```
├── .github/workflows/build.yml   # 云端自动打包脚本（GitHub Actions）
├── build.gradle / settings.gradle / gradle.properties
└── app/
    ├── build.gradle              # 包名、版本、最低系统（Android 8.0+）
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/example/eighthourclock/
        │   ├── MainActivity.kt        # 单击图标：设闹钟 + Toast，无界面
        │   ├── SettingsActivity.kt    # 设置页（含一键添加小组件）
        │   ├── SystemAlarms.kt        # 系统闹钟设置逻辑（共用）
        │   └── ClockWidgetProvider.kt # 桌面小组件（1x1 按钮，零界面）
        └── res/
            ├── layout/activity_settings.xml / widget_clock.xml
            ├── drawable/widget_bg.xml        # 小组件圆角蓝底
            ├── values/strings.xml / styles.xml
            ├── values-v31/styles.xml         # 安卓12+ 透明启动画面
            └── xml/shortcuts.xml / widget_clock_info.xml
```
