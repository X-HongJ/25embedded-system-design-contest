#ifndef _LCD_H_
#define _LCD_H_
#include "types.h"
#include "DSP2833x_Device.h"
#define Command		0														//表示写指令
#define Data			1														//表示写显示数据
#define u8 unsigned char
#define LCD_Enable	1
#define NumOfElec 12			//LCD显示的用电器总数
#define SameCategoryStorageDepthOfElec	3	//同类别用电器存储深度
#define CS_1()  			GpioDataRegs.GPASET.bit.GPIO2 = 1				//片选至 1
#define CS_0()  			GpioDataRegs.GPACLEAR.bit.GPIO2 = 1				//片选至 0

#define AS_1()  			GpioDataRegs.GPASET.bit.GPIO4 = 1				//指令/数据至 1
#define AS_0()  			GpioDataRegs.GPACLEAR.bit.GPIO4 = 1				//指令/数据至 0

#define RST_1()  			GpioDataRegs.GPASET.bit.GPIO3 = 1				//复位至 1
#define RST_0()  			GpioDataRegs.GPACLEAR.bit.GPIO3 = 1				//复位至 0

#define SCL_1()  			GpioDataRegs.GPASET.bit.GPIO5 = 1				//时钟至 1
#define SCL_0()  			GpioDataRegs.GPACLEAR.bit.GPIO5 = 1				//时钟至 0

#define SDA_1()				GpioDataRegs.GPASET.bit.GPIO6 = 1				//数据至 1
#define SDA_0()				GpioDataRegs.GPACLEAR.bit.GPIO6 = 1				//数据至 0

//枚举故障类型
enum
{
	fault_Normal 			= -1,	//正常
	fault_normal_1			= 0,
	fault_Poor_contact		= 1,	//解除不良
	fault_arc_fault			= 2,	//电弧故障
	fault_leakage			= 3,	//漏电
	fault_overvoltage		= 4,	//过压
	fault_undervoltage		= 5,	//欠压
	fault_overload			= 6		//过载
};
//extern unsigned char HOPPING[];
/*extern u8 Title[];
extern u8 Category[][24];
extern u8 LCD_NUM[][12];
extern u8 LCD_AlphabetSM[][12];
extern u8 LCD_AlphabetBG[][12];*/
extern char isAPP1,isAPP2,isAPP3;
extern u8 LCD_RoomFlag;
//Init
void LCD_GPIO_Init(void);
void LCD_Initial(void);
//
void Write_Command_or_Data(unsigned char Dat,unsigned char Flag);
void Picture_display(const unsigned char *p);
void display_graphic_16x16(u8 page,u8 column,u8 *dp);
void display_graphic_12x16(u8 page,u8 column,u8 *dp);
void display_graphic_6x16(u8 page,u8 column,u8 *dp);
void Screen_clear(u8 num1,u8 num2);
void LCD_PageLogo(void);  //开机logo
void Title_LCD(void);
void LCD_UserBase(void);	//基础信息 TASK已包含
void LCD_PageUpdate(u8 Page,u8 fault,int wireTemp,char *Electype,Uint16 UV,Uint16 Ptotal,char *appNow,char *appInline,char *isAPP); //用电信息更新
void LCD_Loading(u8 POS); //加载中
void updateLoading(u8 POS);
void LCD_PageBL(void);	//BL基础界面
void LCD_PageBL_Size(Uint16 Size);//BL BIN Size
void LCD_BL_UpdateProgress(u8 progress);	//BL进度条
void LCD_PageVer(Uint16 BOOTV,Uint16 APPV);	//版本信息
void LCD_Task(u8 Page);	//基础事务
void LCD_BL_UpdateResult(u8 RST); //BL更新结果
void LCD_InitialFast(void);
void TimingInitializationLCD();
#endif
