# 角色文件夹结构

## 目录结构

每个角色的所有相关文件都集中在一个独立的文件夹中，结构如下：

```
src/main/resources/roles/
├── [角色名]/
│   ├── config/
│   │   └── role.json          # 角色配置文件
│   └── state/
│       ├── idle/              # 待机动画帧
│       ├── stand/             # 站立动画帧
│       └── run/               # 奔跑动画帧
└── default/                    # 默认桌宠角色
    ├── config/
    │   └── role.json
    └── state/
        ├── idle/
        │   └── 0.png
        ├── stand/
        └── run/
            ├── 0.png
            ├── 1.png
            └── ...
```

## 添加新角色

### 1. 创建角色文件夹

在 `src/main/resources/roles/` 下创建新角色文件夹，例如 `cat`：

```
src/main/resources/roles/cat/
├── config/
│   └── role.json
└── state/
    ├── idle/
    ├── stand/
    └── run/
```

### 2. 配置角色信息

在 `config/role.json` 中定义角色配置：

```json
{
  "name": "cat",
  "displayName": "猫咪桌宠",
  "description": "可爱的猫咪桌宠角色",
  "width": 200,
  "height": 250,
  "actions": [
    {
      "name": "IDLE",
      "folderName": "idle",
      "frameCount": 1,
      "loop": false,
      "frameDurationMs": 160
    },
    {
      "name": "STAND",
      "folderName": "stand",
      "frameCount": 3,
      "loop": true,
      "frameDurationMs": 160
    },
    {
      "name": "RUN",
      "folderName": "run",
      "frameCount": 11,
      "loop": true,
      "frameDurationMs": 160
    }
  ]
}
```

### 3. 添加动画帧图片

在对应的 `state` 子文件夹中放入帧图片，命名规则为数字序号：

- `state/idle/0.png` - 待机帧（第1帧）
- `state/run/0.png`, `state/run/1.png`, ... - 奔跑动画帧

### 4. 注册角色

在代码中注册新角色（后续版本可能支持自动扫描）：

编辑 `PetRole.java`：

```java
public static List<PetRole> getAllRoles() {
    return List.of(Default, Cat);
}

public static final PetRole Default = new PetRole("default");
public static final PetRole Cat = new PetRole("cat");
```

## 配置说明

### role.json 字段说明

| 字段 | 类型 | 说明 |
|------|------|------|
| name | String | 角色标识名（文件夹名） |
| displayName | String | 显示名称 |
| description | String | 角色描述 |
| width | Integer | 角色宽度（像素） |
| height | Integer | 角色高度（像素） |
| actions | Array | 动作配置列表 |

### ActionConfig 字段说明

| 字段 | 类型 | 说明 |
|------|------|------|
| name | String | 动作标识名（代码中使用） |
| folderName | String | 帧图片所在文件夹名 |
| frameCount | Integer | 动画帧数量 |
| loop | Boolean | 是否循环播放 |
| frameDurationMs | Integer | 每帧持续时间（毫秒） |

## 播放动画

在代码中播放角色动画：

```java
@Autowired
private PetMainFrame mainFrame;

// 设置角色
mainFrame.setRole(PetRole.getDefault());

// 播放动作（使用动作名）
mainFrame.playAnimation("run");
mainFrame.playAnimation("idle");
mainFrame.playAnimation("stand");

// 指定帧率播放
mainFrame.playAnimation("run", 200); // 200ms每帧
```
