
#include "DSP2833x_Device.h"     // DSP2833x 头文件
#include "DSP2833x_Examples.h"   // DSP2833x 例子相关头文件
#include  <math.h>
#include "SCI.h"
#include "FFT.h"
#include "ADC.h"
#include "EPwmSetup.h"
#include "Data_Frame.h"
#include "main.h"
/**************************************宏定义************************************************/
//W5500头文件
#include "device.h"
#include "spi2.h"
#include "ult.h"
#include "socket.h"
#include "w5500.h"
#include "24c16.h"
#include "string.h"
#include "dhcp.h"

#include "Ethernet_Deal.h"
#include "I2cEeprom.h"
#include "BootLoader.h"
//LCD
#include "LCD.h"
/********************************************************************************************/
#define POST_SHIFT   0  // Shift results after the entire sample table is full
#define INLINE_SHIFT 1  // Shift results as the data is taken from the results regsiter
#define NO_SHIFT     0  // Do not shift the results

// ADC start parameters
#if (CPU_FRQ_150MHZ)     // Default - 150 MHz SYSCLKOUT
  #define ADC_MODCLK 0x3 // HSPCLK = SYSCLKOUT/2*ADC_MODCLK2 = 150/(2*3)   = 25.0 MHz
#endif
#if (CPU_FRQ_100MHZ)
  #define ADC_MODCLK 0x2 // HSPCLK = SYSCLKOUT/2*ADC_MODCLK2 = 100/(2*2)   = 25.0 MHz
#endif

//#define SampCnt 256

/**************************************变量定义************************************************/
Uint32 t1=0,t2=0,t3=0,t4=0,T1=0,T2=0,t5,t6,t7,t8,T3,T4,t9,t10,t11,t12,T5,T6;
//Uint16 SampleTable_I1[SampCnt];  //定义接收BUF的SIZE
//Uint16 SampleTable_U1[SampCnt];  //定义接收BUF的SIZE
//Uint16 SampleTable_I2[SampCnt];  //定义接收BUF的SIZE
//Uint16 SampleTable_U2[SampCnt];  //定义接收BUF的SIZE
//Uint16 SampleTable_I3[SampCnt];  //定义接收BUF的SIZE
//Uint16 SampleTable_U3[SampCnt];  //定义接收BUF的SIZE
//Uint16 Sample_Leakge_Current_1[SampCnt];
//Uint16 Sample_Leakge_Current_2[SampCnt];
//Uint16 Sample_Leakge_Current_3[SampCnt];
Uint16 Sample_Temp;  //采样临时变量
Uint16 array_index_I1;  //定义变量
Uint16 array_index_U1;  //定义变量
Uint16 array_index_I2;  //定义变量
Uint16 array_index_U2;  //定义变量
Uint16 array_index_I3;  //定义变量
Uint16 array_index_U3;  //定义变量
Uint16 array_index_LeakgeCurrent;
Uint16 TIM0_Cnt_Base = 0; //定时器0中断计数值
Uint16 _500ms_Cnt_Base = 0; //500ms计数值
Uint16 TIM1_Cnt = 0;		//计时器 1 计数值
char isBlink = 0;  //灯是否闪烁  0:不闪 1：绿灯闪   2：红灯闪
char IWD_Cnt = 0;   //看门狗复位计数值
char Handshake_FLG=0; //握手包发送标志位
Uint16 Current_Diff_Cnt_1=0; //电流微分计算计数因子
Uint16 Current_Diff_Cnt_2=0; //电流微分计算计数因子
Uint16 Current_Diff_Cnt_3=0; //电流微分计算计数因子
char Current_Diff_Flag_1 = 0; //电流微分计算计数标志
char Current_Diff_Flag_2 = 0; //电流微分计算计数标志
char Current_Diff_Flag_3 = 0; //电流微分计算计数标志
Uint16 ENV_Count_Base = 0; //环境包计数基础
u8 ENV_Upload_Flag = 0;//发送标志
Uint16 LCD_Count_Base = 0; //LCD刷新计数基础
u8 LCDRefreshFlag = 0;//LCD更新标志
u8 WaitFlag = 0; //30swait Flag
#define SocketMaxTryTimes 15
uint32 RST_Count_Base = 0; //RESET计数
u8 Reset_flag = 0;//RST标志
/**************************************声明区**************************************************/
interrupt void ISRCap1(void);
interrupt void ISRCap2(void);
interrupt void ISRCap3(void);
interrupt void epwm_int(void);
interrupt void ISRTimer0(void);  //声明定时器TIME0中断
interrupt void ISRTimer1(void);

