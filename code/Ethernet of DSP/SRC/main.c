
#include "DSP2833x_Device.h"     // DSP2833x ͷ�ļ�
#include "DSP2833x_Examples.h"   // DSP2833x �������ͷ�ļ�
#include  <math.h>
#include "SCI.h"
#include "FFT.h"
#include "ADC.h"
#include "EPwmSetup.h"
#include "Data_Frame.h"
#include "main.h"
/**************************************�궨��************************************************/
//W5500ͷ�ļ�
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

/**************************************��������************************************************/
Uint32 t1=0,t2=0,t3=0,t4=0,T1=0,T2=0,t5,t6,t7,t8,T3,T4,t9,t10,t11,t12,T5,T6;
//Uint16 SampleTable_I1[SampCnt];  //�������BUF��SIZE
//Uint16 SampleTable_U1[SampCnt];  //�������BUF��SIZE
//Uint16 SampleTable_I2[SampCnt];  //�������BUF��SIZE
//Uint16 SampleTable_U2[SampCnt];  //�������BUF��SIZE
//Uint16 SampleTable_I3[SampCnt];  //�������BUF��SIZE
//Uint16 SampleTable_U3[SampCnt];  //�������BUF��SIZE
//Uint16 Sample_Leakge_Current_1[SampCnt];
//Uint16 Sample_Leakge_Current_2[SampCnt];
//Uint16 Sample_Leakge_Current_3[SampCnt];
Uint16 Sample_Temp;  //������ʱ����
Uint16 array_index_I1;  //�������
Uint16 array_index_U1;  //�������
Uint16 array_index_I2;  //�������
Uint16 array_index_U2;  //�������
Uint16 array_index_I3;  //�������
Uint16 array_index_U3;  //�������
Uint16 array_index_LeakgeCurrent;
Uint16 TIM0_Cnt_Base = 0; //��ʱ��0�жϼ���ֵ
Uint16 _500ms_Cnt_Base = 0; //500ms����ֵ
Uint16 TIM1_Cnt = 0;		//��ʱ�� 1 ����ֵ
char isBlink = 0;  //���Ƿ���˸  0:���� 1���̵���   2�������
char IWD_Cnt = 0;   //���Ź���λ����ֵ
char Handshake_FLG=0; //���ְ����ͱ�־λ
Uint16 Current_Diff_Cnt_1=0; //����΢�ּ����������
Uint16 Current_Diff_Cnt_2=0; //����΢�ּ����������
Uint16 Current_Diff_Cnt_3=0; //����΢�ּ����������
char Current_Diff_Flag_1 = 0; //����΢�ּ��������־
char Current_Diff_Flag_2 = 0; //����΢�ּ��������־
char Current_Diff_Flag_3 = 0; //����΢�ּ��������־
Uint16 ENV_Count_Base = 0; //��������������
u8 ENV_Upload_Flag = 0;//���ͱ�־
Uint16 LCD_Count_Base = 0; //LCDˢ�¼�������
u8 LCDRefreshFlag = 0;//LCD���±�־
u8 WaitFlag = 0; //30swait Flag
#define SocketMaxTryTimes 15
uint32 RST_Count_Base = 0; //RESET����
u8 Reset_flag = 0;//RST��־
/**************************************������**************************************************/
interrupt void ISRCap1(void);
interrupt void ISRCap2(void);
interrupt void ISRCap3(void);
interrupt void epwm_int(void);
interrupt void ISRTimer0(void);  //������ʱ��TIME0�ж�
interrupt void ISRTimer1(void);

extern    void InitCapl(void);
void Del_ArcFlag(void);
//extern    void EPwmSetup();
//extern    void Freq_Analysis();
/*extern    void scia_echoback_init();
extern    void scia_xmit(int a);          //���������ֽڵĺ���
extern    void scia_msg(char *msg);       //������������ĺ���
extern    void ADC_Setup(void);*/
/***************************************W5500*************************************************/
//extern uint8 txsize[];
//extern uint8 rxsize[];
uint8 buffer[64];
char NetworkCableState=0;//��������״̬
char NetworkCableStateCopy=-1;//��������״̬
uint16 WizRecLength=0;
/***************************************W5500************************************************/
/**************************************������************************************************/
Uint32 Cnt;
char LCD_Index = 0;  //LCD��ҳ��0��һҳ���������Ķ�̬����
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
   Uint16 i;              //�������
   unsigned char app1StartBuff[] = "!!APP1_Start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
   unsigned char app2StartBuff[] = "!!APP2_Start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
   int wait_min = 0; //��ǰ�ȴ�ʱ��
   u8 isCLR_REBOOT_TIMES = 0; //�Ƿ�����ȴ�ʱ���־
   u8 Socket_Retry_Times = 0; //socketʧ�ܴ���
   uint8 REBOOT_TIMES = 0;	//�����Ĵ���
   int reboot_time_array[10] = {1,3,5,10,15,20,30,60,120,240}; //��ʱ���� unit:min
