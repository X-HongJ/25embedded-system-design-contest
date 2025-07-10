import fiftyone as fo
import os

# 设置数据集的路径
dataset_dir = r"E:\PostGraduatedProjext\Other\cprpc\code\ultralytics\data\imageSets\fireandsmoke"

# 定义数据集名称和类型
dataset_name = 'fire_smoke'
dataset_type = fo.types.YOLOv5Dataset  # YOLO格式的数据集

# 加载数据集
dataset = fo.load_dataset(dataset_name)

# 数据集分割
splits = ["train", "val", "test"]

# 将数据从目录加载到数据集
for split in splits:
    dataset.add_dir(
        dataset_dir=dataset_dir,  # 数据集所在目录
        dataset_type=dataset_type,  # 数据集类型
        split=split,  # 数据集分割类型
        tags=[split]  # 标签，按分割类型添加
    )

# 持久化数据集
dataset.persistent = True

# 启动应用并查看数据集
session = fo.launch_app(dataset, port=5151)
session.wait()