extern    void InitCapl(void);
void Del_ArcFlag(void);
//extern    void EPwmSetup();
//extern    void Freq_Analysis();
/*extern    void scia_echoback_init();
extern    void scia_xmit(int a);          //声明发送字节的函数
extern    void scia_msg(char *msg);       //声明发送数组的函数
extern    void ADC_Setup(void);*/
/***************************************W5500*************************************************/
//extern uint8 txsize[];
//extern uint8 rxsize[];
uint8 buffer[64];
char NetworkCableState=0;//网线连接状态
char NetworkCableStateCopy=-1;//网线连接状态
uint16 WizRecLength=0;
/***************************************W5500************************************************/
/**************************************主程序************************************************/
Uint32 Cnt;
char LCD_Index = 0;  //LCD翻页（0第一页），联网的动态参数
void MemCopyUser(Uint16 *SourceAddr, Uint16* SourceEndAddr, Uint16* DestAddr)
{
    while(SourceAddr < SourceEndAddr)
    {
       *DestAddr++ = *SourceAddr++;
    }
    return;
}

uint16_t sin_wave[256] = {
    2050, 2098, 2146, 2194, 2241, 2289, 2337, 2384, 2431, 2478, 2525, 2572,
    2618, 2663, 2709, 2754, 2799, 2843, 2886, 2929, 2972, 3014, 3056, 3096,
    3137, 3176, 3215, 3253, 3291, 3327, 3363, 3398, 3433, 3466, 3499, 3530,
    3561, 3591, 3620, 3648, 3675, 3701, 3726, 3750, 3773, 3795, 3816, 3836,
    3854, 3872, 3889, 3904, 3918, 3931, 3943, 3954, 3964, 3973, 3980, 3986,
    3991, 3995, 3998, 3999, 3999, 3999, 3997, 3993, 3989, 3983, 3976, 3968,
    3959, 3949, 3938, 3925, 3911, 3896, 3881, 3863, 3845, 3826, 3806, 3784,
    3762, 3738, 3714, 3688, 3662, 3634, 3606, 3576, 3546, 3515, 3482, 3449,
    3416, 3381, 3345, 3309, 3272, 3234, 3196, 3156, 3117, 3076, 3035, 2993,
    2951, 2908, 2865, 2821, 2776, 2731, 2686, 2641, 2595, 2548, 2502, 2455,
    2408, 2360, 2313, 2265, 2217, 2170, 2122, 2074, 2025, 1977, 1929, 1882,
    1834, 1786, 1739, 1691, 1644, 1597, 1551, 1504, 1458, 1413, 1368, 1323,
    1278, 1234, 1191, 1148, 1106, 1064, 1023, 982,  943,  903,  865,  827,
    790,  754,  718,  683,  650,  617,  584,  553,  523,  493,  465,  437,
    411,  385,  361,  337,  315,  293,  273,  254,  236,  218,  203,  188,
    174,  161,  150,  140,  131,  123,  116,  110,  106,  102,  100,  100,
    100,  101,  104,  108,  113,  119,  126,  135,  145,  156,  168,  181,
    195,  210,  227,  245,  263,  283,  304,  326,  349,  373,  398,  424,
    451,  479,  508,  538,  569,  600,  633,  666,  701,  736,  772,  808,
    846,  884,  923,  962,  1003, 1043, 1085, 1127, 1170, 1213, 1256, 1300,
    1345, 1390, 1436, 1481, 1527, 1574, 1621, 1668, 1715, 1762, 1810, 1858,
    1905, 1953, 2001, 2049};

struct Complex Sample_TEST[SampCnt];
float  test_output[SampCnt];