/****************************************W5500***********************************************/
	uint16 local_port=6000;
/****************************************W5500************************************************/
// ��ʼ��ϵͳ����:
// ����PLL, WatchDog, ʹ������ʱ��
// ��������������Դ�DSP2833x_SysCtrl.c�ļ����ҵ�..
   InitSysCtrl();
  // Xintf����IO��ʼ��
     InitXintf16Gpio();

     // ��ʼ������SCI-A��GPIO
        InitSciaGpio();

//EALLOW��EDIS�ǳɶ�ʹ�õģ���Щ�Ĵ������ܵ������ģ���������д��
//EALLOW�൱��ȥ����������д�����ļĴ������в����� EDIS �����°�����Ĵ���������������˼��
   EALLOW;
   SysCtrlRegs.HISPCP.all = ADC_MODCLK;	// ADCʱ�ӵ����� HSPCLK = SYSCLKOUT/ADC_MODCLK
   EDIS;

// ��������жϳ�ʼ���ж�������:
// ��ֹCPUȫ���ж�
   DINT;

// ��ʼ��PIE�ж���������ʹ��ָ���жϷ����ӳ���ISR��
// ��Щ�жϷ����ӳ��򱻷�����DSP280x_DefaultIsr.cԴ�ļ���
// �������������DSP2833x_PieVect.cԴ�ļ�����.
   InitPieCtrl();
   MemCopyUser(&RamfuncsLoadStart, &RamfuncsLoadEnd, &RamfuncsRunStart);
// ��ֹCPU�жϺ��������CPU�жϱ�־
   IER = 0x0000;
   IFR = 0x0000;

// PIE ������ָ��ָ���жϷ����(ISR)������ʼ��.
// ��ʹ�ڳ����ﲻ��Ҫʹ���жϹ��ܣ�ҲҪ�� PIE ��������г�ʼ��.
// ��������Ϊ�˱���PIE����Ĵ���.
   InitPieVectTable();

//EALLOW��EDIS�ǳɶ�ʹ�õģ���Щ�Ĵ������ܵ������ģ���������д��
//EALLOW�൱��ȥ����������д�����ļĴ������в����� EDIS �����°�����Ĵ���������������˼��
   EALLOW;  // This is needed to write to EALLOW protected registers
   PieVectTable. ECAP1_INT = &ISRCap1;  // ��CAP1�ж���Ӷ��ж���������
   PieVectTable. ECAP2_INT = &ISRCap2;  // ��CAP2�ж���Ӷ��ж���������
   PieVectTable. ECAP3_INT = &ISRCap3;  // ��CAP2�ж���Ӷ��ж���������
   PieVectTable. EPWM1_INT = &epwm_int;
   PieVectTable. TINT0     = &ISRTimer0;  //����ʱ���ж���Ӷ��ж���������
   PieVectTable. XINT13    = &ISRTimer1;
   EDIS;    // This is needed to disable write to EALLOW protected registers

	for (i=0; i<SampCnt; i++)               //Forѭ��
	{
		I_Array_Old1[i] = 0;//��һ�ε�������
		I_Array_Old2[i] = 0;//��һ�ε�������
		I_Array_Old3[i] = 0;//��һ�ε�������
		Diff_I_Buff[i] = 0;//��ֵ�������
	}
// ��ʼ��CAP���������
   InitCapl();

// ��ʼ��ADC
   InitAdc();

   //ADC����
   ADC_Setup();
   //epwm����
   EPwmSetup();

// ��ʼ��SCI-A������ʽ�Ͳ�������
   scia_echoback_init();

   InitCpuTimers();   // ��ʱ����ʼ��


//ͨ�����������Ϳ����ö�ʱ�� 0 ÿ��һ��ʱ�����һ���жϣ����ʱ���
//���㹫ʽΪ�� ��T= Freq * Period /150000000��s���������� 150000000 ��
//CPU ��ʱ��Ƶ�ʣ��� 150MHz �� ʱ��Ƶ�ʣ���Դ�ʵ�飬Frep Ϊ 150��Period Ϊ 1000000����ô��T=1s��
    ConfigCpuTimer(&CpuTimer0, 150, 100000);// 100ms
    ConfigCpuTimer(&CpuTimer1, 150, 1000);//((float)CurtDiffPeriod)*1000);//1ms
    StartCpuTimer0();  //������ʱ��0
    StartCpuTimer1();  //������ʱ��1

    IER |= M_INT1;    //ʹ�ܵ�һ���ж�
    PieCtrlRegs.PIECTRL.bit.ENPIE = 1; //ʹ�����ж�
    PieCtrlRegs.PIEIER1.bit.INTx7 = 1; //ʹ�ܵ�һ���ж���ĵ��߸��ж�--��ʱ���ж�

    IER |= M_INT4;  //ʹ�ܵ�һ���ж�
    IER |= M_INT3;  //ʹ��epwm�ж�
    IER |= M_INT13; //��ʱ��1�ж�

    PieCtrlRegs.PIEIER3.bit.INTx1 = 1; //ʹ�ܵ������ж���ĵ�һ���ж�--epwm�ж�
    PieCtrlRegs.PIEIER4.bit.INTx1 = 1; //ʹ�ܵ������ж���ĵ�һ���ж�--CAP1�ж�
    PieCtrlRegs.PIEIER4.bit.INTx2 = 1; //ʹ�ܵ������ж���ĵڶ����ж�--CAP2�ж�
    PieCtrlRegs.PIEIER4.bit.INTx3 = 1; //ʹ�ܵ������ж���ĵڶ����ж�--CAP3�ж�

    EINT;   // �ж�ʹ��
    ERTM;   // ʹ����ʵʱ�ж�

/*********************************EEPROM*************************************/
    AT24CXX_Eerom_Gpio_Init();
    REBOOT_TIMES = AT24CXX_ReadData(REBOOT_TIMES_ADDRESS); //read boot times
    i = AT24CXX_ReadData(BL_Status_ADDRESS);
    ClrAPP_Cnt();//clr app cnt
    Read_BoardInfo(); //��ȡID
    if(i == BL_APP1)
    	scia_msg((char*)app1StartBuff,sizeof(app1StartBuff)); //��λʱ��������
    if(i == BL_APP2)
        scia_msg((char*)app2StartBuff,sizeof(app2StartBuff)); //��λʱ��������

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
    //��ֲW5500�ĳ�ʼ������
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
    close_socket(0);close_socket(1);//����˿ڻ���
	DELAY_US(50000);
	Show_BoardInfo();
	Self_Check(); //�Լ켰�ָ�


	while(1)
	{

		Cnt++;
		IWD_Cnt = 0;  //ι��
		/*DHCP����*/
		do_dhcp();
		if(initScreenFlag) //���ڳ�ʼ��LCD����ֹ����
		{
			initScreenFlag = 0;
			TimingInitializationLCD();
		}
		if(Reset_flag)
		{
			SCI_Printf("NetWork��ʱ����\r\n");
			//W5500_RST();
			SoftwareReset();
			RST_Count_Base = 0;
			Reset_flag  = 0;
		}
		/*��������״̬-����*/
		NetworkCableState = getNetworkCableState();   //Ϊ1��������
		if((NetworkCableState != NetworkCableStateCopy)||(NetworkCableStateCopy==-1))
		{
			NetworkCableStateCopy = NetworkCableState;
			if(NetworkCableState)
			{
				printf("\r\n��������\r\n");
				SCI_Printf("����������\n");
			}
			else
			{
				printf("\r\n���߶Ͽ�\r\n");
				SCI_Printf("�����ѶϿ�\n");
				Blue_Led_OFF;//����ƹر�
				Err_Code = Err_Code|gBIT1; //set net error
				DELAY_US(500000);
				//reset_w5500();											/*Ӳ��λW5500*/
				//socket_buf_init(txsize, rxsize);		/*��ʼ��8��Socket�ķ��ͽ��ջ����С*/
				close_socket(0);close_socket(1);//����˿ڻ���
			}
		}
		while(!NetworkCableState)
		{
			NetworkCableState = getNetworkCableState();
			DELAY_US(1000000);
			printf("Network cable lose\n");
			SCI_Printf("Network cable lose\n");
			IWD_Cnt = 0;  //ι��
		}
		/*��������״̬-�������*/

		/*TCPC��ҵ�����*/
		if(dhcp_state != STATE_DHCP_LEASED) //δ���DHCP
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
			continue ; //��������ѭ��
		}
		