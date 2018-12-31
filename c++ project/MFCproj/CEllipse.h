#pragma once
#include "CShape.h"
class CEllipse : public CShape
{
public:
       DECLARE_SERIAL(CEllipse)
       CEllipse();
       ~CEllipse();
       void myShapeDraw (CDC *dc);
};
 