void main(void)
{
   Uint16 i;              //定义变量
   unsigned char app1StartBuff[] = "!!APP1_Start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
   unsigned char app2StartBuff[] = "!!APP2_Start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
   int wait_min = 0; //当前等待时间
   u8 isCLR_REBOOT_TIMES = 0; //是否清零等待时间标志
   u8 Socket_Retry_Times = 0; //socket失败次数
   uint8 REBOOT_TIMES = 0;	//重启的次数
   int reboot_time_array[10] = {1,3,5,10,15,20,30,60,120,240}; //超时数组 unit:min
/****************************************W5500***********************************************/
	uint16 local_port=6000;
/****************************************W5500************************************************/
// 初始化系统控制:
// 设置PLL, WatchDog, 使能外设时钟
// 下面这个函数可以从DSP2833x_SysCtrl.c文件中找到..
   InitSysCtrl();
  // Xintf总线IO初始化
     InitXintf16Gpio();

     // 初始化串口SCI-A的GPIO
        InitSciaGpio();

//EALLOW，EDIS是成对使用的，有些寄存器是受到保护的，不能任意写，
//EALLOW相当于去掉保护，对写保护的寄存器进行操作后 EDIS 是重新把这个寄存器保护起来的意思。
   EALLOW;
   SysCtrlRegs.HISPCP.all = ADC_MODCLK;	// ADC时钟的配置 HSPCLK = SYSCLKOUT/ADC_MODCLK
   EDIS;

// 清除所有中断初始化中断向量表:
// 禁止CPU全局中断
   DINT;

// 初始化PIE中断向量表，并使其指向中断服务子程序（ISR）
// 这些中断服务子程序被放在了DSP280x_DefaultIsr.c源文件中
// 这个函数放在了DSP2833x_PieVect.c源文件里面.
   InitPieCtrl();
   MemCopyUser(&RamfuncsLoadStart, &RamfuncsLoadEnd, &RamfuncsRunStart);
// 禁止CPU中断和清除所有CPU中断标志
   IER = 0x0000;
   IFR = 0x0000;

// PIE 向量表指针指向中断服务程(ISR)完成其初始化.
// 即使在程序里不需要使用中断功能，也要对 PIE 向量表进行初始化.
// 这样做是为了避免PIE引起的错误.
   InitPieVectTable();

//EALLOW，EDIS是成对使用的，有些寄存器是受到保护的，不能任意写，
//EALLOW相当于去掉保护，对写保护的寄存器进行操作后 EDIS 是重新把这个寄存器保护起来的意思。
   EALLOW;  // This is needed to write to EALLOW protected registers
   PieVectTable. ECAP1_INT = &ISRCap1;  // 将CAP1中断添加都中断向量表里
   PieVectTable. ECAP2_INT = &ISRCap2;  // 将CAP2中断添加都中断向量表里
   PieVectTable. ECAP3_INT = &ISRCap3;  // 将CAP2中断添加都中断向量表里
   PieVectTable. EPWM1_INT = &epwm_int;
   PieVectTable. TINT0     = &ISRTimer0;  //将定时器中断添加都中断向量表里
   PieVectTable. XINT13    = &ISRTimer1;
   EDIS;    // This is needed to disable write to EALLOW protected registers

	for (i=0; i<SampCnt; i++)               //For循环
	{
		I_Array_Old1[i] = 0;//上一次电流数组
		I_Array_Old2[i] = 0;//上一次电流数组
		I_Array_Old3[i] = 0;//上一次电流数组
		Diff_I_Buff[i] = 0;//差分电流数组
	}
// 初始化CAP的相关配置
   InitCapl();

// 初始化ADC
   InitAdc();

   //ADC配置
   ADC_Setup();
   //epwm设置
   EPwmSetup();

// 初始化SCI-A工作方式和参数配置
   scia_echoback_init();

   InitCpuTimers();   // 定时器初始化


//通过以下面程序就可以让定时器 0 每隔一段时间产生一次中断，这段时间的
//计算公式为： △T= Freq * Period /150000000（s）；（其中 150000000 是
//CPU 的时钟频率，即 150MHz 的 时钟频率）针对此实验，Frep 为 150，Period 为 1000000，那么△T=1s。
    ConfigCpuTimer(&CpuTimer0, 150, 100000);// 100ms
    ConfigCpuTimer(&CpuTimer1, 150, 1000);//((float)CurtDiffPeriod)*1000);//1ms
    StartCpuTimer0();  //开启定时器0
    StartCpuTimer1();  //开启定时器1

    IER |= M_INT1;    //使能第一组中断
    PieCtrlRegs.PIECTRL.bit.ENPIE = 1; //使能总中断
    PieCtrlRegs.PIEIER1.bit.INTx7 = 1; //使能第一组中断里的第七个中断--定时器中断

    IER |= M_INT4;  //使能第一组中断
    IER |= M_INT3;  //使能epwm中断
    IER |= M_INT13; //定时器1中断

    PieCtrlRegs.PIEIER3.bit.INTx1 = 1; //使能第三组中断里的第一个中断--epwm中断
    PieCtrlRegs.PIEIER4.bit.INTx1 = 1; //使能第四组中断里的第一个中断--CAP1中断
    PieCtrlRegs.PIEIER4.bit.INTx2 = 1; //使能第四组中断里的第二个中断--CAP2中断
    PieCtrlRegs.PIEIER4.bit.INTx3 = 1; //使能第四组中断里的第二个中断--CAP3中断

    EINT;   // 中断使能
    ERTM;   // 使能总实时中断

/*********************************EEPROM*************************************/
    AT24CXX_Eerom_Gpio_Init();
    REBOOT_TIMES = AT24CXX_ReadData(REBOOT_TIMES_ADDRESS); //read boot times
    i = AT24CXX_ReadData(BL_Status_ADDRESS);
    ClrAPP_Cnt();//clr app cnt
    Read_BoardInfo(); //读取ID
    if(i == BL_APP1)
    	scia_msg((char*)app1StartBuff,sizeof(app1StartBuff)); //复位时发送数据
    if(i == BL_APP2)
        scia_msg((char*)app2StartBuff,sizeof(app2StartBuff)); //复位时发送数据

    /***************LCD*****/
#if LCD_Enable
    LCD_GPIO_Init();
	LCD_Initial();
	LCD_PageLogo();
	DELAY_US(1000000);
	Screen_clear(0,7);
	Title_LCD();
	LCD_Loading(i);
#endif
    /***********************************************W5500********************/
    //移植W5500的初始化函数
    InitSpiaGpio();
    gpio_config();
    spi_init();		  // init SPI
	BEEP_ON;
	delay_loop(5);
	BEEP_OFF;
    Reset_W5500();
    //set_default();
    //set_network();
    set_w5500_mac();
    init_dhcp_client();
    /**********************************************W5500END*********************/
    close_socket(0);close_socket(1);//清理端口缓存
	DELAY_US(50000);
	Show_BoardInfo();
	Self_Check(); //自检及恢复


	while(1)
	{

		Cnt++;
		IWD_Cnt = 0;  //喂狗
		/*DHCP过程*/
		do_dhcp();
		if(initScreenFlag) //定期初始化LCD，防止白屏
		{
			initScreenFlag = 0;
			TimingInitializationLCD();
		}
		if(Reset_flag)
		{
			SCI_Printf("NetWork超时重启\r\n");
			//W5500_RST();
			SoftwareReset();
			RST_Count_Base = 0;
			Reset_flag  = 0;
		}
		/*网线连接状态-处理*/
		NetworkCableState = getNetworkCableState();   //为1是有连接
		if((NetworkCableState != NetworkCableStateCopy)||(NetworkCableStateCopy==-1))
		{
			NetworkCableStateCopy = NetworkCableState;
			if(NetworkCableState)
			{
				printf("\r\n网线连接\r\n");
				SCI_Printf("网线已连接\n");
			}
			else
			{
				printf("\r\n网线断开\r\n");
				SCI_Printf("网线已断开\n");
				Blue_Led_OFF;//网络灯关闭
				Err_Code = Err_Code|gBIT1; //set net error
				DELAY_US(500000);
				//reset_w5500();											/*硬复位W5500*/
				//socket_buf_init(txsize, rxsize);		/*初始化8个Socket的发送接收缓存大小*/
				close_socket(0);close_socket(1);//清理端口缓存
			}
		}
		while(!NetworkCableState)
		{
			NetworkCableState = getNetworkCableState();
			DELAY_US(1000000);
			printf("Network cable lose\n");
			SCI_Printf("Network cable lose\n");
			IWD_Cnt = 0;  //喂狗
		}
		/*网线连接状态-处理结束*/

		/*TCPC的业务代码*/
		if(dhcp_state != STATE_DHCP_LEASED) //未完成DHCP
		{
			//DELAY_US(300000);
			if(dhcp_state == STATE_DHCP_REREQUEST)
				SCI_Printf("reDHCPing\n");
			else
			{
				DELAY_US(300000);
				SCI_Printf("firstDHCPing\n");
				updateLoading(LCD_Index%5);
			}
			continue ; //结束本次循环
		}
		