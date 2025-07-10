import numpy as np
from django.http import HttpResponse
from django.shortcuts import render

# Create your views here.

latest_frame = None  # 存储当前帧

def receive_frame(request):
    global latest_frame
    if request.method == 'POST' and 'image' in request.FILES:
        file = request.FILES['image']
        np_arr = np.frombuffer(file.read(), np.uint8)
        latest_frame = cv2.imdecode(np_arr, cv2.IMREAD_COLOR)
        return HttpResponse("OK")

    return HttpResponse("Invalid request", status=400)
