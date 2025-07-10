from ultralytics import YOLO

# 加载一个模型，路径为 YOLO 模型的 .pt 文件
model = YOLO("runs/train/exp14/weights/best.pt")

# 导出模型，格式为 ONNX
model.export(format="onnx")