# 八小时时钟 v1.6

点一下（图标或桌面按钮），自动把系统闹钟设到 N 小时之后。

原理：通过安卓系统的 `AlarmClock.ACTION_SET_ALARM` 接口，请手机自带的时钟 App 设置闹钟。响铃由系统时钟负责，关机重启、清理后台都不受影响，无需任何运行时权限。

## v1.6 变化

- **设置页赛博朋克风**：近黑背景、霓虹青光 + 品红点缀、发光标题、霓虹描边参数卡片、青蓝渐变保存按钮、中英双语标签
- 设置页只保留两个按钮：「保存设置」「打开系统时钟管理闹钟」（"放到桌面"按钮已移除）
- 图标：深色渐变底 + 青色细线表盘（v1.5 新版样式）

## ⚠️ 上传时最容易犯的错（必读）

打包脚本只认仓库里的 `eight-hour-clock/` 目录。上传时**必须拖名为 `eight-hour-clock` 的这个文件夹本身**，不能拖套在它外面的任何文件夹（如"八小时时钟-vX.X"），否则文件会传到错误路径，打出来的还是旧版。

## 更新已有仓库（→ v1.6）

1. 在桌面找到 `eight-hour-clock` 文件夹（没有外层套娃）
2. 仓库页面 → **Add file → Upload files** → 把这个 `eight-hour-clock` 文件夹拖进网页 → **Commit changes**
3. 等 Actions 绿勾 → 下载 APK → 手机直接安装覆盖旧版
4. 桌面小组件样式如未更新，长按移除后重新拖一个

> 仓库里之前误传的 `八小时时钟-v1.5` 文件夹是无害的死文件，不影响打包，可忽略。
> `AlarmEngine.kt`、`AlarmReceiver.kt`、`BootReceiver.kt`、`widget_bg.xml` 4 个旧残留同理，想删可点进文件 → ⋯ → Delete file。

## 从零开始（完整步骤）

1. **建仓库**：GitHub → 右上角 + → New repository → 名字 `eight-hour-clock` → Public → Create
2. **传文件**：仓库页 → uploading an existing file → 把 `eight-hour-clock` 文件夹拖入 → Commit changes
   - 若 `.github` 文件夹漏传：Add file → Create new file → 文件名填 `.github/workflows/build.yml`，内容照抄 zip 里同名文件
3. **等打包**：Actions 标签 → Build APK 变绿勾（约 3~5 分钟）
4. **装手机**：运行记录页底部 Artifacts → 下载 zip → 解压出 `app-debug.apk` → 发到手机安装

## 桌面小组件添加方法（手动）

长按桌面空白处 → 小组件/插件 → 找到"八小时时钟"→ 拖到桌面

## 注意事项

- 每点一次会**新建**闹钟（个数由设置决定），多余的可在系统时钟 App 里删
- 安卓系统不提供删除系统闹钟的接口，任何 App 都无法代删，只能手动清理
- 支持 Android 8.0 及以上
