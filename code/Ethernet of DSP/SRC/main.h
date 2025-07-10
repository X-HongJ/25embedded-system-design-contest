/*
 * main.h
 *
 *  Created on: 2021年8.9
 *      Author: ZLL
 */

#ifndef MAIN_H_
#define MAIN_H_

#include "DSP2833x_Device.h"

extern uint32 RST_Count_Base;
extern Uint16 Current_Diff_Cnt_1; //电流微分计算计数因子
extern Uint16 Current_Diff_Cnt_2; //电流微分计算计数因子
extern Uint16 Current_Diff_Cnt_3; //电流微分计算计数因子
extern char LCD_Index;  //LCD翻页，联网的动态参数
extern uint16 WizRecLength;
extern Uint16 initScreenFlag;
#endif
