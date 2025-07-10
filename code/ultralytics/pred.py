import warnings
warnings.filterwarnings('ignore')
from ultralytics import YOLO

if __name__ == '__main__':
    # 加载你训练好的模型
    model = YOLO('runs/train/exp14/weights/best.pt')  # 修改为你实际模型路径

    # 要预测的图片路径（可以是单张图，也可以是一个文件夹）
    source = 'data/Testimage'  # 例如 'test.jpg' 或 'test_images/' 文件夹

    # 执行推理
    results = model.predict(
        source=source,        # 输入图片路径或文件夹
        imgsz=640,            # 输入图像大小
        conf=0.25,            # 置信度阈值
        save=True,            # 是否保存预测结果图像
        save_txt=True,        # 是否保存预测框到 txt 文件
        save_conf=True,       # 保存每个框的置信度
        project='runs/predict',  # 输出目录
        name='exp',              # 子目录名
        device='',               # 默认自动选择设备
        show=False               # 若设为 True 会弹出图像窗口（需要图形界面）
    )

    print("推理完成，结果已保存到 runs/predict/exp/")
