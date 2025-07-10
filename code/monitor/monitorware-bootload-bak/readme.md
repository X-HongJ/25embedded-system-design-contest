bootloader实现中间件

bootloader网站对中间件协议：

json格式：
{
    "deviceId": Integer,
    "filePath": String,
    "action": "update",
    "timestamp": Integer(10)       
}

中间件、固件中心回复，json格式：
{
    "deviceID": Integer,
    "status": String("append"/"success"/"failed"),
    "desc": String,
    "timestamp": Integer(10)
}
