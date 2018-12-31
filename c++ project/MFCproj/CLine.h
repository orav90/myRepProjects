#pragma once
#include "CShape.h"
class CLine : public CShape
{
public:
       DECLARE_SERIAL(CLine)
       CLine();
       ~CLine();
       void myShapeDraw (CDC *dc);
       void rearrangeStartEndPoints ();
};