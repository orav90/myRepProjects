#pragma once
#include "CShape.h"
class CRectangle : public CShape
{
public:
       DECLARE_SERIAL(CRectangle)
       CRectangle(void);
       ~CRectangle(void);
       void myShapeDraw(CDC *dc);
};
